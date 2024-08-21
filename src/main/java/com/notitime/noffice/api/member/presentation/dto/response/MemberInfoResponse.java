package com.notitime.noffice.api.member.presentation.dto.response;

import com.notitime.noffice.domain.member.model.Member;

public record MemberInfoResponse(Long id, String name, String alias, String profileImage) {
	public static MemberInfoResponse from(Member member) {
		return new MemberInfoResponse(
				member.getId(),
				member.getName(),
				member.getAlias(),
				member.getProfileImage()
		);
	}
}
