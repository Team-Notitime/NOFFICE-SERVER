package com.notitime.noffice.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.notitime.noffice.global.config.SecurityWhiteListPaths;
import com.notitime.noffice.global.web.BusinessErrorCode;
import com.notitime.noffice.global.web.NofficeResponse;
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

	public void commence(HttpServletRequest request, HttpServletResponse response,
	                     AuthenticationException authException) throws IOException {
		if (!SecurityWhiteListPaths.isWhitelisted(request)) {
			handleException(response);
		}
	}

	private void handleException(HttpServletResponse response) throws IOException {
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setCharacterEncoding("utf-8");
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		response.getWriter()
				.write(objectMapper.writeValueAsString(NofficeResponse.fail(BusinessErrorCode.UNAUTHORIZED)));
	}
}