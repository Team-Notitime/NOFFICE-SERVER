package com.notitime.noffice.global.exception;

import com.notitime.noffice.global.response.BusinessErrorCode;
import lombok.Getter;

@Getter
public abstract class NofficeException extends RuntimeException {
	private final BusinessErrorCode errorCode;

	protected NofficeException(String message, BusinessErrorCode errorDetail) {
		super(message);
		this.errorCode = errorDetail;
	}

	public NofficeException(BusinessErrorCode errorCode) {
		this.errorCode = errorCode;
	}
}
