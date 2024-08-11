package com.notitime.noffice.response;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.NOT_REQUIRED;

import io.swagger.v3.oas.annotations.media.Schema;

public record PromotionVerifyResponse(
		@Schema(requiredMode = NOT_REQUIRED, description = "프로모션 ID", example = "1")
		Long promotionId,
		@Schema(requiredMode = NOT_REQUIRED, description = "프로모션 이름", example = "예술은 폭발이다")
		String promotionName,
		@Schema(requiredMode = NOT_REQUIRED, description = "프로모션 코드 문자열", example = "CODE_IS_HYPER_ART")
		String promotionCode,
		@Schema(requiredMode = NOT_REQUIRED, description = "프로모션 진행 여부", example = "false")
		Boolean isVerified
) {
}
