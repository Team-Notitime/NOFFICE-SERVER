package com.notitime.noffice.response;

import com.notitime.noffice.domain.announcement.model.Announcement;
import java.time.LocalDateTime;

public record AnnouncementCoverResponse(
		Long announcementId,
		String title,
		String content,
		String profileImageUrl,
		String placeLinkName,
		String placeLinkUrl,
		LocalDateTime endAt,
		Long readerCount,
		Long totalMemberCount,
		LocalDateTime createdAt,
		LocalDateTime updatedAt
) {
	public static AnnouncementCoverResponse of(Announcement announcement, Long readerCount, Long totalMemberCount) {
		return new AnnouncementCoverResponse(
				announcement.getId(),
				announcement.getTitle(),
				announcement.getContent(),
				announcement.getProfileImageUrl(),
				announcement.getPlaceLinkName(),
				announcement.getPlaceLinkUrl(),
				announcement.getEndAt(),
				readerCount,
				totalMemberCount,
				announcement.getCreatedAt(),
				announcement.getUpdatedAt());
	}
}
