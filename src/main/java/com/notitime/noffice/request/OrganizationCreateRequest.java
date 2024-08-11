package com.notitime.noffice.request;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.NOT_REQUIRED;
import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.List;

public record OrganizationCreateRequest(
		@Schema(requiredMode = REQUIRED, description = "조직 이름", example = "조지기름")
		String name,
		@Schema(requiredMode = REQUIRED, description = "카테고리 ID 목록", example = "[1, 2]")
		List<Long> categoryList,
		@Schema(requiredMode = NOT_REQUIRED, description = "조직 커버 이미지 URL", example = "https://test-image.com/cover_image.jpg")
		String profileImage,
		@Schema(requiredMode = NOT_REQUIRED, description = "조직 활동 마감일자", example = "2021-07-01")
		LocalDateTime endAt,
		@Schema(requiredMode = NOT_REQUIRED, description = "프로모션 코드 문자열", example = "NOFFICE_HART")
		PromotionVerifyRequest promotionCode
) {
}