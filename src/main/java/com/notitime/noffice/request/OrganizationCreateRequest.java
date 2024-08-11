package com.notitime.noffice.request;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.NOT_REQUIRED;
import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;

public record OrganizationCreateRequest(
		@Schema(requiredMode = REQUIRED, example = "조직 이름", description = "조직 이름")
		String name,
		@Schema(requiredMode = REQUIRED, description = "조직 분류 ID 목록")
		CategoryRequest categories,
		@Schema(requiredMode = REQUIRED, example = "https://test-image.com/cover_image.jpg", description = "조직 커버 이미지 URL")
		String profile_image,
		@Schema(requiredMode = REQUIRED, description = "조직 활동 마감일자", example = "2021-07-01")
		LocalDate endAt,
		@Schema(requiredMode = NOT_REQUIRED, description = "프로모션 코드 문자열", example = "NOFFICE_HART")
		PromotionVerifyRequest promotionCode
) {
}