package com.notitime.noffice.auth.jwt;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtGenerator {

	@Value("${jwt.secret}")
	private String JWT_SECRET;
	@Value("${jwt.access-token-expiration}")
	private long ACCESS_TOKEN_EXPIRE_TIME;
	@Value("${jwt.refresh-token-expiration}")
	private long REFRESH_TOKEN_EXPIRE_TIME;

	public static final String CLAIM_MEMBER_DEFAULT_ROLE = "role";

	public String generateToken(Long memberId, boolean isAccessToken) {
		final Date now = generateNowDate();
		final Date expiration = generateExpirationDate(isAccessToken, now);

		Claims claims = Jwts.claims().setSubject(String.valueOf(memberId));
		if (isAccessToken) {
			claims.put(CLAIM_MEMBER_DEFAULT_ROLE, "ROLE_USER");
		}
		return Jwts.builder()
				.setHeaderParam(Header.TYPE, Header.JWT_TYPE)
				.setClaims(claims)
				.setIssuedAt(now)
				.setExpiration(expiration)
				.signWith(getSigningKey(), SignatureAlgorithm.HS256)
				.compact();
	}

	public JwtParser getJwtParser() {
		return Jwts.parserBuilder()
				.setSigningKey(getSigningKey())
				.build();
	}

	private Date generateNowDate() {
		return new Date();
	}

	private Date generateExpirationDate(boolean isAccessToken, Date now) {
		return new Date(now.getTime() + calculateExpirationTime(isAccessToken));
	}

	private Key getSigningKey() {
		byte[] keyBytes = Base64.getDecoder().decode(JWT_SECRET);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	private long calculateExpirationTime(boolean isAccessToken) {
		if (isAccessToken) {
			return ACCESS_TOKEN_EXPIRE_TIME;
		}
		return REFRESH_TOKEN_EXPIRE_TIME;
	}
}
