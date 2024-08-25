package com.notitime.noffice.api.task.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record TaskStatusUpdateRequest(
		@Schema(description = "투두 항목 ID", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
		Long id,
		@Schema(description = "투두 항목 상태", example = "true", requiredMode = Schema.RequiredMode.REQUIRED)
		Boolean status
) {}