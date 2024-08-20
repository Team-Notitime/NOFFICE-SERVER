package com.notitime.noffice.api.promotion.presentation;

import com.notitime.noffice.global.response.NofficeResponse;
import com.notitime.noffice.response.PromotionVerifyResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestBody;


@Tag(name = "프로모션", description = "프로모션 관련 API")
interface PromotionApi {

	@Operation(summary = "프로모션 코드 검증", description = "프로모션 코드를 검증합니다.", responses = {
			@ApiResponse(responseCode = "200", description = "프로모션 코드 검증 성공"),
			@ApiResponse(responseCode = "404", description = "프로모션 코드가 존재하지 않습니다.")
	})
	NofficeResponse<PromotionVerifyResponse> verifyPromotionCode(@RequestBody final String promotionCode);
}