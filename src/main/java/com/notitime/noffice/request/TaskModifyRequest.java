package com.notitime.noffice.request;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import io.swagger.v3.oas.annotations.media.Schema;

public record TaskModifyRequest(
		@Schema(description = "투두 항목 ID", example = "1", requiredMode = REQUIRED)
		Long id,
		@Schema(description = "투두 항목", example = "노피스 API 제작", requiredMode = REQUIRED)
		String content) {
}
