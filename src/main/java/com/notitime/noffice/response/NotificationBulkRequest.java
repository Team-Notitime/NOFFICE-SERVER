package com.notitime.noffice.response;

import java.util.List;

public record NotificationBulkRequest(String title, String content, Long announcementId, List<String> noticeBefore,
                                      List<String> noticeDate) {
}
