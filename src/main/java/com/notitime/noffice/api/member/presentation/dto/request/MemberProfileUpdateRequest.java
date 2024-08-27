package com.notitime.noffice.api.member.presentation.dto.request;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import io.swagger.v3.oas.annotations.media.Schema;

public record MemberProfileUpdateRequest(
		@Schema(description = "사용자의 이미지가 저장된 Bucket URL", example = "https://bucket.ap-azname.hostname.com/example.type?", requiredMode = REQUIRED)
		String imageUrl
) {
}