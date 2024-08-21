package com.notitime.noffice.api.task.presentation.dto.response;

import com.notitime.noffice.domain.task.model.Task;

public record TaskModifyResponse(Long id, String content) {
	public static TaskModifyResponse from(Task task) {
		return new TaskModifyResponse(task.getId(), task.getContent());
	}
}
