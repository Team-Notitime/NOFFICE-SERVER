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
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final DomainAccessProperties domainAccessProperties;
	private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	private final JwtValidator jwtValidator;
	private final JwtProvider jwtProvider;

	private static final String[] whiteList = {"/api/v1/**",
			"/health", "/swagger-ui/**", "/swagger-resources/**", "/v3/api-docs/**", "/webjars/**", "/h2-console/**"};

	@Bean
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
								.requestMatchers(whiteList).permitAll()
								.anyRequest().authenticated())
				.addFilterBefore(new JwtAuthenticationFilter(jwtValidator, jwtProvider),
						UsernamePasswordAuthenticationFilter.class)
				.addFilterBefore(new ExceptionHandlerFilter(), JwtAuthenticationFilter.class)
				.cors(corsConfigurer -> corsConfigurer.configurationSource(corsConfigurationSource()))
				.build();
	}

	@Bean
	@ConditionalOnProperty(name = "spring.h2.console.enabled", havingValue = "true")
	public WebSecurityCustomizer configureH2ConsoleEnable() {
		return web -> web.ignoring()
				.requestMatchers(PathRequest.toH2Console());
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowCredentials(true);
		configuration.addAllowedOrigin("http://localhost:8080");
		configuration.addAllowedOrigin("http://localhost:5173");
		configuration.addAllowedOrigin("http://www.googleapis.com");
		configuration.addAllowedOrigin("https://www.googleapis.com");
		configuration.addAllowedOrigin("https://oauth2.googleapis.com");
		configuration.addAllowedOrigin("https://appleid.apple.com");
		configuration.addAllowedOrigin(domainAccessProperties.getOriginServerDomain());
		configuration.addAllowedOrigin(domainAccessProperties.getCertifiedServerDomain());
		configuration.addAllowedHeader("*");
		configuration.addAllowedMethod("*");
		configuration.addExposedHeader("Authorization");

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
}