package com.notitime.noffice.global.exception;

import com.notitime.noffice.global.web.BusinessErrorCode;

public class UnauthorizedException extends NofficeException {

	public UnauthorizedException(String message, BusinessErrorCode errorCode) {
		super(message, errorCode);
	}

	public UnauthorizedException(String message) {
		super(message, BusinessErrorCode.UNAUTHORIZED);
	}

	public UnauthorizedException(BusinessErrorCode errorCode) {
		super(errorCode);
	}
}
