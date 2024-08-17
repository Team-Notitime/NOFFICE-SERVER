package com.notitime.noffice.response;

import com.notitime.noffice.api.announcement.presentation.dto.OrganizationResponse;
import com.notitime.noffice.api.announcement.presentation.dto.OrganizationResponses;
import com.notitime.noffice.domain.member.model.Member;
import java.util.List;

public record MemberResponse(Long id, String name, String alias, String profileImage,
                             List<OrganizationResponse> organizations) {
	public static MemberResponse of(Member member) {
		return new MemberResponse(
				member.getId(),
				member.getName(),
				member.getAlias(),
				member.getProfileImage(),
				OrganizationResponses.from(member.getOrganizations()).organizations()
		);
	}
}
