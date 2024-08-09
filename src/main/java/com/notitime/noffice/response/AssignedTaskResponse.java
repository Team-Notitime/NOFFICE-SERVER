package com.notitime.noffice.response;

import java.util.List;

public record AssignedTaskResponse(
		Long organizationId,
		String organizationName,
		List<TaskResponse> tasks
) {
}
