package com.notitime.noffice.response;

import com.notitime.noffice.domain.category.model.Category;

public record CategoryResponse(Long id, String name) {

	public static CategoryResponse of(Category category) {
		return new CategoryResponse(category.getId(), category.getName());
	}
}
