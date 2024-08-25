package com.notitime.noffice.api.task.presentation.dto.response;

import com.notitime.noffice.domain.task.model.Task;
import com.notitime.noffice.domain.task.model.TaskStatus;

public record TaskResponse(Long id, Long announcementId, String content, Boolean isMemberChecked) {
	public static TaskResponse from(Task task) {
		return new TaskResponse(task.getId(), task.getAnnouncement().getId(), task.getContent(), null);
	}

	public static TaskResponse from(Task task, Boolean isMemberChecked) {
		return new TaskResponse(task.getId(), task.getAnnouncement().getId(), task.getContent(), isMemberChecked);
	}

	public static TaskResponse from(TaskStatus taskStatus) {
		return new TaskResponse(taskStatus.getTask().getId(), taskStatus.getTask().getAnnouncement().getId(),
				taskStatus.getTask().getContent(), taskStatus.getIsChecked());
	}
}
