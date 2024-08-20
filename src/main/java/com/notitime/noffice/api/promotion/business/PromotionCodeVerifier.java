package com.notitime.noffice.api.promotion.business;

import com.notitime.noffice.api.promotion.presentation.dto.PromotionVerifyResponse;
import com.notitime.noffice.domain.promotion.Promotion;
import com.notitime.noffice.domain.promotion.persistence.PromotionRepository;
import com.notitime.noffice.global.exception.NotFoundException;
import com.notitime.noffice.global.response.BusinessErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PromotionCodeVerifier {

	private final PromotionRepository promotionRepository;

	public PromotionVerifyResponse verify(String promotionCode) {
		Promotion promotion = promotionRepository.findByCode(promotionCode)
				.orElseThrow(() -> new NotFoundException(BusinessErrorCode.NOT_FOUND_PROMOTION));
		return PromotionVerifyResponse.of(promotion);
	}
}
