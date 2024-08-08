package com.notitime.noffice.request;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.notitime.noffice.response.TaskCreateRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import java.util.List;

@JsonInclude(NON_NULL)
public record AnnouncementCreateRequest(
		@Schema(requiredMode = RequiredMode.REQUIRED, example = "조직 ID") Long organizationId,
		@Schema(requiredMode = RequiredMode.REQUIRED, example = "1") Long memberId,
		@Schema(requiredMode = RequiredMode.REQUIRED, example = "공지 제목") String title,
		@Schema(requiredMode = RequiredMode.REQUIRED, example = "공지 내용") String content,
		@Schema(requiredMode = RequiredMode.NOT_REQUIRED, example = "프로필 이미지 URL") String profileImageUrl,
		@Schema(requiredMode = RequiredMode.NOT_REQUIRED, example = "장소 링크 이름") String placeLinkName,
		@Schema(requiredMode = RequiredMode.NOT_REQUIRED, example = "장소 링크 URL") String placeLinkUrl,
		@Schema(requiredMode = RequiredMode.NOT_REQUIRED, example = "true") Boolean isFaceToFace,
		@Schema(requiredMode = RequiredMode.NOT_REQUIRED) List<TaskCreateRequest> tasks,
		@Schema(requiredMode = RequiredMode.REQUIRED, example = "2021-07-01T 00:00:00") String endAt,
		@Schema(requiredMode = RequiredMode.REQUIRED, example = "[2021-07-01T00:00:00, 2021-07-02T00:00:00]") List<String> noticeBefore,
		@Schema(requiredMode = RequiredMode.REQUIRED) List<String> noticeDate) {
	public AnnouncementCreateRequest {
		profileImageUrl = (profileImageUrl != null && profileImageUrl.isEmpty()) ? null : profileImageUrl;
		placeLinkName = (placeLinkName != null && placeLinkName.isEmpty()) ? null : placeLinkName;
		placeLinkUrl = (placeLinkUrl != null && placeLinkUrl.isEmpty()) ? null : placeLinkUrl;
		isFaceToFace = (isFaceToFace != null) ? isFaceToFace : false;
		tasks = (tasks != null && tasks.isEmpty()) ? null : tasks;
	}
}
