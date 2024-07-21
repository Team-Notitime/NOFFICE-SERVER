package com.notitime.noffice.global.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum BusinessSuccessCode implements SuccessCode {

	OK(HttpStatus.OK, "NOF-200", "요청이 성공했습니다. - 200."),
	CREATED(HttpStatus.CREATED, "NOF-201", "리소스가 생성되었습니다. - 201"),
	NO_CONTENT(HttpStatus.NO_CONTENT, "NOF-204", "요청이 성공했습니다. - 204"),
	RESET_CONTENT(HttpStatus.RESET_CONTENT, "NOF-205", "리소스가 갱신되었습니다. - 205");

	private final HttpStatus httpStatus;
	private final String code;
	private final String message;

}