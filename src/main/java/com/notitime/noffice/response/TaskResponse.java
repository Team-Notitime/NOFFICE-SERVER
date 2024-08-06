package com.notitime.noffice.response;

import com.notitime.noffice.domain.task.model.Task;

public record TaskResponse(Long id, Long announcementId, String content) {
	public static TaskResponse from(Task task) {
		return new TaskResponse(task.getId(), task.getAnnouncement().getId(), task.getContent());
	}
}
