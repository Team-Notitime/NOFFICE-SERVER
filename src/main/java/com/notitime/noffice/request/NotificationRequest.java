package com.notitime.noffice.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record NotificationRequest(
		@Schema(requiredMode = RequiredMode.REQUIRED, example = "알림 제목") String title,
		@Schema(requiredMode = RequiredMode.NOT_REQUIRED, example = "알림 내용") String content,
		@Schema(requiredMode = RequiredMode.REQUIRED, example = "1") Long memberId,
		@Schema(requiredMode = RequiredMode.REQUIRED, example = "2021-07-01T00:00:00") LocalDateTime sendAt
) {
	public NotificationRequest {
		content = (content != null && content.isEmpty()) ? null : content;
	}
}
