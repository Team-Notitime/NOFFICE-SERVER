package com.notitime.noffice.global.web;

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
	POST_LOGOUT_SUCCESS(HttpStatus.OK, "NOF-2000", "로그아웃에 성공하였습니다."),
	GET_MEMBER_SUCCESS(HttpStatus.OK, "NOF-2001", "회원 정보 조회에 성공하였습니다."),
	GET_JOINED_ORGANIZATIONS_SUCCESS(HttpStatus.OK, "NOF-2002", "회원의 가입된 조직 조회에 성공하였습니다."),
	GET_ORGANIZATION_SUCCESS(HttpStatus.OK, "NOF-2003", "조직 정보 조회에 성공하였습니다."),
	GET_SIGNUP_INFO_SUCCESS(HttpStatus.OK, "NOF-2004", "조직 가입 정보 조회에 성공하였습니다."),
	GET_CATEGORY_SUCCESS(HttpStatus.OK, "NOF-2004", "카테고리 조회에 성공하였습니다."),
	GET_ANNOUNCEMENT_SUCCESS(HttpStatus.OK, "NOF-2050", "노티 단일 조회에 성공하였습니다."),
	GET_ANNOUNCEMENTS_SUCCESS(HttpStatus.OK, "NOF-2051", "노티 목록 조회에 성공하였습니다."),
	PUT_ANNOUNCEMENT_SUCCESS(HttpStatus.OK, "NOF-2052", "노티 수정에 성공하였습니다."),
	CHANGE_SEND_TIME_SUCCESS(HttpStatus.OK, "NOF-2072", "알림 발송 시간 변경 성공"),
	GET_ASSIGNED_TASKS_SUCCESS(HttpStatus.OK, "NOF-2061", "사용자별 투두 목록 조회 성공"),
	GET_PUBLISHED_ANNOUNCEMENTS_SUCCESS(HttpStatus.OK, "NOF-2073", "조직별 노티 페이징 조회 성공"),
	PUT_CATEGORIES_SUCCESS(HttpStatus.OK, "NOF-2082", "조직 카테고리 수정 성공"),
	GET_TASKS_BY_ANNOUNCEMENT_SUCCESS(HttpStatus.OK, "NOF-2091", "노티별 투두 조회 성공"),
	PATCH_TASK_MODIFY_SUCCESS(HttpStatus.OK, "NOF-2092", "투두 수정 성공"),
	SEND_UNREADER_REMIND_SUCCSS(HttpStatus.OK, "NOF-2500", "공지 미열람자 알림 발송 성공"),
	VERIFY_PROMOTION_CODE_SUCCESS(HttpStatus.OK, "NOF-2000", "프로모션 코드 검증 성공"),
	GET_READ_MEMBERS_SUCCESS(HttpStatus.OK, "NOF-2084", "공지 열람 사용자 목록 조회 성공"),
	GET_UNREAD_MEMBERS_SUCCESS(HttpStatus.OK, "NOF-2085", "공지 미열람 사용자 목록 조회 성공"),
	GET_PENDING_MEMBERS_SUCCESS(HttpStatus.OK, "NOF-2086", "조직 가입 대기자 목록 조회 성공"),
	GET_SELECTABLE_COVER_SUCCESS(HttpStatus.OK, "NOF-2087", "선택 가능한 공지 커버 이미지 목록 조회 성공"),

	// CREATED (2100 ~ 2199)
	CREATED(HttpStatus.CREATED, "NOF-210", "리소스가 생성되었습니다. - 201"),
	CREATED_NOTIFICATION_SUCCESS(HttpStatus.CREATED, "NOF-20710", "알림 대기열 등록 성공"),
	CREATED_BULK_NOTIFICATION_SUCCESS(HttpStatus.CREATED, "NOF-20711", "조직 전체 알림 대량 등록 성공"),
	CREATE_ORGANIZATION_SUCCESS(HttpStatus.CREATED, "NOF-2100", "조직 생성에 성공하였습니다."),
	POST_JOIN_ORGANIZATION_SUCCESS(HttpStatus.CREATED, "NOF-2101", "조직 가입에 성공하였습니다."),
	POST_ANNOUNCEMENT_SUCCESS(HttpStatus.CREATED, "NOF-2150", "노티 생성에 성공하였습니다."),
	POST_SAVE_FCM_TOKEN_SUCCESS(HttpStatus.CREATED, "NOF-210", "FCM 토큰 저장 성공"),
	POST_GRANT_PROMOTION_SUCCESS(HttpStatus.CREATED, "NOF-2111", "프로모션 가입 성공"),

	// NO CONTENT (2400 ~ 2499)
	NO_CONTENT(HttpStatus.NO_CONTENT, "NOF-2400", "요청이 성공했습니다. - 204"),
	PATCH_CHANGE_ROLES_SUCCESS(HttpStatus.NO_CONTENT, "NOF-2401", "사용자 권한 변경에 성공하였습니다."),
	DELETE_ANNOUNCEMENT_SUCCESS(HttpStatus.NO_CONTENT, "NOF-2053", "노티 삭제에 성공하였습니다."),
	DELETE_NOTIFICATION_SUCCESS(HttpStatus.NO_CONTENT, "NOF-2073", "알림 삭제 성공"),
	DELETE_TASK_SUCCESS(HttpStatus.NO_CONTENT, "NOF-2093", "투두 삭제 성공"),
	PATCH_REGISTER_MEMBER_SUCCESS(HttpStatus.NO_CONTENT, "NOF-2081", "조직 가입 승인 성공"),

	// RESET CONTENT (2500 ~ 2599)
	RESET_CONTENT(HttpStatus.RESET_CONTENT, "NOF-205", "리소스가 갱신되었습니다. - 205");


	private final HttpStatus httpStatus;
	private final String code;
	private final String message;

}