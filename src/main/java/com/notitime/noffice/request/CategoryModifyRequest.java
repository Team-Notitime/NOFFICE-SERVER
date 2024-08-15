package com.notitime.noffice.request;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

public record CategoryModifyRequest(
		@Schema(description = "수정하려는 조직 ID", requiredMode = REQUIRED, example = "1")
		Long organizationId,
		@Schema(description = "수정하려는 카테고리 ID 목록", requiredMode = REQUIRED, example = "[1, 2, 3]")
		List<Long> categoryIds
) {
}
