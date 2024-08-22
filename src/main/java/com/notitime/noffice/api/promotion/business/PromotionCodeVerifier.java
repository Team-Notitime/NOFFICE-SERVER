package com.notitime.noffice.api.promotion.business;

import static com.notitime.noffice.global.web.BusinessErrorCode.NOT_FOUND_PROMOTION;

import com.notitime.noffice.api.promotion.presentation.dto.response.PromotionVerifyResponse;
import com.notitime.noffice.domain.promotion.Promotion;
import com.notitime.noffice.domain.promotion.persistence.PromotionRepository;
import com.notitime.noffice.global.exception.NotFoundException;
import com.notitime.noffice.global.util.HashUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PromotionCodeVerifier {

	private final PromotionRepository promotionRepository;

	@Value("${promotion.welcome.code}")
	private String PromotionCodeOrigin;

	@Value("${promotion.config.min-length}")
	private int CODE_MIN_LENGTH;

	@Cacheable(value = "promotion", key = "#promotionCode", condition = "@promotionCodeVerifier.isValidCodeFormat(#promotionCode)")
	public PromotionVerifyResponse verify(String promotionCode) {
		validateToHashedPromotionCode(promotionCode);
		Promotion promotion = promotionRepository.findByCode(promotionCode)
				.orElseThrow(() -> new NotFoundException(NOT_FOUND_PROMOTION));
		return PromotionVerifyResponse.of(promotion);
	}

	private void validateToHashedPromotionCode(String promotionCode) {
		if (promotionCode.length() < CODE_MIN_LENGTH ||
				!getHashedPromotionCode(promotionCode).equals(getHashedPromotionCode(PromotionCodeOrigin))) {
			throw new NotFoundException(NOT_FOUND_PROMOTION);
		}
	}

	public boolean isValidCodeFormat(String promotionCode) {
		return promotionCode.length() >= CODE_MIN_LENGTH;
	}

	private String getHashedPromotionCode(String promotionCode) {
		return HashUtil.createHash(promotionCode);
	}
}
