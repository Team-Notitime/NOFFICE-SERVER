package com.notitime.noffice.api.category.presentation;

import com.notitime.noffice.api.category.business.CategoryService;
import com.notitime.noffice.global.web.BusinessSuccessCode;
import com.notitime.noffice.global.web.NofficeResponse;
import com.notitime.noffice.api.category.presentation.dto.response.CategoryResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
public class CategoryController implements CategoryApi {
	private final CategoryService categoryService;

	@GetMapping
	public NofficeResponse<CategoryResponses> getCategories() {
		return NofficeResponse.success(BusinessSuccessCode.GET_CATEGORY_SUCCESS, categoryService.getCategories());
	}
}
