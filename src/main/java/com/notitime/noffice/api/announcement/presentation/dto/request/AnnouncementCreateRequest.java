package com.notitime.noffice.api.announcement.presentation.dto.request;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.NOT_REQUIRED;
import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import com.notitime.noffice.api.task.presentation.dto.request.TaskCreateRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.List;

public record AnnouncementCreateRequest(
		@Schema(description = "조직 ID", requiredMode = REQUIRED, example = "1")
		Long organizationId,
		@Schema(description = "공지 발행자 ID", requiredMode = REQUIRED, example = "1")
		Long memberId,
		@Schema(description = "공지 제목", requiredMode = REQUIRED, example = "공지 제목")
		String title,
		@Schema(description = "공지 내용", requiredMode = REQUIRED, example = "공지 내용")
		String content,
		@Schema(description = "프로필 이미지 URL", requiredMode = NOT_REQUIRED)
		String profileImageUrl,
		@Schema(description = "장소 링크 이름", requiredMode = NOT_REQUIRED)
		String placeLinkName,
		@Schema(description = "장소 링크 URL", requiredMode = NOT_REQUIRED)
		String placeLinkUrl,
		@Schema(description = "장소 대면/비대면 여부", requiredMode = NOT_REQUIRED, example = "true")
		Boolean isFaceToFace,
		@Schema(description = "투두 목록", requiredMode = NOT_REQUIRED)
		List<TaskCreateRequest> tasks,
		@Schema(description = "활동 진행 만료일", requiredMode = REQUIRED, example = "2021-07-01T 00:00:00")
		LocalDateTime endAt,
		@Schema(description = "리마인드 알림 시간 (ISO_LOCAL_DATE_TIME)", requiredMode = REQUIRED, example = "[\"2024-08-20T15:30:45\", \"2024-08-21T15:30:45\"]")
		List<LocalDateTime> noticeBefore,
		@Schema(description = "리마인드 알림 직접설정시간 (ISO_LOCAL_DATE_TIME)", requiredMode = REQUIRED, example = "[\"2024-08-20T15:30:45\", \"2024-08-21T15:30:45\"]")
		List<LocalDateTime> noticeDate) {
	public AnnouncementCreateRequest {
		profileImageUrl = (profileImageUrl != null && profileImageUrl.isEmpty()) ? null : profileImageUrl;
		placeLinkName = (placeLinkName != null && placeLinkName.isEmpty()) ? null : placeLinkName;
		placeLinkUrl = (placeLinkUrl != null && placeLinkUrl.isEmpty()) ? null : placeLinkUrl;
		isFaceToFace = (isFaceToFace != null) ? isFaceToFace : false;
		tasks = (tasks != null && tasks.isEmpty()) ? null : tasks;
	}
}
