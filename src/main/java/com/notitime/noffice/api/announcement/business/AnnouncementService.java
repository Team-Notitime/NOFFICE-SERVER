package com.notitime.noffice.api.announcement.business;

import com.notitime.noffice.api.notification.business.NotificationService;
import com.notitime.noffice.domain.announcement.model.Announcement;
import com.notitime.noffice.domain.announcement.persistence.AnnouncementRepository;
import com.notitime.noffice.global.exception.NotFoundException;
import com.notitime.noffice.global.response.BusinessErrorCode;
import com.notitime.noffice.request.AnnouncementCreateRequest;
import com.notitime.noffice.request.AnnouncementUpdateRequest;
import com.notitime.noffice.response.AnnouncementResponse;
import com.notitime.noffice.response.AnnouncementResponses;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AnnouncementService {

	private final AnnouncementRepository announcementRepository;
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
		return Announcement.createAnnouncement(
				request.title(),
				request.content(),
				request.profileImageUrl(),
				request.isFaceToFace(),
				request.placeLinkName(),
				request.placeLinkUrl(),
				LocalDateTime.parse(request.endAt(), Announcement.DATE_TIME_FORMATTER));
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
}
