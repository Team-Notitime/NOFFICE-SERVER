package com.notitime.noffice.api.promotion.presentation;

import static com.notitime.noffice.global.web.BusinessSuccessCode.POST_GRANT_PROMOTION_SUCCESS;
import static com.notitime.noffice.global.web.BusinessSuccessCode.VERIFY_PROMOTION_CODE_SUCCESS;

import com.notitime.noffice.api.promotion.business.PromotionCodeVerifier;
import com.notitime.noffice.api.promotion.business.PromotionService;
import com.notitime.noffice.api.promotion.presentation.dto.request.PromotionGrantRequest;
import com.notitime.noffice.api.promotion.presentation.dto.request.VerifyPromotionRequest;
import com.notitime.noffice.api.promotion.presentation.dto.response.PromotionVerifyResponse;
import com.notitime.noffice.global.web.NofficeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/promotion")
public class PromotionController implements PromotionApi {

	private final PromotionCodeVerifier promotionCodeVerifier;
	private final PromotionService promotionService;

	@GetMapping("/verify")
	public NofficeResponse<PromotionVerifyResponse> verifyPromotionCode(
			@RequestBody final VerifyPromotionRequest request) {
		return NofficeResponse.success(VERIFY_PROMOTION_CODE_SUCCESS, promotionCodeVerifier.verify(request.code()));
	}

	@PostMapping("/grant-organization")
	public NofficeResponse<Void> grantOrganization(@RequestBody final PromotionGrantRequest request) {
		promotionService.grantOrganization(request);
		return NofficeResponse.success(POST_GRANT_PROMOTION_SUCCESS);
	}
}
