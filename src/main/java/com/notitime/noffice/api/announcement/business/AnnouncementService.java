package com.notitime.noffice.api.announcement.business;

import static com.notitime.noffice.global.web.BusinessErrorCode.NOT_FOUND_ANNOUNCEMENT;
import static com.notitime.noffice.global.web.BusinessErrorCode.NOT_FOUND_MEMBER;
import static com.notitime.noffice.global.web.BusinessErrorCode.NOT_FOUND_ORGANIZATION;

import com.notitime.noffice.api.announcement.presentation.dto.request.AnnouncementCreateRequest;
import com.notitime.noffice.api.announcement.presentation.dto.request.AnnouncementUpdateRequest;
import com.notitime.noffice.api.announcement.presentation.dto.response.AnnouncementCoverResponse;
import com.notitime.noffice.api.announcement.presentation.dto.response.AnnouncementResponse;
import com.notitime.noffice.api.announcement.presentation.dto.response.AnnouncementResponses;
import com.notitime.noffice.api.announcement.presentation.dto.response.ReadStatusResponse;
import com.notitime.noffice.api.member.presentation.dto.response.MemberInfoResponse;
import com.notitime.noffice.api.notification.business.NotificationService;
import com.notitime.noffice.api.organization.business.RoleVerifier;
import com.notitime.noffice.api.task.business.TaskStatusManager;
import com.notitime.noffice.domain.announcement.model.Announcement;
import com.notitime.noffice.domain.announcement.persistence.AnnouncementReadStatusRepository;
import com.notitime.noffice.domain.announcement.persistence.AnnouncementRepository;
import com.notitime.noffice.domain.member.model.Member;
import com.notitime.noffice.domain.member.persistence.MemberRepository;
import com.notitime.noffice.domain.organization.model.Organization;
import com.notitime.noffice.domain.organization.persistence.OrganizationRepository;
import com.notitime.noffice.external.firebase.FcmService;
import com.notitime.noffice.global.exception.NotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AnnouncementService {

	private final AnnouncementRepository announcementRepository;
	private final AnnouncementReadStatusRepository announcementReadStatusRepository;
	private final MemberRepository memberRepository;
	private final OrganizationRepository organizationRepository;

	private final NotificationService notificationService;
	private final ReadStatusRecoder readStatusRecoder;
	private final TaskStatusManager taskStatusManager;
	private final RoleVerifier roleVerifier;
	private final FcmService fcmService;

	public AnnouncementResponse read(Long memberId, Long announcementId) {
		roleVerifier.verifyJoinedMember(memberId, getOrganizationId(announcementId));
		readStatusRecoder.recordMemberRead(memberId, announcementId);
		return AnnouncementResponse.of(announcementRepository.findById(announcementId)
				.orElseThrow(() -> new NotFoundException(NOT_FOUND_ANNOUNCEMENT)));
	}

	@Transactional(readOnly = true)
	public AnnouncementResponses getAnnouncements() {
		List<Announcement> announcements = announcementRepository.findAll();
		return AnnouncementResponses.of(announcements.stream().map(AnnouncementResponse::of).toList());
	}

	public AnnouncementResponse create(final AnnouncementCreateRequest request) {
		Announcement announcement = createEntity(request);
		fcmService.sendAnnouncementCreatedMessage(announcement);
		notificationService.create(request, announcement);
		return AnnouncementResponse.of(announcement);
	}

	public AnnouncementResponse updateAnnouncement(final Long announcementId,
	                                               final AnnouncementUpdateRequest request) {
		Announcement existAnnouncement = announcementRepository.findById(announcementId).orElseThrow(
				() -> new NotFoundException(NOT_FOUND_ANNOUNCEMENT));
		existAnnouncement.update(request);
		return AnnouncementResponse.of(announcementRepository.save(existAnnouncement));
	}

	public void deleteAnnouncement(final Long announcementId) {
		announcementRepository.deleteById(announcementId);
	}

	public Slice<AnnouncementCoverResponse> getPublishedAnnouncements(Long organizationId, Pageable pageable) {
		return announcementRepository.findByOrganizationId(organizationId, pageable)
				.map(announcement -> {
					Long readCount = announcementReadStatusRepository.countByAnnouncementId(announcement.getId());
					Long totalMemberCount = getTotalMemberCount(organizationId);
					return AnnouncementCoverResponse.of(announcement, readCount, totalMemberCount);
				});
	}

	public ReadStatusResponse getReadMembers(Long memberId, Long announcementId) {
		List<MemberInfoResponse> members = announcementReadStatusRepository.findReadMembers(announcementId).stream()
				.map(MemberInfoResponse::from)
				.toList();
		return ReadStatusResponse.of(announcementId, members);
	}

	public ReadStatusResponse getUnreadMembers(Long memberId, Long announcementId) {
		Announcement announcement = announcementRepository.findById(announcementId)
				.orElseThrow(() -> new NotFoundException(NOT_FOUND_ANNOUNCEMENT));
		List<Member> unreadMembers = announcementReadStatusRepository.findUnReadMembers(announcement.getId());
		return ReadStatusResponse.of(announcementId, unreadMembers.stream()
				.map(MemberInfoResponse::from)
				.toList());
	}

	private Announcement createEntity(AnnouncementCreateRequest request) {
		Organization organization = organizationRepository.findById(request.organizationId())
				.orElseThrow(() -> new NotFoundException(NOT_FOUND_ORGANIZATION));
		Member member = memberRepository.findById(request.memberId())
				.orElseThrow(() -> new NotFoundException(NOT_FOUND_MEMBER));
		Announcement announcement = Announcement.create(
				request.title(),
				request.content(),
				request.endAt(),
				member,
				organization,
				request.profileImageUrl(),
				request.isFaceToFace(),
				request.placeLinkName(),
				request.placeLinkUrl()
		);
		announcement.withEndAt(request.endAt());
		announcement.withTasks(request.tasks());
		announcementRepository.save(announcement);
		taskStatusManager.assignTasks(organization, announcement);
		organization.addAnnouncement(announcement);
		return announcement;
	}

	private Long getTotalMemberCount(Long organizationId) {
		return (long) organizationRepository.findById(organizationId)
				.orElseThrow(() -> new NotFoundException(NOT_FOUND_ORGANIZATION))
				.getMembers().size();
	}

	private Long getOrganizationId(Long announcementId) {
		return announcementRepository.findById(announcementId)
				.orElseThrow(() -> new NotFoundException(NOT_FOUND_ANNOUNCEMENT))
				.getOrganization().getId();
	}

	public void modifyCover(Long memberId, Long announcementId, String coverImageUrl) {
		Announcement announcement = announcementRepository.findById(announcementId)
				.orElseThrow(() -> new NotFoundException(NOT_FOUND_ANNOUNCEMENT));
		announcement.modifyCover(coverImageUrl);
		announcementRepository.save(announcement);
	}
}
