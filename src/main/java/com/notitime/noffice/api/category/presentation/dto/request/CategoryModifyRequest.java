package com.notitime.noffice.api.category.presentation.dto.request;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

public record CategoryModifyRequest(
		@Schema(description = "수정하려는 카테고리 ID 목록", requiredMode = REQUIRED, example = "[1, 2, 3]")
		List<Long> categoryIds
) {
}
