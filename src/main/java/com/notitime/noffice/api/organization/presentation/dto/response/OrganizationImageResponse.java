package com.notitime.noffice.api.organization.presentation.dto.response;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.NOT_REQUIRED;
import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import com.notitime.noffice.api.image.presentation.dto.CommonImageResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

public record OrganizationImageResponse(
		@Schema(requiredMode = REQUIRED, example = "조직 ID", description = "조직 ID")
		Long id,
		@Schema(requiredMode = NOT_REQUIRED, description = "조직 커버 이미지 URL 리스트")
		List<CommonImageResponse> images
) {
	public static OrganizationImageResponse of(Long id, List<CommonImageResponse> images) {
		return new OrganizationImageResponse(id, images);
	}
}
