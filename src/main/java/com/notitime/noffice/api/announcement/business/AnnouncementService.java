package com.notitime.noffice.api.announcement.business;

import com.notitime.noffice.api.member.business.MemberService;
import com.notitime.noffice.api.notification.business.NotificationService;
import com.notitime.noffice.api.organization.business.OrganizationService;
import com.notitime.noffice.domain.announcement.model.Announcement;
import com.notitime.noffice.domain.announcement.persistence.AnnouncementRepository;
import com.notitime.noffice.domain.member.model.Member;
import com.notitime.noffice.domain.organization.model.Organization;
import com.notitime.noffice.global.exception.NotFoundException;
import com.notitime.noffice.global.response.BusinessErrorCode;
import com.notitime.noffice.request.AnnouncementCreateRequest;
import com.notitime.noffice.request.AnnouncementUpdateRequest;
import com.notitime.noffice.response.AnnouncementResponse;
import com.notitime.noffice.response.AnnouncementResponses;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AnnouncementService {

	private final AnnouncementRepository announcementRepository;
	private final MemberService memberService;
	private final OrganizationService organizationService;
	private final NotificationService notificationService;

	@Transactional(readOnly = true)
	public AnnouncementResponse getAnnouncement(final Long announcementId) {
		return AnnouncementResponse.of(announcementRepository.findById(announcementId)
				.orElseThrow(() -> new NotFoundException(BusinessErrorCode.NOT_FOUND_ANNOUNCEMENT)));
	}

	@Transactional(readOnly = true)
	public AnnouncementResponses getAnnouncements() {
		List<Announcement> announcements = announcementRepository.findAll();
		return AnnouncementResponses.of(announcements.stream().map(AnnouncementResponse::of).toList());
	}

	public AnnouncementResponse createAnnouncement(final AnnouncementCreateRequest request) {
		Announcement announcement = buildAnnouncementFromRequest(request);
		saveAnnouncement(announcement);
		createNotificationForAnnouncement(request, announcement);
		return buildResponseFromAnnouncement(announcement);
	}

	public AnnouncementResponse updateAnnouncement(final Long announcementId,
	                                               final AnnouncementUpdateRequest announcementCreateRequest) {
		Announcement existAnnouncement = announcementRepository.findById(announcementId).orElseThrow(
				() -> new NotFoundException(BusinessErrorCode.NOT_FOUND_ANNOUNCEMENT));
		return AnnouncementResponse.of(announcementRepository.save(existAnnouncement));
	}

	public void deleteAnnouncement(final Long announcementId) {
		announcementRepository.deleteById(announcementId);
	}

	private Announcement buildAnnouncementFromRequest(AnnouncementCreateRequest request) {
		LocalDateTime endAt = Optional.ofNullable(request.endAt())
				.map(date -> LocalDateTime.parse(date, Announcement.DATE_TIME_FORMATTER))
				.orElse(null);
		Announcement announcement = Announcement.createAnnouncement(
				request.title(),
				request.content(),
				endAt,
				getMemberEntity(request.memberId()),
				getOrganizationEntity(request.organizationId())
		);
		Optional.ofNullable(request.profileImageUrl()).ifPresent(announcement::withProfileImageUrl);
		Optional.ofNullable(request.isFaceToFace()).ifPresent(announcement::withIsFaceToFace);
		Optional.ofNullable(request.placeLinkName()).ifPresent(announcement::withPlaceLinkName);
		Optional.ofNullable(request.placeLinkUrl()).ifPresent(announcement::withPlaceLinkUrl);

		return announcement;
	}

	private void saveAnnouncement(Announcement announcement) {
		announcementRepository.save(announcement);
	}

	private void createNotificationForAnnouncement(AnnouncementCreateRequest request, Announcement announcement) {
		notificationService.createNotification(request, announcement);
	}

	private AnnouncementResponse buildResponseFromAnnouncement(Announcement announcement) {
		return AnnouncementResponse.of(announcement);
	}

	private Member getMemberEntity(Long memberId) {
		return memberService.getMemberEntity(memberId);
	}

	private Organization getOrganizationEntity(Long organizationId) {
		return organizationService.getOrganizationEntity(organizationId);
	}
}
