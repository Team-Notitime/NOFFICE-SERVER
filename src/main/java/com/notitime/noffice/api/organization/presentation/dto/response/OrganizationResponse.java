package com.notitime.noffice.api.organization.presentation.dto.response;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import com.notitime.noffice.domain.JoinStatus;
import com.notitime.noffice.domain.OrganizationRole;
import com.notitime.noffice.domain.organization.model.Organization;
import io.swagger.v3.oas.annotations.media.Schema;

public record OrganizationResponse(
		@Schema(description = "요청한 사용자의 조직 내 권한", example = "LEADER")
		OrganizationRole role,
		@Schema(description = "조직 ID", requiredMode = REQUIRED, example = "1")
		Long organizationId,
		@Schema(description = "조직 이름", requiredMode = REQUIRED, example = "CMC 15th : NotiTime")
		String organizationName,
		@Schema(description = "프로필 이미지", requiredMode = REQUIRED, example = "https://notitime.com/profile.png")
		String profileImage,
		@Schema(description = "가입 상태", example = "(string 1 of 4) : ACTIVE, PENDING, REJECTED, DELETED")
		JoinStatus joinStatus
) {
	public static OrganizationResponse of(Organization organization, OrganizationRole role, JoinStatus joinStatus) {
		return new OrganizationResponse(
				role,
				organization.getId(),
				organization.getName(),
				organization.getProfileImage(),
				joinStatus);
	}
}
