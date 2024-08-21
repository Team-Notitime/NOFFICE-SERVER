package com.notitime.noffice.global.exception;

import com.notitime.noffice.global.web.BusinessErrorCode;

public class NotFoundException extends NofficeException {

	public NotFoundException(String message, BusinessErrorCode errorCode) {
		super(message, errorCode);
	}

	public NotFoundException(String message) {
		super(message, BusinessErrorCode.NOT_FOUND);
	}

	public NotFoundException(BusinessErrorCode errorCode) {
		super(errorCode);
	}
}
