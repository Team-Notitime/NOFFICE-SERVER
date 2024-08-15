package com.notitime.noffice.api.image.business.dto;

import com.notitime.noffice.domain.image.model.ContentImage;
import java.util.List;

public record ContentImageResponses(List<ContentImageResponse> images) {
	public static ContentImageResponses from(List<ContentImage> images) {
		List<ContentImageResponse> responses = images.stream()
				.map(ContentImageResponse::of)
				.toList();

		return new ContentImageResponses(responses);
	}
}

