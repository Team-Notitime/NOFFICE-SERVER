package com.notitime.noffice.response;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.NOT_REQUIRED;
import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import com.notitime.noffice.domain.organization.model.Organization;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

public record OrganizationCreateResponse(
		@Schema(requiredMode = REQUIRED, example = "조직 ID", description = "조직 ID")
		Long id,
		@Schema(requiredMode = REQUIRED, example = "조직 이름", description = "조직 이름")
		String name,
		@Schema(requiredMode = REQUIRED, description = "조직 활동 마감일자", example = "2021-07-01")
		LocalDateTime endAt,
		@Schema(requiredMode = REQUIRED, description = "조직 커버 이미지 URL", example = "https://test-image.com/cover_image.jpg")
		String profileImage,
		@Schema(requiredMode = NOT_REQUIRED, description = "프로모션 진행 여부", example = "false")
		PromotionVerifyResponse promotion
) {

	public static OrganizationCreateResponse of(Organization organization) {
		return new OrganizationCreateResponse(
				organization.getId(),
				organization.getName(),
				organization.getEndAt(),
				organization.getProfileImage(), null);
	}

	public static OrganizationCreateResponse withPromotion(Organization organization,
	                                                       PromotionVerifyResponse promotion) {
		return new OrganizationCreateResponse(
				organization.getId(),
				organization.getName(),
				organization.getEndAt(),
				organization.getProfileImage(),
				promotion);
	}
}