package com.notitime.noffice.auth.jwt;

import static com.notitime.noffice.auth.filter.JwtAuthenticationFilter.BEARER;

import com.notitime.noffice.global.exception.UnauthorizedException;
import com.notitime.noffice.global.web.BusinessErrorCode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class JwtValidator {

	private final JwtGenerator jwtGenerator;

	public void validateAccessToken(String accessToken) {
		try {
			String role = parseToken(accessToken).get(JwtGenerator.CLAIM_MEMBER_DEFAULT_ROLE, String.class);
			if (role == null) {
				throw new UnauthorizedException(BusinessErrorCode.INVALID_ACCESS_TOKEN_VALUE);
			}
		} catch (ExpiredJwtException e) {
			throw new UnauthorizedException(BusinessErrorCode.EXPIRED_ACCESS_TOKEN);
		} catch (Exception e) {
			throw new UnauthorizedException(BusinessErrorCode.INVALID_ACCESS_TOKEN_VALUE);
		}
	}

	public void validateRefreshToken(final String refreshToken) {
		try {
			parseToken(getToken(refreshToken));
		} catch (ExpiredJwtException e) {
			throw new UnauthorizedException(BusinessErrorCode.EXPIRED_REFRESH_TOKEN);
		} catch (Exception e) {
			throw new UnauthorizedException(BusinessErrorCode.INVALID_REFRESH_TOKEN_VALUE);
		}
	}

	public void equalsRefreshToken(
			final String refreshToken,
			final String storedRefreshToken) {
		if (!getToken(refreshToken).equals(storedRefreshToken)) {
			throw new UnauthorizedException(BusinessErrorCode.MISMATCH_REFRESH_TOKEN);
		}
	}

	private String getToken(final String refreshToken) {
		if (refreshToken.startsWith(BEARER)) {
			return refreshToken.substring(BEARER.length());
		}
		throw new UnauthorizedException(BusinessErrorCode.MISSING_BEARER_PREFIX);
	}

	private Claims parseToken(final String token) {
		return jwtGenerator.getJwtParser().parseClaimsJws(token).getBody();
	}
}