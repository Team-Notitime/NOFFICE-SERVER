package com.notitime.noffice.api.promotion.presentation.dto.request;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.NOT_REQUIRED;

import io.swagger.v3.oas.annotations.media.Schema;

public record PromotionVerifyRequest(
		@Schema(requiredMode = NOT_REQUIRED, description = "프로모션 코드 문자열", example = "CODE_IS_HYPER_ART")
		String promotionCode) {
	public PromotionVerifyRequest {
		promotionCode = (promotionCode != null && promotionCode.isEmpty()) ? null : promotionCode;
	}
}
