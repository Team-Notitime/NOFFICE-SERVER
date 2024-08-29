package com.notitime.noffice.api.promotion.presentation.dto.request;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import io.swagger.v3.oas.annotations.media.Schema;

public record VerifyPromotionRequest(
		@Schema(requiredMode = REQUIRED, description = "프로모션 코드", example = "testcode")
		String code
) {
}
