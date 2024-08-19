package com.notitime.noffice.api.notification.business;

import com.notitime.noffice.domain.announcement.model.Announcement;
import com.notitime.noffice.domain.announcement.persistence.AnnouncementRepository;
import com.notitime.noffice.domain.notification.model.Notification;
import com.notitime.noffice.domain.notification.persistence.NotificationRepository;
import com.notitime.noffice.request.AnnouncementCreateRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class NotificationService {

	private final NotificationRepository notificationRepository;
	private final AnnouncementRepository announcementRepository;

	public void create(AnnouncementCreateRequest request, Announcement announcement) {
		List<Notification> notifications = NotificationAssembler.assemble(request, announcement);
		notificationRepository.saveAll(notifications);
	}
}
