package com.notitime.noffice.api.image.strategy;

import static com.notitime.noffice.api.image.business.dto.ImagePurpose.PROMOTION_COVER;

import com.notitime.noffice.api.image.presentation.dto.CommonImageResponse;
import com.notitime.noffice.domain.promotion.PromotionImage;
import com.notitime.noffice.domain.promotion.persistence.PromotionImageRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PromotionImageRetrievalStrategy implements ImageRetrievalStrategy<PromotionImage> {

	private final PromotionImageRepository promotionImageRepository;

	@Override
	public List<CommonImageResponse> getSelectableImages(Long organizationId) {
		return promotionImageRepository.findAll().stream()
				.map(this::toResponse)
				.toList();
	}

	@Override
	public CommonImageResponse toResponse(PromotionImage image) {
		return new CommonImageResponse(PROMOTION_COVER, image.getId(), image.getImageUrl());
	}
}