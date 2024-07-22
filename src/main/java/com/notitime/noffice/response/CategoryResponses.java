package com.notitime.noffice.response;

import com.notitime.noffice.domain.Category;
import java.util.List;

public record CategoryResponses(List<CategoryResponse> categories) {

	public static CategoryResponses from(List<Category> categories) {
		List<CategoryResponse> responses = categories.stream()
				.map(CategoryResponse::of)
				.toList();

		return new CategoryResponses(responses);
	}
}

