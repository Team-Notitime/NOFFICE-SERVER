package com.notitime.noffice.external.openfeign.kakao;

import com.notitime.noffice.external.openfeign.kakao.dto.KakaoUserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "kakaoUserInfoClient", url = "https://kapi.kakao.com")
public interface KakaoUserInfoClient {

	@GetMapping(value = "/v2/user/me")
	KakaoUserResponse getUserInformation(@RequestHeader(HttpHeaders.AUTHORIZATION) String accessToken);
}