package com.notitime.noffice.request;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

public record TaskBulkCreateRequest(
		@Schema(description = "투두 항목 목록", example = "[\"노피스 API 제작\", \"노피스 API 문서 작성\"]", requiredMode = REQUIRED)
		List<String> contents) {
}
