package com.notitime.noffice.response;

import java.util.List;

public record AnnouncementResponses(List<AnnouncementResponse> announcements) {
	public static AnnouncementResponses of(List<AnnouncementResponse> announcements) {
		return new AnnouncementResponses(announcements);
	}
}
