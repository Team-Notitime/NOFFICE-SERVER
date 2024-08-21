package com.notitime.noffice.api.notification.business;

import static com.notitime.noffice.domain.notification.model.NoticeType.DEFAULT;
import static com.notitime.noffice.domain.notification.model.NoticeType.MANUAL;

import com.notitime.noffice.domain.announcement.model.Announcement;
import com.notitime.noffice.domain.notification.model.Notification;
import com.notitime.noffice.api.announcement.presentation.dto.request.AnnouncementCreateRequest;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class NotificationAssembler {
	public static List<Notification> assemble(AnnouncementCreateRequest request,
	                                          Announcement announcement) {
		return Stream.concat(
				request.noticeBefore().stream()
						.map(noticeBefore -> Notification.createNotification(announcement, noticeBefore, DEFAULT)),
				request.noticeDate().stream()
						.map(noticeDate -> Notification.createNotification(announcement, noticeDate, MANUAL))
		).collect(Collectors.toList());
	}
}