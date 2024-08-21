package com.notitime.noffice.api.promotion.presentation.dto.response;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.NOT_REQUIRED;

import com.notitime.noffice.domain.promotion.Promotion;
import io.swagger.v3.oas.annotations.media.Schema;

public record PromotionVerifyResponse(
		@Schema(requiredMode = NOT_REQUIRED, description = "프로모션 ID", example = "1")
		Long promotionId,
		@Schema(requiredMode = NOT_REQUIRED, description = "프로모션 코드 문자열", example = "NOFFICEWELCOME")
		String promotionCode
) {
	public static PromotionVerifyResponse of(Promotion promotion) {
		return new PromotionVerifyResponse(promotion.getId(), promotion.getCode());
	}
}
