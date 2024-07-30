package com.notitime.noffice.global.config;

import com.notitime.noffice.auth.JwtAuthenticationEntryPoint;
import com.notitime.noffice.auth.filter.ExceptionHandlerFilter;
import com.notitime.noffice.auth.filter.JwtAuthenticationFilter;
import com.notitime.noffice.auth.jwt.JwtProvider;
import com.notitime.noffice.auth.jwt.JwtValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer.FrameOptionsConfig;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	private final JwtValidator jwtValidator;
	private final JwtProvider jwtProvider;
	private static final String[] whiteList = {"/api/v1/**",
			"/health", "/swagger-ui/**", "/swagger-resources/**", "/v3/api-docs/**", "/webjars/**", "/h2-console/**"};

	@Bean
	@Order(2)
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		return http
				.csrf(AbstractHttpConfigurer::disable)
				.formLogin(AbstractHttpConfigurer::disable)
				.httpBasic(AbstractHttpConfigurer::disable)
				.sessionManagement(sessionManagementConfigurer ->
						sessionManagementConfigurer
								.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.exceptionHandling(exceptionHandlingConfigurer ->
						exceptionHandlingConfigurer
								.authenticationEntryPoint(jwtAuthenticationEntryPoint))
				.authorizeHttpRequests(authorizationManagerRequestMatcherRegistry ->
						authorizationManagerRequestMatcherRegistry
								.anyRequest()
								.authenticated())
				.addFilterBefore(new JwtAuthenticationFilter(jwtValidator, jwtProvider),
						UsernamePasswordAuthenticationFilter.class)
				.addFilterBefore(new ExceptionHandlerFilter(), JwtAuthenticationFilter.class)
				.build();
	}

	@Bean
	@Order(1)
	public SecurityFilterChain whiteListFilterChain(HttpSecurity http) throws Exception {
		return http
				.authorizeHttpRequests(authorizationManagerRequestMatcherRegistry ->
						authorizationManagerRequestMatcherRegistry
								.requestMatchers(whiteList)
								.permitAll())
				.headers(headersConfigurer ->
						headersConfigurer
								.frameOptions(FrameOptionsConfig::sameOrigin))
				.csrf(AbstractHttpConfigurer::disable)
				.build();
	}

	@Bean
	@ConditionalOnProperty(name = "spring.h2.console.enabled", havingValue = "true")
	public WebSecurityCustomizer configureH2ConsoleEnable() {
		return web -> web.ignoring()
				.requestMatchers(PathRequest.toH2Console());
	}
}