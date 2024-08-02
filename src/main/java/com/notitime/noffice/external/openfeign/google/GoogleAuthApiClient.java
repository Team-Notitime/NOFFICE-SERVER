package com.notitime.noffice.external.openfeign.google;

import com.notitime.noffice.external.openfeign.google.dto.GoogleTokenResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "GoogleAuthApiClient", url = "${client.google-auth.url}", configuration = GoogleFeignClientConfiguration.class)
public interface GoogleAuthApiClient {

	@PostMapping("/token")
	GoogleTokenResponse googleAuth(
			@RequestParam(name = "code") String code,
			@RequestParam(name = "clientId") String clientId,
			@RequestParam(name = "clientSecret") String clientSecret,
			@RequestParam(name = "redirectUri") String redirectUri,
			@RequestParam(name = "grantType") String grantType);
}
