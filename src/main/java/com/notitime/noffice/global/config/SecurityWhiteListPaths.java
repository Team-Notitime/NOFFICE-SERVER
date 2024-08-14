package com.notitime.noffice.global.config;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

public class SecurityWhiteListPaths {
	private static final PathMatcher pathMatcher = new AntPathMatcher();
	public static final String[] SECURITY_WHITE_LIST = {
			"/health",
			"/error",
			"/swagger-ui/**",
			"/swagger-resources/**",
			"/api/v1/member/login",
			"/api-docs/**",
			"/v3/api-docs/**",
			"/webjars/**",
			"/h2-console/**"
	};

	public static final List<String> FILTER_WHITE_LIST = List.of(
			"/api/v1/auth/google/callback",
			"/swagger-ui/**",
			"/swagger-resources/**",
			"/api-docs/**",
			"/api/v1/member/login"
	);
	
	public static boolean isWhitelisted(HttpServletRequest request) {
		String path = request.getRequestURI();
		return FILTER_WHITE_LIST.stream().anyMatch(pattern -> pathMatcher.match(pattern, path));
	}
}
