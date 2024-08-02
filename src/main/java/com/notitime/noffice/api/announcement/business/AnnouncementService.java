package com.notitime.noffice.api.announcement.business;

import com.notitime.noffice.api.announcement.persistence.AnnouncementRepository;
import com.notitime.noffice.domain.Announcement;
import com.notitime.noffice.global.exception.NotFoundException;
import com.notitime.noffice.global.response.BusinessErrorCode;
import com.notitime.noffice.response.AnnouncementResponse;
import com.notitime.noffice.response.AnnouncementResponses;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AnnouncementService {

	private final AnnouncementRepository announcementRepository;

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
}
