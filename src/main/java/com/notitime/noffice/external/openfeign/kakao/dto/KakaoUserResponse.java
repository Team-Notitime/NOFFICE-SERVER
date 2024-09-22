package com.notitime.noffice.external.openfeign.kakao.dto;

public record KakaoUserResponse(
		String id,
		KakaoAccount kakaoAccount
) {
}