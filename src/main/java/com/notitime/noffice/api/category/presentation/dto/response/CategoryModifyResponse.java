package com.notitime.noffice.api.category.presentation.dto.response;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.NOT_REQUIRED;
import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import com.notitime.noffice.domain.organization.model.Organization;
import io.swagger.v3.oas.annotations.media.Schema;

public record CategoryModifyResponse(
		@Schema(description = "카테고리 수정된 조직 식별자", requiredMode = REQUIRED, example = "1")
		Long organizationId,
		@Schema(description = "카테고리 수정된 조직 이름", requiredMode = REQUIRED, example = "CMC 15th : NotiTime")
		String organizationName,
		@Schema(description = "변경된 카테고리 목록", requiredMode = NOT_REQUIRED)
		CategoryResponses categories) {

	public static CategoryModifyResponse of(Organization organization, CategoryResponses categories) {
		return new CategoryModifyResponse(organization.getId(), organization.getName(), categories);
	}
}
