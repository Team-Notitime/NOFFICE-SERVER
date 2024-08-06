package com.notitime.noffice.request;

import java.time.LocalDateTime;

public record NotificationRequest(String title, String content, Long memberId, LocalDateTime sendAt) {
}
