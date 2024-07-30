package com.notitime.noffice.response;

import com.notitime.noffice.domain.organization.model.Organization;
import io.swagger.v3.oas.annotations.media.Schema;

public record OrganizationResponse(
		@Schema(description = "조직 ID") Long id,
		@Schema(description = "조직 이름") String name) {

	public static OrganizationResponse of(Organization organization) {
		return new OrganizationResponse(organization.getId(), organization.getName());
	}
}
