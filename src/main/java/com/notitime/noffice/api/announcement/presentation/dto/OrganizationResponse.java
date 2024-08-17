package com.notitime.noffice.api.announcement.presentation.dto;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import com.notitime.noffice.domain.OrganizationRole;
import com.notitime.noffice.domain.organization.model.Organization;
import io.swagger.v3.oas.annotations.media.Schema;

public record OrganizationResponse(
		@Schema(description = "요청한 사용자의 조직 내 권한", example = "LEADER")
		OrganizationRole role,
		@Schema(requiredMode = REQUIRED, description = "조직 ID", example = "1")
		Long organizationId,
		@Schema(requiredMode = REQUIRED, description = "조직 이름", example = "CMC 15th : NotiTime")
		String organizationName,
		@Schema(requiredMode = REQUIRED, description = "프로필 이미지", example = "https://notitime.com/profile.png")
		String profileImage) {
	public static OrganizationResponse of(Organization organization) {
		return new OrganizationResponse(organization.getId(), organization.getName(), organization.getProfileImage());
	}
}
