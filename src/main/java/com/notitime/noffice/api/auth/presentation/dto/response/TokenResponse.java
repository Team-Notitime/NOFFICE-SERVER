package com.notitime.noffice.api.auth.presentation.dto.response;

import com.notitime.noffice.auth.jwt.Token;

public record TokenResponse(
		String accessToken,
		String refreshToken
) {
	public static TokenResponse of(String accessToken, String refreshToken) {
		return new TokenResponse(accessToken, refreshToken);
	}

	public static TokenResponse toResponse(Token token) {
		return TokenResponse.of(token.accessToken(), token.refreshToken());
	}
}