package com.notitime.noffice.response;

import com.notitime.noffice.domain.member.model.Member;

public record MemberResponse(Long id, String name, String alias, String profileImage,
                             OrganizationResponses organizations) {
	public static MemberResponse of(Member member) {
		return new MemberResponse(member.getId(), member.getName(), member.getAlias(),
				member.getProfileImage(), OrganizationResponses.from(member.getOrganizations()));
	}
}
