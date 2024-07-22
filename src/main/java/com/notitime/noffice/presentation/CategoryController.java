package com.notitime.noffice.presentation;

import com.notitime.noffice.global.response.BusinessSuccessCode;
import com.notitime.noffice.global.response.NofficeResponse;
import com.notitime.noffice.response.CategoryResponses;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "카테고리", description = "조직 내 카테고리 조회 API")
@RestController
@RequestMapping("/v1/category")
@RequiredArgsConstructor
public class CategoryController {

	@Operation(summary = "전체 카테고리 조회")
	@GetMapping
	public NofficeResponse<CategoryResponses> getCategories() {
		return NofficeResponse.success(BusinessSuccessCode.GET_CATEGORY_SUCCESS);
	}

	@Operation(summary = "특정 조직의 카테고리 조회")
	@GetMapping("/organization/{organizationId}")
	public NofficeResponse<CategoryResponses> getCategoriesByOrganization(@PathVariable Long organizationId) {
		return NofficeResponse.success(BusinessSuccessCode.GET_CATEGORY_SUCCESS);
	}
}