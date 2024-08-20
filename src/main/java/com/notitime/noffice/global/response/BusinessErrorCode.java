package com.notitime.noffice.global.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum BusinessErrorCode implements ErrorCode {

	// 400 Bad Request
	BAD_REQUEST(HttpStatus.BAD_REQUEST, "NOF-400", "잘못된 요청입니다."),
	FAILED_TO_LOAD_PRIVATE_KEY(HttpStatus.BAD_REQUEST, "NOF-400", "개인 키를 로드하는 데 실패했습니다."),
	INVALID_APPLE_IDENTITY_TOKEN(HttpStatus.UNAUTHORIZED, "NOF-401", "유효하지 않은 아이덴티티 토큰입니다."),
	UNSUPPORTED_ALGORITHM(HttpStatus.BAD_REQUEST, "NOF-400", "키 생성에 사용된 알고리즘을 지원하지 않습니다: "),
	INVALID_KEY_SPEC(HttpStatus.BAD_REQUEST, "NOF-400", "공개 키 생성에 잘못된 키 사양이 제공되었습니다."),
	APPLE_FEIGN_CONNECT_ERROR(HttpStatus.BAD_REQUEST, "NOF-4011", "Apple auth Feign 연결 중 오류가 발생했습니다."),
	GOOGLE_FEIGN_CONNECT_ERROR(HttpStatus.BAD_REQUEST, "NOF-4012", "Google auth Feign 연결 중 오류가 발생했습니다."),
	NOT_SUPPORTED_LOGIN_PLATFORM(HttpStatus.BAD_REQUEST, "NOF-400", "지원되지 않는 로그인 플랫폼입니다."),

	// 401 Unauthorized
	UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "NOF-401", "리소스 접근 권한이 없습니다."),
	INVALID_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED, "NOF-401", "유효하지 않은 액세스 토큰입니다."),
	INVALID_ACCESS_TOKEN_VALUE(HttpStatus.UNAUTHORIZED, "NOF-401", "액세스 토큰의 값이 일치하지 않습니다."),
	EXPIRED_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED, "NOF-401", "액세스 토큰이 만료되었습니다."),
	INVALID_REFRESH_TOKEN_VALUE(HttpStatus.UNAUTHORIZED, "NOF-401", "리프레시 토큰의 값이 일치하지 않습니다."),
	EXPIRED_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "NOF-401", "리프레시 토큰이 만료되었습니다."),
	MISMATCH_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "NOF-401", "리프레시 토큰이 일치하지 않습니다."),
	EXPIRED_IDENTITY_TOKEN(HttpStatus.UNAUTHORIZED, "NOF-401", "아이덴티티 토큰이 만료되었습니다."),
	INVALID_IDENTITY_TOKEN_VALUE(HttpStatus.UNAUTHORIZED, "NOF-401", "애플 아이덴티티 토큰의 값이 일치하지 않습니다."),
	MISSING_BEARER_PREFIX(HttpStatus.UNAUTHORIZED, "NOF-401", "Bearer가 누락되었습니다."),

	// 403 Forbidden
	FORBIDDEN(HttpStatus.FORBIDDEN, "NOF-403", "리소스에 대한 접근 권한이 없습니다."),
	FORBIDDEN_ORGANIZATION_ACCESS(HttpStatus.FORBIDDEN, "NOF-403", "조직에 대한 접근 권한이 없습니다."),
	FORBIDDEN_ROLE_ACCESS(HttpStatus.FORBIDDEN, "NOF-403", "해당 조직의 권한이 올바르지 않습니다."),

	// 404 Not Found
	NOT_FOUND(HttpStatus.NOT_FOUND, "NOF-404", "리소스를 찾을 수 없습니다."),
	NOT_FOUND_ANNOUNCEMENT(HttpStatus.NOT_FOUND, "NOF-4450", "공지사항을 찾을 수 없습니다."),
	NOT_FOUND_TASK(HttpStatus.NOT_FOUND, "NOF-4460", "요청한 투두를 찾을 수 없습니다."),
	NOT_FOUND_MEMBER(HttpStatus.NOT_FOUND, "NOF-4470", "멤버를 찾을 수 없습니다."),
	NOT_FOUND_ORGANIZATION(HttpStatus.NOT_FOUND, "NOF-4480", "조직을 찾을 수 없습니다."),
	NOT_FOUND_PROMOTION(HttpStatus.NOT_FOUND, "NOF-4490", "프로모션을 찾을 수 없습니다."),
	STORE_FILE_SIZE_EXCEEDED(HttpStatus.NOT_FOUND, "NOF-404", "업로드 가능한 파일 크기를 초과했습니다."),

	// 500 Internal Server Error
	INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "NOF-500", "서버 내부 오류가 발생했습니다.");

	private final HttpStatus httpStatus;
	private final String code;
	private final String message;
}
