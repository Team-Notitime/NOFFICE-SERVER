package com.notitime.noffice.request;

import com.notitime.noffice.domain.SocialAuthProvider;

public record SocialAuthRequest(
		SocialAuthProvider provider,
		String code
) {
	public static SocialAuthRequest of(SocialAuthProvider provider, String code) {
		return new SocialAuthRequest(provider, code);
	}
}
