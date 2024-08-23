package com.notitime.noffice.api.task.presentation.dto.response;

import com.notitime.noffice.domain.task.model.Task;
import com.notitime.noffice.domain.task.model.TaskStatus;

public record TaskResponse(Long id, Long announcementId, String content) {
	public static TaskResponse from(Task task) {
		return new TaskResponse(task.getId(), task.getAnnouncement().getId(), task.getContent());
	}

	public static TaskResponse from(TaskStatus taskStatus) {
		Task task = taskStatus.getTask();
		return new TaskResponse(task.getId(), task.getAnnouncement().getId(), task.getContent());
	}
}
