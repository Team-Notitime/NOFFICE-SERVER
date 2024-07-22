package com.notitime.noffice.request;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

public record CategoryRequest(
		@Schema(description = "카테고리 아이디 리스트", example = "1,3,5")
		List<Long> categoryIds) {
}
