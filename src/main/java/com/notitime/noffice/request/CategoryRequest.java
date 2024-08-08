package com.notitime.noffice.request;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

public record CategoryRequest(
		@Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "[1, 2]", description = "카테고리 ID 목록")
		List<Long> categoryIds) {
}
