package com.notitime.noffice.api.notification.business;

import com.notitime.noffice.domain.announcement.model.Announcement;
import com.notitime.noffice.domain.notification.model.Notification;
import com.notitime.noffice.request.AnnouncementCreateRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class NotificationAssembler {

	public static List<Notification> assemble(AnnouncementCreateRequest request,
	                                          Announcement announcement) {
		List<Notification> notifications = new ArrayList<>();

		List<String> noticeBeforeList = request.noticeBefore();
		List<String> noticeDateList = request.noticeDate();

		for (int i = 0; i < noticeBeforeList.size(); i++) {
			LocalDateTime sendAt = calculateSendAt(
					parseDate(noticeDateList.get(i)),
					noticeBeforeList.get(i)
			);
			notifications.add(Notification.createNotification(announcement, sendAt));
		}
		return notifications;
	}

	private static LocalDateTime parseDate(String date) {
		return LocalDateTime.parse(date, Announcement.DATE_TIME_FORMATTER);
	}

	private static LocalDateTime calculateSendAt(LocalDateTime noticeDate, String noticeBefore) {
		long minutesBefore = Long.parseLong(noticeBefore);
		return noticeDate.minusMinutes(minutesBefore);
	}
}