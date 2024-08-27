package com.notitime.noffice.api.organization.presentation.dto.request;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import io.swagger.v3.oas.annotations.media.Schema;

public record OrganizationProfileUpdateRequest(
		@Schema(description = "조직의 이미지가 저장된 Bucket URL", example = "https://bucket.ap-azname.hostname.com/example.type?", requiredMode = REQUIRED)
		String imageUrl
) {
}