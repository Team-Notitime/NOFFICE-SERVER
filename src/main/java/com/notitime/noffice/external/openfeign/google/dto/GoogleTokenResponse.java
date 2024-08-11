package com.notitime.noffice.external.openfeign.google.dto;

public record GoogleTokenResponse(
		String access_token,

		String token_type,

		String id_token
) {
	public static GoogleTokenResponse of(String accessToken, String token_type, String id_token) {
		return new GoogleTokenResponse(accessToken, token_type, id_token);
	}
}
