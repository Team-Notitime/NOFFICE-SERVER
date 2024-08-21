package com.notitime.noffice.api.announcement.presentation.dto;

import com.notitime.noffice.domain.organization.model.OrganizationMember;
import java.util.List;

public record OrganizationResponses(List<OrganizationResponse> organizations) {
	public static OrganizationResponses from(List<OrganizationMember> organizations) {
		List<OrganizationResponse> responses = organizations.stream()
				.map(organization -> OrganizationResponse.of(organization.getOrganization(), organization.getRole(),
						organization.getStatus()))
				.toList();
		return new OrganizationResponses(responses);
	}
}
