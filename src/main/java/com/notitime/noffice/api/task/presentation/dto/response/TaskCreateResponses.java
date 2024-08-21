package com.notitime.noffice.api.task.presentation.dto.response;

import java.util.List;

public record TaskCreateResponses(List<TaskResponse> tasks) {
	public static TaskCreateResponses from(List<TaskResponse> tasks) {
		return new TaskCreateResponses(tasks);
	}
}
