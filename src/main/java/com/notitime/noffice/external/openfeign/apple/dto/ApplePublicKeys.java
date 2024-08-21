package com.notitime.noffice.external.openfeign.apple.dto;

import com.notitime.noffice.global.exception.UnauthorizedException;
import com.notitime.noffice.global.web.BusinessErrorCode;
import java.util.List;

public record ApplePublicKeys(
		List<ApplePublicKey> keys
) {
	public ApplePublicKey getMatchedPublicKey(String kid, String alg) {
		return keys.stream()
				.filter(applePublicKey -> applePublicKey.kid().equals(kid) && applePublicKey.alg().equals(alg))
				.findAny()
				.orElseThrow(() -> new UnauthorizedException(BusinessErrorCode.INVALID_APPLE_IDENTITY_TOKEN));
	}
}
