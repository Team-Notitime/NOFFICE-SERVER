package com.notitime.noffice.global.exception;

import com.notitime.noffice.global.web.BusinessErrorCode;

public class AuthGoogleFeignException extends NofficeException {

	public AuthGoogleFeignException(String message, BusinessErrorCode errorCode) {
		super(message, errorCode);
	}

	public AuthGoogleFeignException(String message) {
		super(message, BusinessErrorCode.GOOGLE_FEIGN_CONNECT_ERROR);
	}

	public AuthGoogleFeignException(BusinessErrorCode errorCode) {
		super(errorCode);
	}
}
