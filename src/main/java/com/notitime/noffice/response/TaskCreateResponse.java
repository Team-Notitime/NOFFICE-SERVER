package com.notitime.noffice.response;

public record TaskCreateResponse(Long id, Long announcementId, String content) {
	public static TaskCreateResponse of(Long id, Long announcementId, String content) {
		return new TaskCreateResponse(id, announcementId, content);
	}
}
