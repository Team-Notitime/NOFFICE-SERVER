package com.notitime.noffice.response;

import com.notitime.noffice.domain.OrganizationMember;
import java.util.List;

public record OrganizationResponses(List<OrganizationResponse> organizations) {
	public static OrganizationResponses from(List<OrganizationMember> organizations) {
		List<OrganizationResponse> responses = organizations.stream()
				.map(organization -> OrganizationResponse.of(organization.getOrganization()))
				.toList();

		return new OrganizationResponses(responses);
	}
}
