package com.notitime.noffice.api.image.business.dto;

import com.notitime.noffice.domain.image.model.ContentImage;

public record ContentImageResponse(Long id, String title, String imageUrl) {

	public static ContentImageResponse of(ContentImage contentImage) {
		return new ContentImageResponse(contentImage.getId(), contentImage.getTitle(), contentImage.getImageUrl());
	}
}
