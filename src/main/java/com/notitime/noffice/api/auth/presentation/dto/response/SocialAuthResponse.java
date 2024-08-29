package com.notitime.noffice.api.auth.presentation.dto.response;

import com.notitime.noffice.domain.SocialAuthProvider;

public record SocialAuthResponse(
		Long memberId,
		String memberName,
		SocialAuthProvider provider,
		Boolean isAlreadyMember,
		TokenResponse token
) {
	public static SocialAuthResponse of(Long memberId, String memberName, SocialAuthProvider provider,
	                                    Boolean isAlreadyMember, TokenResponse token) {
		return new SocialAuthResponse(
				memberId,
				memberName,
				provider,
				isAlreadyMember,
				TokenResponse.of(token.accessToken(), token.refreshToken()));
	}
}