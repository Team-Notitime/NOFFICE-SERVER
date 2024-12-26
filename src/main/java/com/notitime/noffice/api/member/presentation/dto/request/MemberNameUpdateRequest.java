package com.notitime.noffice.api.member.presentation.dto.request;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import io.swagger.v3.oas.annotations.media.Schema;

public record MemberNameUpdateRequest(
		@Schema(description = "사용자가 변경하려는 이름", example = "변경될 이름", requiredMode = REQUIRED)
		String name
) {
}

