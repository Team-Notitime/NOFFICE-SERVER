package com.notitime.noffice.response;

import com.notitime.noffice.domain.announcement.model.Announcement;
import java.time.LocalDateTime;

public record AnnouncementCoverResponse(
		Long announcementId,
		String title,
		String profileImageUrl,
		String placeLinkName,
		String placeLinkUrl,
		LocalDateTime endAt,
		LocalDateTime createdAt,
		LocalDateTime updatedAt
) {
	public static AnnouncementCoverResponse of(Announcement announcement) {
		return new AnnouncementCoverResponse(
				announcement.getId(),
				announcement.getTitle(),
				announcement.getProfileImageUrl(),
				announcement.getPlaceLinkName(),
				announcement.getPlaceLinkUrl(),
				announcement.getEndAt(),
				announcement.getCreatedAt(),
				announcement.getUpdatedAt());
	}
}
