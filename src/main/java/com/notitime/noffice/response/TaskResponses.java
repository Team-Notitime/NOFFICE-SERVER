package com.notitime.noffice.response;

import com.notitime.noffice.domain.task.model.Task;
import java.util.List;

public record TaskResponses(List<TaskResponse> tasks) {
	public static TaskResponses from(List<Task> tasks) {
		return new TaskResponses(tasks.stream().map(TaskResponse::from).toList());
	}
}
