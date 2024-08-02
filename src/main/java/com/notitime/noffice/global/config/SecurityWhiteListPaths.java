package com.notitime.noffice.global.config;

import java.util.List;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

public class SecurityWhiteListPaths {
	private static final PathMatcher pathMatcher = new AntPathMatcher();
	public static final List<String> FILTER_WHITE_LIST = List.of(
			"/health",
			"/api/v1/**",
			"/swagger-ui/**",
			"/swagger-resources/**",
			"/api-docs/**",
			"/v3/api-docs/**",
			"/webjars/**",
			"/h2-console/**"
	);

	public static final String[] SECURITY_WHITE_LIST = {
			"/health",
			"/api/v1/**",
			"/v1/user/**",
			"/error",
			"/swagger-ui/**",
			"/swagger-resources/**",
			"/api-docs/**",
			"/v3/api-docs/**",
			"/webjars/**",
			"/h2-console/**"
	};
}
