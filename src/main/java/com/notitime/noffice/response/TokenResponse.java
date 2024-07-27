package com.notitime.noffice.response;

import com.notitime.noffice.auth.jwt.Token;

public record TokenResponse(
		String accessToken,
		String refreshToken
) {
	public static TokenResponse of(String accessToken, String refreshToken) {
		return new TokenResponse("Bearer " + accessToken, "Bearer " + refreshToken);
	}

	public static TokenResponse toResponse(Token token) {
		return TokenResponse.of(token.accessToken(), token.refreshToken());
	}
}