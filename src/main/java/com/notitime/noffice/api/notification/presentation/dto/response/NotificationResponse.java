package com.notitime.noffice.api.notification.presentation.dto.response;

public record NotificationResponse(Long notificationId, Long announcementId, String title, String content) {
}
