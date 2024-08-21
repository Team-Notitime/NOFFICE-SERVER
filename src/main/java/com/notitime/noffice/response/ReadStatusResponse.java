package com.notitime.noffice.response;

import java.util.List;

public record ReadStatusResponse(
		Long announcementId,
		List<MemberReaderResponse> memberList
) {
	public static ReadStatusResponse of(Long announcementId, List<MemberReaderResponse> memberList) {
		return new ReadStatusResponse(announcementId, memberList);
	}
}
