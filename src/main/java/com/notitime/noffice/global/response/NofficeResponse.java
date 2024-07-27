package com.notitime.noffice.global.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@JsonPropertyOrder({"timestamp", "httpStatus", "code", "message", "data"})
@Builder(access = AccessLevel.PRIVATE)
public class NofficeResponse<T> {

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd HH:mm:ss", timezone = "Asia/Seoul")
	@JsonProperty("timestamp")
	private final LocalDateTime timestamp = LocalDateTime.now();
	private final int httpStatus;

	private final String code;

	private final String message;

	@JsonInclude(value = Include.NON_NULL)
	private T data;

	public static <T> NofficeResponse<T> success(SuccessCode success) {
		return new NofficeResponse<>(success.getHttpStatus().value(), success.getCode(), success.getMessage());
	}

	public static <T> NofficeResponse<T> success(SuccessCode success, T data) {
		return new NofficeResponse<>(success.getHttpStatus().value(), success.getCode(), success.getMessage(), data);
	}

	public static <T> NofficeResponse<T> fail(ErrorCode error) {
		return new NofficeResponse<>(error.getHttpStatus().value(), error.getCode(), error.getMessage());
	}

	NofficeResponse(int httpStatus, String code, String message) {
		this.httpStatus = httpStatus;
		this.code = code;
		this.message = message;
	}

	NofficeResponse(int httpStatus, String code, String message, T data) {
		this.httpStatus = httpStatus;
		this.code = code;
		this.message = message;
		this.data = data;
	}
}
