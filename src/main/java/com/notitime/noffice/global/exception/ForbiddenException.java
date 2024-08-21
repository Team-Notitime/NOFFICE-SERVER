package com.notitime.noffice.global.exception;

import com.notitime.noffice.global.web.BusinessErrorCode;

public class ForbiddenException extends NofficeException {
	public ForbiddenException(String message, BusinessErrorCode errorCode) {
		super(message, errorCode);
	}

	public ForbiddenException(String message) {
		super(message, BusinessErrorCode.FORBIDDEN);
	}

	public ForbiddenException(BusinessErrorCode errorCode) {
		super(errorCode);
	}
}