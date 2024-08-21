package com.notitime.noffice.api.organization.presentation.dto.response;

import com.notitime.noffice.domain.organization.model.Organization;
import io.swagger.v3.oas.annotations.media.Schema;

public record OrganizationSignupResponse(
		@Schema(description = "조직 ID", example = "1")
		Long organizationId,
		@Schema(description = "조직 이름", example = "CMC 15th : NotiTime")
		String organizationName,
		@Schema(description = "프로필 이미지", example = "https://notitime.com/profile.png")
		String profileImage
) {
	public static OrganizationSignupResponse of(Organization organization) {
		return new OrganizationSignupResponse(
				organization.getId(),
				organization.getName(),
				organization.getProfileImage());
	}
}
