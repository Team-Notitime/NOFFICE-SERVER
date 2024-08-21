package com.notitime.noffice.external.openfeign.google;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.notitime.noffice.global.exception.AuthGoogleFeignException;
import com.notitime.noffice.global.web.BusinessErrorCode;
import feign.Response;
import feign.codec.ErrorDecoder;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class GoogleFeignClientErrorDecoder implements ErrorDecoder {
	private final ObjectMapper objectMapper;

	@Override
	public Exception decode(String methodKey, Response response) {
		Object body = null;
		if (response != null && response.body() != null) {
			try {
				body = objectMapper.readValue(response.body().toString(), Object.class);
			} catch (IOException e) {
				log.error("Error decoding response body", e);
			}
		}

		log.error("구글 소셜 로그인 Feign API Feign Client 호출 중 오류가 발생되었습니다. body: {}", body);

		return new AuthGoogleFeignException("구글 소셜 로그인 Feign API Feign Client 호출 중 오류가 발생되었습니다.",
				BusinessErrorCode.GOOGLE_FEIGN_CONNECT_ERROR);
	}
}