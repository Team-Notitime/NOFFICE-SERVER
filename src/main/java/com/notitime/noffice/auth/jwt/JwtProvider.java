package com.notitime.noffice.auth.jwt;

import io.jsonwebtoken.JwtParser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class JwtProvider {

	private final JwtGenerator jwtGenerator;

	public Token issueTokens(Long memberId) {
		return Token.of(jwtGenerator.generateToken(memberId, true),
				jwtGenerator.generateToken(memberId, false));
	}

	public Long getSubject(String token) {
		JwtParser jwtParser = jwtGenerator.getJwtParser();
		return Long.valueOf(jwtParser.parseClaimsJws(token)
				.getBody()
				.getSubject());
	}
}
