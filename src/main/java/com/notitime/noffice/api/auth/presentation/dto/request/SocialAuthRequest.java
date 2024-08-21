package com.notitime.noffice.api.auth.presentation.dto.request;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import com.notitime.noffice.domain.SocialAuthProvider;
import io.swagger.v3.oas.annotations.media.Schema;

public record SocialAuthRequest(
		@Schema(description = "소셜 로그인 제공자", example = "APPLE", requiredMode = REQUIRED)
		SocialAuthProvider provider,

		@Schema(description = "제공자로부터 얻은 사용자별 인가 코드", example = "1q2w3e4r", requiredMode = REQUIRED)
		String code
) {
	public static SocialAuthRequest of(SocialAuthProvider provider, String code) {
		return new SocialAuthRequest(provider, code);
	}
}
