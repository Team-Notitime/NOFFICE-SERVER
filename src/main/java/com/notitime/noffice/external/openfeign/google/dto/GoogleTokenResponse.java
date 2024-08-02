package com.notitime.noffice.external.openfeign.google.dto;

public record GoogleTokenResponse(
		String accessToken,
		String refreshToken
) {
	public static GoogleTokenResponse of(String accessToken, String refreshToken) {
		return new GoogleTokenResponse(accessToken, refreshToken);
	}
}
