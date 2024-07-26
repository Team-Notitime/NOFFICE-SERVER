package com.notitime.noffice.global.exception;

import com.notitime.noffice.global.response.BusinessErrorCode;

public class InvalidException extends NofficeException {

	public InvalidException(String message, BusinessErrorCode errorCode) {
		super(message, errorCode);
	}

	public InvalidException(String message) {
		super(message, BusinessErrorCode.BAD_REQUEST);
	}

	public InvalidException(BusinessErrorCode errorCode) {
		super(errorCode);
	}
}

