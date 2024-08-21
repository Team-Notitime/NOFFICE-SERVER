package com.notitime.noffice.external.openfeign.apple;

import com.notitime.noffice.external.openfeign.apple.dto.ApplePublicKey;
import com.notitime.noffice.external.openfeign.apple.dto.ApplePublicKeys;
import com.notitime.noffice.global.exception.BadRequestException;
import com.notitime.noffice.global.exception.InternalServerException;
import com.notitime.noffice.global.web.BusinessErrorCode;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class ApplePublicKeyGenerator {

	private static final String KEY_ID_HEADER_KEY = "kid";
	private static final String SIGN_ALGORITHM_HEADER_KEY = "alg";

	public PublicKey generatePublicKey(Map<String, String> headers, ApplePublicKeys applePublicKeys) {
		ApplePublicKey applePublicKey = applePublicKeys
				.getMatchedPublicKey(headers.get(KEY_ID_HEADER_KEY), headers.get(SIGN_ALGORITHM_HEADER_KEY));
		return getPublicKey(applePublicKey);
	}

	private PublicKey getPublicKey(ApplePublicKey applePublicKey) {

		byte[] nBytes = Base64.getUrlDecoder().decode(applePublicKey.n());
		byte[] eBytes = Base64.getUrlDecoder().decode(applePublicKey.e());

		RSAPublicKeySpec rsaPublicKeySpec = new RSAPublicKeySpec(
				new BigInteger(1, nBytes), new BigInteger(1, eBytes));

		try {
			KeyFactory keyFactory = KeyFactory.getInstance(applePublicKey.kty());
			return keyFactory.generatePublic(rsaPublicKeySpec);
		} catch (NoSuchAlgorithmException e) {
			throw new BadRequestException(BusinessErrorCode.UNSUPPORTED_ALGORITHM);
		} catch (InvalidKeySpecException e) {
			throw new BadRequestException(BusinessErrorCode.INVALID_KEY_SPEC);
		} catch (Exception e) {
			throw new InternalServerException(BusinessErrorCode.INTERNAL_SERVER_ERROR);
		}
	}
}
