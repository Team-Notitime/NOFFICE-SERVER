package com.notitime.noffice.request;

import com.notitime.noffice.response.TaskCreateRequest;
import java.util.List;

public record AnnouncementUpdateRequest(
		Long organizationId,
		Long memberId,
		String title,
		String content,
		String profileImageUrl,
		String placeLinkName,
		String placeLinkUrl,
		Boolean isFaceToFace,
		List<TaskCreateRequest> tasks,
		String endAt,
		List<String> noticeBefore,
		List<String> noticeDate) {
}
