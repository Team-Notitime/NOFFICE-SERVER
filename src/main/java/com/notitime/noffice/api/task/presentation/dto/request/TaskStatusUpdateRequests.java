package com.notitime.noffice.api.task.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

public record TaskStatusUpdateRequests(
		@Schema(description = "투두 항목 상태 리스트", example = "[{\"id\": 1, \"status\": true}, {\"id\": 2, \"status\": false}, {\"id\": 3, \"status\": true}]", requiredMode = Schema.RequiredMode.REQUIRED)
		List<TaskStatusUpdateRequest> tasks) {
}