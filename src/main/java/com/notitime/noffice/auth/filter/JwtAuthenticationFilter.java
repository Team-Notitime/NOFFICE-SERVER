package com.notitime.noffice.auth.filter;

import static com.notitime.noffice.auth.MemberAuthentication.createMemberAuthentication;

import com.notitime.noffice.auth.MemberAuthentication;
import com.notitime.noffice.auth.jwt.JwtProvider;
import com.notitime.noffice.auth.jwt.JwtValidator;
import com.notitime.noffice.global.config.SecurityWhiteListPaths;
import com.notitime.noffice.global.exception.UnauthorizedException;
import com.notitime.noffice.global.web.BusinessErrorCode;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	public static final String BEARER = "Bearer ";
	private final JwtValidator jwtValidator;
	private final JwtProvider jwtProvider;
	private final PathMatcher pathMatcher = new AntPathMatcher();

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		if (SecurityWhiteListPaths.isWhitelisted(request)) {
			filterChain.doFilter(request, response);
			return;
		}
		final String accessToken = getAccessToken(request);
		jwtValidator.validateAccessToken(accessToken);
		doAuthentication(request, jwtProvider.getSubject(accessToken));
		filterChain.doFilter(request, response);
	}

	private String getAccessToken(HttpServletRequest request) {
		String accessToken = request.getHeader("Authorization");
		if (StringUtils.hasText(accessToken) && accessToken.startsWith(BEARER)) {
			return accessToken.substring(BEARER.length());
		}
		throw new UnauthorizedException(BusinessErrorCode.INVALID_ACCESS_TOKEN);
	}

	private void doAuthentication(
			HttpServletRequest request,
			Long memberId) {
		MemberAuthentication authentication = createMemberAuthentication(memberId);
		createAndSetWebAuthenticationDetails(request, authentication);
		SecurityContext securityContext = SecurityContextHolder.getContext();
		securityContext.setAuthentication(authentication);
	}

	private void createAndSetWebAuthenticationDetails(
			HttpServletRequest request,
			MemberAuthentication authentication) {
		WebAuthenticationDetailsSource webAuthenticationDetailsSource = new WebAuthenticationDetailsSource();
		WebAuthenticationDetails webAuthenticationDetails = webAuthenticationDetailsSource.buildDetails(request);
		authentication.setDetails(webAuthenticationDetails);
	}
}