package com.notitime.noffice.external.openfeign.google.dto;

public record GoogleInfoResponse(
		String sub,
		String name,
		String givenName,
		String familyName,
		String picture,
		String email,
		Boolean emailVerified,
		String locale
) {
	public static GoogleInfoResponse of(String sub, String name, String givenName, String familyName, String picture,
	                                    String email, Boolean emailVerified, String locale) {
		return new GoogleInfoResponse(sub, name, givenName, familyName, picture, email, emailVerified, locale);
	}
}