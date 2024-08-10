package com.notitime.noffice.external.openfeign.google;

import com.notitime.noffice.external.openfeign.google.dto.GoogleInfoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "GoogleApiClient", url = "https://www.googleapis.com", configuration = GoogleFeignClientConfiguration.class)
public interface GoogleApiClient {

	@GetMapping("/oauth2/v3/userinfo")
	GoogleInfoResponse googleInfo(
			@RequestHeader("Authorization") String token
	);
}