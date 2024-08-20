package com.notitime.noffice.api.promotion.presentation.dto;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.NOT_REQUIRED;

import io.swagger.v3.oas.annotations.media.Schema;

public record PromotionGrantRequest(
		@Schema(description = "프로모션 ID", example = "1", requiredMode = NOT_REQUIRED)
		Long promotionId,
		@Schema(description = "조직 ID", example = "1", requiredMode = NOT_REQUIRED)
		Long organizationId
) {
	public static PromotionGrantRequest of(Long promotionId, Long organizationId) {
		return new PromotionGrantRequest(promotionId, organizationId);
	}
}
