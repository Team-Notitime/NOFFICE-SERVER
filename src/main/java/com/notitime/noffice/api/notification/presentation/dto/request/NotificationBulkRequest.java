package com.notitime.noffice.api.notification.presentation.dto.request;

import java.util.List;

public record NotificationBulkRequest(String title, String content, Long announcementId, List<String> noticeBefore,
                                      List<String> noticeDate) {
}
