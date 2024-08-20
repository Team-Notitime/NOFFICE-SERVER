package com.notitime.noffice.global.util;

import static lombok.AccessLevel.PRIVATE;

import com.notitime.noffice.global.exception.InternalServerException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
public class HashUtil {
	public static String createHash(String code) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			return bytesToHex(digest.digest(code.getBytes(StandardCharsets.UTF_8)));
		} catch (NoSuchAlgorithmException e) {
			throw new InternalServerException("Hash 생성에 실패했습니다.");
		}
	}

	private static String bytesToHex(byte[] hash) {
		StringBuilder hexString = new StringBuilder(2 * hash.length);
		for (byte b : hash) {
			String hex = Integer.toHexString(0xff & b);
			if (hex.length() == 1) {
				hexString.append('0');
			}
			hexString.append(hex);
		}
		return hexString.toString();
	}
}