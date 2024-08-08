package com.notitime.noffice.request;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import java.util.List;

public record NotificationTimeChangeRequest(
		@Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "1", description = "공지 ID")
		Long announcementId,
		@Schema(requiredMode = RequiredMode.REQUIRED, description = "알림 시간 변경 초(second)", example = "60, 120, 180")
		List<String> noticeBefore,

		@Schema(requiredMode = RequiredMode.REQUIRED, description = "알림 시간 변경 기준 일자", example = "[\"2021-07-01T00:00:00\", \"2021-07-02T00:00:00\"]")
		List<String> noticeDate) {
}
