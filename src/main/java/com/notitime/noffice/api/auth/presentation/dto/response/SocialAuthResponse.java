package com.notitime.noffice.api.auth.presentation.dto.response;

import com.notitime.noffice.domain.SocialAuthProvider;

public record SocialAuthResponse(
		Long memberId,
		String memberName,
		SocialAuthProvider provider,
		TokenResponse token
) {
	public static SocialAuthResponse of(Long memberId, String memberName, SocialAuthProvider provider,
	                                    TokenResponse token) {
		return new SocialAuthResponse(
				memberId,
				memberName,
				provider,
				TokenResponse.of(token.accessToken(), token.refreshToken()));
	}
}