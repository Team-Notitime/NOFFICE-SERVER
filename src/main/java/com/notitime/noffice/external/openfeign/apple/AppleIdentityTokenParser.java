package com.notitime.noffice.external.openfeign.apple;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.notitime.noffice.global.exception.UnauthorizedException;
import com.notitime.noffice.global.response.BusinessErrorCode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import java.security.PublicKey;
import java.util.Base64;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class AppleIdentityTokenParser {

	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	public Map<String, String> parseHeaders(String identityToken) {
		try {
			String encodedHeader = identityToken.split("\\.")[0];
			String decodedHeader = new String(Base64.getUrlDecoder().decode(encodedHeader));
			return OBJECT_MAPPER.readValue(decodedHeader, new TypeReference<Map<String, String>>() {});
		} catch (JsonProcessingException | ArrayIndexOutOfBoundsException e) {
			throw new UnauthorizedException(BusinessErrorCode.INVALID_APPLE_IDENTITY_TOKEN);
		}
	}

	public Claims parsePublicKeyAndGetClaims(String identityToken, PublicKey publicKey) {
		try {
			return Jwts.parserBuilder()
					.setSigningKey(publicKey)
					.build()
					.parseClaimsJws(identityToken)
					.getBody();

		} catch (ExpiredJwtException e) {
			throw new UnauthorizedException(BusinessErrorCode.EXPIRED_IDENTITY_TOKEN);
		} catch (UnsupportedJwtException | MalformedJwtException | IllegalArgumentException e) {
			throw new UnauthorizedException(BusinessErrorCode.INVALID_IDENTITY_TOKEN_VALUE);
		}
	}
}
