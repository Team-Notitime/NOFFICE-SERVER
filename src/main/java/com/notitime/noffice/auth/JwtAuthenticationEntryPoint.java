package com.notitime.noffice.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.notitime.noffice.global.response.BusinessErrorCode;
import com.notitime.noffice.global.response.NofficeResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

	private final ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
	                     AuthenticationException authException) throws IOException {
		handleException(response);
	}

	private void handleException(HttpServletResponse response) throws IOException {
		setResponse(response, HttpStatus.UNAUTHORIZED, BusinessErrorCode.UNAUTHORIZED);
	}

	private void setResponse(HttpServletResponse response, HttpStatus httpStatus, BusinessErrorCode errorCode)
			throws IOException {
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setCharacterEncoding("utf-8");
		response.setStatus(httpStatus.value());
		response.getWriter().write(objectMapper.writeValueAsString(NofficeResponse.fail(errorCode)));
	}
}