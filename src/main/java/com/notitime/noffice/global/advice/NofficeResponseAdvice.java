package com.notitime.noffice.global.advice;

import com.notitime.noffice.global.response.NofficeResponse;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

public class NofficeResponseAdvice implements ResponseBodyAdvice<Object> {
	@Override
	public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
		return returnType.getParameterType() == NofficeResponse.class;
	}

	@Override
	public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
	                              Class<? extends HttpMessageConverter<?>> selectedConverterType,
	                              ServerHttpRequest request, ServerHttpResponse response) {

		if (returnType.getParameterType() == NofficeResponse.class) {
			HttpStatus status = HttpStatus.valueOf(((NofficeResponse<?>) body).getHttpStatus());
			response.setStatusCode(status);
		}
		return body;
	}
}
