package com.notitime.noffice.global.exception;

import com.notitime.noffice.global.response.BusinessErrorCode;

public class BadRequestException extends NofficeException {

	public BadRequestException(String message, BusinessErrorCode errorCode) {
		super(message, errorCode);
	}

	public BadRequestException(String message) {
		super(message, BusinessErrorCode.BAD_REQUEST);
	}

	public BadRequestException(BusinessErrorCode errorCode) {
		super(errorCode);
	}
}

