package com.notitime.noffice.api.notification.presentation.dto.response;

import java.util.List;

public record NotificationTimeChangeResponse(Long announcementId, List<NotificationResponse> notifications) {
}
