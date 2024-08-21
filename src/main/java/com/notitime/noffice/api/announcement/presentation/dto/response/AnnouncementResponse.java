package com.notitime.noffice.api.announcement.presentation.dto.response;

import com.notitime.noffice.domain.announcement.model.Announcement;
import com.notitime.noffice.domain.notification.model.Notification;
import java.time.LocalDateTime;
import java.util.List;

public record AnnouncementResponse(
		Long organizationId,
		Long memberId,
		Long announcementId,
		String title,
		String content,
		String profileImageUrl,
		String placeLinkName,
		String placeLinkUrl,
		LocalDateTime endAt,
		List<LocalDateTime> noticeAt,
		LocalDateTime createdAt,
		LocalDateTime updatedAt
) {

	public static AnnouncementResponse of(Announcement announcement) {
		return new AnnouncementResponse(
				announcement.getOrganization().getId(),
				announcement.getMember().getId(),
				announcement.getId(),
				announcement.getTitle(),
				announcement.getContent(),
				announcement.getProfileImageUrl(),
				announcement.getPlaceLinkName(),
				announcement.getPlaceLinkUrl(),
				announcement.getEndAt(),
				announcement.getNotifications().stream()
						.map(Notification::getSendAt).toList(),
				announcement.getCreatedAt(),
				announcement.getUpdatedAt());
	}
}
