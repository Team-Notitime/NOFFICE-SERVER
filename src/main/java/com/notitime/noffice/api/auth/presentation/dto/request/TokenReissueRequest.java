package com.notitime.noffice.api.auth.presentation.dto.request;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import com.notitime.noffice.domain.SocialAuthProvider;
import io.swagger.v3.oas.annotations.media.Schema;

public record TokenReissueRequest(
		@Schema(description = "리프레시 토큰", example = "1q2w3e4r", requiredMode = REQUIRED)
		String refreshToken
) {
	public static TokenReissueRequest of(String refreshToken) {
		return new TokenReissueRequest(refreshToken);
	}
}
