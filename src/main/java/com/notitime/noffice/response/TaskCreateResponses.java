package com.notitime.noffice.response;

import java.util.List;

public record TaskCreateResponses(List<TaskResponse> tasks) {
	public static TaskCreateResponses from(List<TaskResponse> tasks) {
		return new TaskCreateResponses(tasks);
	}
}
