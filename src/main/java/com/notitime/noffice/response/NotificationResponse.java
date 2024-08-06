package com.notitime.noffice.response;

public record NotificationResponse(Long notificationId, Long announcementId, String title, String content) {
}
