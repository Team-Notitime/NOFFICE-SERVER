package com.notitime.noffice.api.image.strategy;

import com.notitime.noffice.api.image.presentation.dto.CommonImageResponse;
import java.util.List;

public interface ImageRetrievalStrategy<T> {
	List<CommonImageResponse> getSelectableImages(Long entityId);

	CommonImageResponse toResponse(T entity);
}
