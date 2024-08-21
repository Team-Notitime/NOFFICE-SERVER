package com.notitime.noffice.api.image.presentation.dto;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import com.notitime.noffice.api.image.business.dto.ImagePurpose;
import io.swagger.v3.oas.annotations.media.Schema;

public record CommonImageResponse(
		@Schema(description = "이미지 타입", requiredMode = REQUIRED, example = "ORGANIZATION_LOGO")
		ImagePurpose type,
		@Schema(description = "이미지 ID", requiredMode = REQUIRED, example = "1")
		Long id,
		@Schema(description = "이미지 URL", requiredMode = REQUIRED, example = "https://test-image.com/image.jpg")
		String url
) {}
