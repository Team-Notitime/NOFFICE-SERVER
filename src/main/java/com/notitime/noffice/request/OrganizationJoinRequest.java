package com.notitime.noffice.request;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import io.swagger.v3.oas.annotations.media.Schema;

public record OrganizationJoinRequest(
		@Schema(description = "사용자 ID", example = "1", requiredMode = REQUIRED)
		Long memberId,
		@Schema(description = "조직 ID", example = "1", requiredMode = REQUIRED)
		Long organizationId) {
}
