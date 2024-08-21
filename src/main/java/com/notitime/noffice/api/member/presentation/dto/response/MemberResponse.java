package com.notitime.noffice.api.member.presentation.dto.response;

import com.notitime.noffice.api.organization.presentation.dto.response.OrganizationResponse;
import com.notitime.noffice.api.organization.presentation.dto.response.OrganizationResponses;
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
