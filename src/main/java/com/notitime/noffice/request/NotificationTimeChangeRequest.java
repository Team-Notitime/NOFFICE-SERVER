package com.notitime.noffice.request;

import java.util.List;

public record NotificationTimeChangeRequest(Long announcementId, List<String> noticeBefore, List<String> noticeDate) {
}
