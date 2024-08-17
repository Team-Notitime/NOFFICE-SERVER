package com.notitime.noffice.api.announcement.presentation.dto;

import com.notitime.noffice.domain.member.model.Member;
import com.notitime.noffice.domain.organization.model.Organization;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;

public record OrganizationJoinResponse(
		@Schema(requiredMode = RequiredMode.REQUIRED, description = "조직 ID", example = "1")
		Long organizationId,
		@Schema(requiredMode = RequiredMode.NOT_REQUIRED, description = "조직 이름", example = "Noffice")
		String organizationName,
		@Schema(requiredMode = RequiredMode.REQUIRED, description = "가입한 사용자 식별자", example = "1")
		Long memberId) {

	public static OrganizationJoinResponse from(Organization organization, Member member) {
		return new OrganizationJoinResponse(organization.getId(), organization.getName(), member.getId());
	}
}
