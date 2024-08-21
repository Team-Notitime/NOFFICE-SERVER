package com.notitime.noffice.response;

import java.util.List;

public record ReadStatusResponse(
		Long announcementId,
		List<MemberInfoResponse> memberList
) {
	public static ReadStatusResponse of(Long announcementId, List<MemberInfoResponse> memberList) {
		return new ReadStatusResponse(announcementId, memberList);
	}
}
