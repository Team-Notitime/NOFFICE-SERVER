package com.notitime.noffice.api.task.presentation.dto.request;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

public record TaskStatusUpdateRequest(
		@Schema(description = "투두 항목 ID 목록", example = "[1, 2, 3]", requiredMode = REQUIRED)
		List<Long> taskIds) {
}
