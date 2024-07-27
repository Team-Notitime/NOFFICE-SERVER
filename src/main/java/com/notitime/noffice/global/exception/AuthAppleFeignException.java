package com.notitime.noffice.global.exception;

import com.notitime.noffice.global.response.BusinessErrorCode;

public class AuthAppleFeignException extends NofficeException {

	public AuthAppleFeignException(String message, BusinessErrorCode errorCode) {
		super(message, errorCode);
	}

	public AuthAppleFeignException(String message) {
		super(message, BusinessErrorCode.APPLE_FEIGN_CONNECT_ERROR);
	}

	public AuthAppleFeignException(BusinessErrorCode errorCode) {
		super(errorCode);
	}
}
