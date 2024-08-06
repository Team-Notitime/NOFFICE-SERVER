package com.notitime.noffice.response;

import java.util.List;

public record NotificationTimeChangeResponse(Long announcementId, List<NotificationResponse> notifications) {
}
