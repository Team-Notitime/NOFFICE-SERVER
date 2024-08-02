package com.notitime.noffice.global.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum BusinessSuccessCode implements SuccessCode {

	// OK (200 ~ 2099)
	OK(HttpStatus.OK, "NOF-2000", "요청이 성공했습니다."),
	POST_LOGIN_SUCCESS(HttpStatus.OK, "NOF-2000", "로그인에 성공하였습니다."),
	POST_REISSUE_SUCCESS(HttpStatus.OK, "NOF-2000", "액세스 토큰 재발급에 성공하였습니다."),
	GET_MEMBER_SUCCESS(HttpStatus.OK, "NOF-2001", "회원 정보 조회에 성공하였습니다."),
	GET_JOINED_ORGANIZATIONS_SUCCESS(HttpStatus.OK, "NOF-2002", "회원의 가입된 조직 조회에 성공하였습니다."),
	GET_ORGANIZATION_SUCCESS(HttpStatus.OK, "NOF-2003", "조직 정보 조회에 성공하였습니다."),
	GET_CATEGORY_SUCCESS(HttpStatus.OK, "NOF-2004", "카테고리 조회에 성공하였습니다."),
	GET_ANNOUNCEMENT_SUCCESS(HttpStatus.OK, "NOF-2050", "노티 단일 조회에 성공하였습니다."),
	GET_ANNOUNCEMENTS_SUCCESS(HttpStatus.OK, "NOF-2051", "노티 목록 조회에 성공하였습니다."),
	PUT_ANNOUNCEMENT_SUCCESS(HttpStatus.OK, "NOF-2052", "노티 수정에 성공하였습니다."),


	// CREATED (2100 ~ 2199)
	CREATED(HttpStatus.CREATED, "NOF-210", "리소스가 생성되었습니다. - 201"),
	POST_ORGANIZATION_SUCCESS(HttpStatus.CREATED, "NOF-2100", "조직 생성에 성공하였습니다."),
	POST_JOIN_ORGANIZATION_SUCCESS(HttpStatus.CREATED, "NOF-2101", "조직 가입에 성공하였습니다."),
	POST_ANNOUNCEMENT_SUCCESS(HttpStatus.CREATED, "NOF-2150", "노티 생성에 성공하였습니다."),

	// NO CONTENT (2400 ~ 2499)
	NO_CONTENT(HttpStatus.NO_CONTENT, "NOF-2400", "요청이 성공했습니다. - 204"),
	DELETE_ANNOUNCEMENT_SUCCESS(HttpStatus.NO_CONTENT, "NOF-2053", "노티 삭제에 성공하였습니다."),

	// RESET CONTENT (2500 ~ 2599)
	RESET_CONTENT(HttpStatus.RESET_CONTENT, "NOF-205", "리소스가 갱신되었습니다. - 205");

	private final HttpStatus httpStatus;
	private final String code;
	private final String message;

}