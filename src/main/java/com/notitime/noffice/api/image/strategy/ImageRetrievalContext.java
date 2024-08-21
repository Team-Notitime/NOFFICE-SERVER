package com.notitime.noffice.api.image.strategy;

import com.notitime.noffice.api.image.presentation.dto.CommonImageResponse;
import java.util.ArrayList;
import java.util.List;

public class ImageRetrievalContext {
	private final List<ImageRetrievalStrategy<?>> strategies;

	public ImageRetrievalContext(List<ImageRetrievalStrategy<?>> strategies) {
		this.strategies = strategies;
	}

	public List<CommonImageResponse> retrieve(Long entityIdentifier) {
		List<CommonImageResponse> result = new ArrayList<>();
		for (ImageRetrievalStrategy<?> strategy : strategies) {
			result.addAll(strategy.getSelectableImages(entityIdentifier));
		}
		return result;
	}
}