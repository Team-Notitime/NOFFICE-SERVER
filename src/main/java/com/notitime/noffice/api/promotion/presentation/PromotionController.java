package com.notitime.noffice.api.promotion.presentation;

import static com.notitime.noffice.global.response.BusinessSuccessCode.VERIFY_PROMOTION_CODE_SUCCESS;

import com.notitime.noffice.api.promotion.business.PromotionCodeVerifier;
import com.notitime.noffice.api.promotion.presentation.dto.PromotionVerifyResponse;
import com.notitime.noffice.global.response.NofficeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/promotion")
public class PromotionController implements PromotionApi {

	private final PromotionCodeVerifier promotionCodeVerifier;

	@GetMapping("/verify")
	public NofficeResponse<PromotionVerifyResponse> verifyPromotionCode(@RequestBody final String promotionCode) {
		return NofficeResponse.success(VERIFY_PROMOTION_CODE_SUCCESS, promotionCodeVerifier.verify(promotionCode));
	}
}
