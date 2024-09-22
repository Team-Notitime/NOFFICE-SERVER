package com.notitime.noffice.external.openfeign.apple;

import com.notitime.noffice.external.openfeign.apple.dto.ApplePublicKeys;
import com.notitime.noffice.external.openfeign.apple.dto.AuthorizedMemberInfo;
import io.jsonwebtoken.Claims;
import java.security.PublicKey;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class AppleOAuthProvider {

	private final AppleIdentityTokenParser appleIdentityTokenParser;
	private final ApplePublicKeyGenerator applePublicKeyGenerator;

	@Value("${oauth.apple.client-id}")
	private String clientId;

	public AuthorizedMemberInfo getAppleUserInfo(final String identityToken, final String name,
	                                             ApplePublicKeys applePublicKeys) {
		Map<String, String> headers = appleIdentityTokenParser.parseHeaders(identityToken);
		PublicKey applePublicKey = applePublicKeyGenerator.generatePublicKey(headers, applePublicKeys);
		Claims claims = appleIdentityTokenParser.parsePublicKeyAndGetClaims(identityToken, applePublicKey);
		return AuthorizedMemberInfo.of(
				claims.get("sub").toString(),
				name,
				claims.get("email").toString());
	}
}
