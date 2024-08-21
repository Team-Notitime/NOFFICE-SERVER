package com.notitime.noffice.external.firebase;

import static com.notitime.noffice.external.firebase.FCMNotificationConstants.ANNOUNCE_CREATE_BODY_PREFIX;
import static com.notitime.noffice.external.firebase.FCMNotificationConstants.ANNOUNCE_CREATE_BODY_SUFFIX;
import static com.notitime.noffice.external.firebase.FCMNotificationConstants.ANNOUNCE_CREATE_TITLE_SUFFIX;
import static com.notitime.noffice.external.firebase.FCMNotificationConstants.SPECIFIED_MEMBER_CONTENT;
import static com.notitime.noffice.external.firebase.FCMNotificationConstants.SPECIFIED_MEMBER_TITLE;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.MulticastMessage;
import com.notitime.noffice.api.announcement.business.ReadStatusRecoder;
import com.notitime.noffice.api.organization.business.RoleVerifier;
import com.notitime.noffice.domain.FcmToken;
import com.notitime.noffice.domain.announcement.model.Announcement;
import com.notitime.noffice.domain.announcement.persistence.AnnouncementRepository;
import com.notitime.noffice.domain.fcmtoken.persistence.FcmTokenRepository;
import com.notitime.noffice.domain.member.model.Member;
import com.notitime.noffice.domain.member.persistence.MemberRepository;
import com.notitime.noffice.domain.organization.model.Organization;
import com.notitime.noffice.domain.organization.persistence.OrganizationRepository;
import com.notitime.noffice.global.exception.NotFoundException;
import com.notitime.noffice.global.web.BusinessErrorCode;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class FcmService {

	private final FcmTokenRepository fcmTokenRepository;
	private final OrganizationRepository organizationRepository;
	private final AnnouncementRepository announcementRepository;
	private final MemberRepository memberRepository;
	private final RoleVerifier roleVerifier;
	private final ReadStatusRecoder readStatusRecoder;

	public FCMCreateResponse sendToMember(Long leaderId, FCMSingleCreateRequest request) {
		roleVerifier.verifyLeader(leaderId, request.organizationId());

		List<Member> members = memberRepository.findAllById(request.targetMemberIds());
		List<String> resultTokens = new ArrayList<>();
		for (Member member : members) {
			String title = member.getName();
			for (String token : getMemberTokens(member.getId())) {
				log.info("Sending FCM Notification : {} --- token: {}", request.notificationTitle(), token);
				Message message = Message.builder()
						.putData("title", title + SPECIFIED_MEMBER_TITLE.getValue())
						.putData("content", SPECIFIED_MEMBER_CONTENT.getValue())
						.setToken(token)
						.build();
				sendAsync(message);
				resultTokens.add(token);
			}
		}
		return FCMCreateResponse.of(request.notificationTitle(), request.notificationBody(), resultTokens);
	}

	public void sendAnnouncementCreatedMessage(Announcement announcement) {
		Organization organization = announcement.getOrganization();
		String slicedOrganizationName = parseSlicedOrganizationName(organization);
		String slicedTitle = parseSlicedAnnouncementTitle(announcement);
		sendToOrganizationTopic(
				organization.getId(),
				slicedOrganizationName + ANNOUNCE_CREATE_TITLE_SUFFIX.getValue(),
				ANNOUNCE_CREATE_BODY_PREFIX.getValue() + slicedTitle + ANNOUNCE_CREATE_BODY_SUFFIX.getValue());
	}


	public void sendRemind(Long organizationId, Long announcementId) {
		Announcement announcement = announcementRepository.findById(announcementId)
				.orElseThrow(() -> new IllegalArgumentException("해당 공지가 존재하지 않습니다."));
		String slicedTitle = announcement.getTitle().length() > 10 ? announcement.getTitle().substring(0, 10)
				: announcement.getTitle();
		sendToOrganizationTopic(organizationId, "리마인드 알림", "공지사항 " + slicedTitle + "를 확인해주세요.");
	}

	public FCMCreateResponse sendToUnReader(Long leaderId, Long announcementId) {
		Organization organization = announcementRepository.findById(announcementId)
				.orElseThrow(() -> new NotFoundException(BusinessErrorCode.NOT_FOUND_ANNOUNCEMENT))
				.getOrganization();
		roleVerifier.verifyLeader(leaderId, organization.getId());
		List<String> unreadMemberTokens = readStatusRecoder.getUnreadMemberIds(announcementId).stream()
				.map(this::getMemberTokens)
				.flatMap(List::stream)
				.toList();
		String title = parseSlicedOrganizationName(organization) + " 미열람자 알림";
		String content = "관리자가 공지 확인을 요청했어요.";
		MulticastMessage message = MulticastMessage.builder()
				.putData("title", title)
				.putData("content", content)
				.addAllTokens(unreadMemberTokens)
				.build();
		sendMulticast(message);
		return FCMCreateResponse.of(title, content, unreadMemberTokens);
	}

	private void sendToOrganizationTopic(Long organizationId, String title, String content) {
		Organization organization = organizationRepository.findById(organizationId)
				.orElseThrow(() -> new NotFoundException(BusinessErrorCode.NOT_FOUND_ORGANIZATION));
		Message message = Message.builder()
				.putData("title", title)
				.putData("content", content)
				.setTopic("organization_" + organizationId)
				.build();
		sendAsync(message);
	}

	public void subscribeTopic(List<String> tokens, Long organizationId) {
		// TODO : 사용자의 그룹 가입 시 토큰과함께 조직의 토픽을 구독하도록 수정
		FirebaseMessaging.getInstance().subscribeToTopicAsync(tokens, "organization_" + organizationId.toString());
	}

	public void sendAsync(Message message) {
		FirebaseMessaging.getInstance().sendAsync(message);
	}

	public void sendMulticast(MulticastMessage message) {
		FirebaseMessaging.getInstance().sendEachForMulticastAsync(message);
	}

	private void validateMemberExist(Long memberId) {
		boolean isExists = !memberRepository.existsById(memberId);
		throw new NotFoundException(BusinessErrorCode.NOT_FOUND_MEMBER);
	}

	private List<String> getMemberTokens(Long memberId) {
		return fcmTokenRepository.findByMemberId(memberId).stream()
				.map(FcmToken::getValue)
				.toList();
	}

	private String parseSlicedOrganizationName(Organization organization) {
		return organization.getName().length() > 10 ? organization.getName().substring(0, 10) : organization.getName();
	}

	private String parseSlicedAnnouncementTitle(Announcement announcement) {
		return announcement.getTitle().length() > 10 ? announcement.getTitle().substring(0, 10)
				: announcement.getTitle();
	}
}
