package com.notitime.noffice.global.exception;

import com.notitime.noffice.global.response.BusinessErrorCode;

public class InternalServerException extends NofficeException {

	public InternalServerException(String message, BusinessErrorCode errorCode) {
		super(message, errorCode);
	}

	public InternalServerException(String message) {
		super(message, BusinessErrorCode.INTERNAL_SERVER_ERROR);
	}

	public InternalServerException(BusinessErrorCode errorCode) {
		super(errorCode);
	}
}
