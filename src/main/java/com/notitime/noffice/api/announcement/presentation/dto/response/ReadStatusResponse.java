package com.notitime.noffice.api.announcement.presentation.dto.response;

import com.notitime.noffice.api.member.presentation.dto.response.MemberInfoResponse;
import java.util.List;

public record ReadStatusResponse(
		Long announcementId,
		List<MemberInfoResponse> memberList
) {
	public static ReadStatusResponse of(Long announcementId, List<MemberInfoResponse> memberList) {
		return new ReadStatusResponse(announcementId, memberList);
	}
}
