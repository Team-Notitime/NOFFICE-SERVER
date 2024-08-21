package com.notitime.noffice.api.task.presentation.dto.response;

import java.util.List;

public record AssignedTaskResponse(
		Long organizationId,
		String organizationName,
		List<TaskResponse> tasks
) {
}
