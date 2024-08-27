package com.notitime.noffice.api.member.presentation.dto.request;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import io.swagger.v3.oas.annotations.media.Schema;

public record MemberAliasUpdateRequest(
		@Schema(description = "사용자가 변경하려는 별명", example = "변경될 닉네임", requiredMode = REQUIRED)
		String alias
) {
}
