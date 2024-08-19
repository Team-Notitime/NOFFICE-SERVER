package com.notitime.noffice.external.firebase;

import java.util.List;

public record FCMCreateResponse(
		String title,
		String body,
		List<String> targetToken
) {
	public static FCMCreateResponse of(String title, String body, List<String> targetToken) {
		return new FCMCreateResponse(title, body, targetToken);
	}
}
