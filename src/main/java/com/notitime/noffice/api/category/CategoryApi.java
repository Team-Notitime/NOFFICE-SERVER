package com.notitime.noffice.api.category;

import com.notitime.noffice.global.response.NofficeResponse;
import com.notitime.noffice.response.CategoryResponses;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "카테고리", description = "조직 분류 카테고리 관련 API")
public interface CategoryApi {
	
	@Operation(summary = "전체 카테고리 조회", description = "등록 가능한 모든 카테고리를 조회합니다.", responses = {
			@ApiResponse(responseCode = "200", description = "카테고리 목록 조회 성공"),
			@ApiResponse(responseCode = "404", description = "등록된 카테고리가 없습니다.")
	})
	NofficeResponse<CategoryResponses> getCategories();
}
