package com.notitime.noffice.global.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum BusinessErrorCode implements ErrorCode {

	BAD_REQUEST(HttpStatus.BAD_REQUEST, "NOF-400", "잘못된 요청입니다."),
	FAILED_TO_LOAD_PRIVATE_KEY(HttpStatus.BAD_REQUEST, "NOF-400", "개인 키를 로드하는 데 실패했습니다."),
	INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "NOF-500", "서버 내부 오류가 발생했습니다."),
	INVALID_APPLE_IDENTITY_TOKEN(HttpStatus.UNAUTHORIZED, "NOF-401", "유효하지 않은 아이덴티티 토큰입니다."),
	UNSUPPORTED_ALGORITHM(HttpStatus.BAD_REQUEST, "NOF-400", "키 생성에 사용된 알고리즘을 지원하지 않습니다: "),
	INVALID_KEY_SPEC(HttpStatus.BAD_REQUEST, "NOF-400", "공개 키 생성에 잘못된 키 사양이 제공되었습니다."),
	UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "NOF-401", "인증되지 않은 사용자입니다.");


	private final HttpStatus httpStatus;
	private final String code;
	private final String message;
}
