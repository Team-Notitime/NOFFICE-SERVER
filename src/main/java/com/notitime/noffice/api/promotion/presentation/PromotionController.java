package com.notitime.noffice.api.promotion.presentation;

import com.notitime.noffice.api.promotion.business.PromotionCodeVerifier;
import com.notitime.noffice.api.promotion.presentation.dto.PromotionVerifyResponse;
import com.notitime.noffice.global.response.BusinessSuccessCode;
import com.notitime.noffice.global.response.NofficeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PromotionController implements PromotionApi {

	private final PromotionCodeVerifier promotionCodeVerifier;

	@GetMapping
	public NofficeResponse<PromotionVerifyResponse> verifyPromotionCode(@PathVariable String promotionCode) {
		return NofficeResponse.success(BusinessSuccessCode.VERIFY_PROMOTION_CODE_SUCCESS,
				promotionCodeVerifier.verify(promotionCode));
	}
}
