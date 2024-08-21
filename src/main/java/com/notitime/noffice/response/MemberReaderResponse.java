package com.notitime.noffice.response;

import com.notitime.noffice.domain.member.model.Member;

public record MemberReaderResponse(Long id, String name, String alias, String profileImage) {
	public static MemberReaderResponse from(Member member) {
		return new MemberReaderResponse(
				member.getId(),
				member.getName(),
				member.getAlias(),
				member.getProfileImage()
		);
	}
}
