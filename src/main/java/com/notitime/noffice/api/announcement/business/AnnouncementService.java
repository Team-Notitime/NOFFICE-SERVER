package com.notitime.noffice.api.announcement.business;

import static com.notitime.noffice.global.response.BusinessErrorCode.NOT_FOUND_ANNOUNCEMENT;
import static com.notitime.noffice.global.response.BusinessErrorCode.NOT_FOUND_MEMBER;
import static com.notitime.noffice.global.response.BusinessErrorCode.NOT_FOUND_ORGANIZATION;

import com.notitime.noffice.api.notification.business.NotificationService;
import com.notitime.noffice.api.organization.business.RoleVerifier;
import com.notitime.noffice.domain.announcement.model.Announcement;
import com.notitime.noffice.domain.announcement.persistence.AnnouncementRepository;
import com.notitime.noffice.domain.member.model.Member;
import com.notitime.noffice.domain.member.persistence.MemberRepository;
import com.notitime.noffice.domain.organization.model.Organization;
import com.notitime.noffice.domain.organization.persistence.OrganizationRepository;
import com.notitime.noffice.external.firebase.FcmService;
import com.notitime.noffice.global.exception.NotFoundException;
import com.notitime.noffice.request.AnnouncementCreateRequest;
import com.notitime.noffice.request.AnnouncementUpdateRequest;
import com.notitime.noffice.response.AnnouncementCoverResponse;
import com.notitime.noffice.response.AnnouncementResponse;
import com.notitime.noffice.response.AnnouncementResponses;
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
	private final MemberRepository memberRepository;
	private final OrganizationRepository organizationRepository;
	private final NotificationService notificationService;
	private final RoleVerifier roleVerifier;
	private final ReadStatusRecoder readStatusRecoder;
	private final FcmService fcmService;

	@Transactional(readOnly = true)
	public AnnouncementResponse read(Long memberId, Long announcementId) {
		roleVerifier.verifyJoinedMember(memberId, announcementId);
		recordReadStatus(memberId, announcementId);
		return AnnouncementResponse.of(announcementRepository.findById(announcementId)
				.orElseThrow(() -> new NotFoundException(NOT_FOUND_ANNOUNCEMENT)));
	}

	@Transactional(readOnly = true)
	public AnnouncementResponses getAnnouncements() {
		List<Announcement> announcements = announcementRepository.findAll();
		return AnnouncementResponses.of(announcements.stream().map(AnnouncementResponse::of).toList());
	}

	public AnnouncementResponse createAnnouncement(final AnnouncementCreateRequest request) {
		Announcement announcement = buildAnnouncementFromRequest(request);
		saveAnnouncement(announcement);
		fcmService.sendAnnouncementCreatedMessage(announcement);
		notificationService.create(request, announcement);
		return buildResponseFromAnnouncement(announcement);
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
					Long readCount = readStatusRecoder.countReader(announcement.getId());
					Long totalMemberCount = getTotalMemberCount(organizationId);
					return AnnouncementCoverResponse.of(announcement, readCount, totalMemberCount);
				});
	}

	private Announcement buildAnnouncementFromRequest(AnnouncementCreateRequest request) {
		Organization organization = organizationRepository.findById(request.organizationId())
				.orElseThrow(() -> new NotFoundException(NOT_FOUND_ORGANIZATION));
		Member member = memberRepository.findById(request.memberId())
				.orElseThrow(() -> new NotFoundException(NOT_FOUND_MEMBER));
		return Announcement.create(request, member, organization);
	}

	private void saveAnnouncement(Announcement announcement) {
		announcementRepository.save(announcement);
	}

	private AnnouncementResponse buildResponseFromAnnouncement(Announcement announcement) {
		return AnnouncementResponse.of(announcement);
	}

	private void recordReadStatus(Long memberId, Long announcementId) {
		Member member = memberRepository.findById(memberId)
				.orElseThrow(() -> new NotFoundException(NOT_FOUND_MEMBER));
		Announcement announcement = announcementRepository.findById(announcementId)
				.orElseThrow(() -> new NotFoundException(NOT_FOUND_ANNOUNCEMENT));
		readStatusRecoder.record(member, announcement);
	}

	private Long getTotalMemberCount(Long organizationId) {
		return (long) organizationRepository.findById(organizationId)
				.orElseThrow(() -> new NotFoundException(NOT_FOUND_ORGANIZATION))
				.getMembers().size();
	}
}
