package com.notitime.noffice.global.advice;

import com.notitime.noffice.global.exception.AuthAppleFeignException;
import com.notitime.noffice.global.exception.AuthGoogleFeignException;
import com.notitime.noffice.global.exception.BadRequestException;
import com.notitime.noffice.global.exception.NofficeException;
import com.notitime.noffice.global.exception.NotFoundException;
import com.notitime.noffice.global.response.BusinessErrorCode;
import com.notitime.noffice.global.response.NofficeResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@RestControllerAdvice
@Slf4j
public class NofficeExceptionHandler {

	@ExceptionHandler(BadRequestException.class)
	public NofficeResponse<Void> handleBadRequestException(BadRequestException e) {
		log.error("handleBadRequestException() in NofficeExceptionHandler throw BadRequestException : {}",
				e.getMessage());
		return NofficeResponse.fail(e.getErrorCode());
	}

	@ExceptionHandler(AuthAppleFeignException.class)
	public NofficeResponse<Void> handleAuthAppleFeignException(AuthAppleFeignException e) {
		log.error("handleAuthAppleFeignException() in NofficeExceptionHandler throw AuthAppleFeignException : {}",
				e.getMessage());
		return NofficeResponse.fail(e.getErrorCode());
	}

	@ExceptionHandler(AuthGoogleFeignException.class)
	public NofficeResponse<Void> handleAuthGoogleFeignException(AuthGoogleFeignException e) {
		log.error("handleAuthGoogleFeignException() in NofficeExceptionHandler throw AuthGoogleFeignException : {}",
				e.getMessage());
		return NofficeResponse.fail(e.getErrorCode());
	}

	@ExceptionHandler(NotFoundException.class)
	public NofficeResponse<Void> handleEntityNotFoundException(NotFoundException e) {
		log.error("handleEntityNotFoundException() in GlobalExceptionHandler throw EntityNotFoundException : {}",
				e.getMessage());
		return NofficeResponse.fail(e.getErrorCode());
	}

	@ExceptionHandler(MissingServletRequestParameterException.class)
	public NofficeResponse<Void> handleMissingServletRequestParameterException(
			MissingServletRequestParameterException e) {
		log.error(
				"handleMissingServletRequestParameterException() in GlobalExceptionHandler throw MissingServletRequestParameterException : {}",
				e.getMessage());
		return NofficeResponse.fail(BusinessErrorCode.BAD_REQUEST);
	}

	@ExceptionHandler(MaxUploadSizeExceededException.class)
	public NofficeResponse<Void> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException e) {
		log.error(
				"handleMaxUploadSizeExceededException() in GlobalExceptionHandler throw MaxUploadSizeExceededException : {}",
				e.getMessage());
		return NofficeResponse.fail(BusinessErrorCode.STORE_FILE_SIZE_EXCEEDED);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public NofficeResponse<Void> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
		log.error(
				"handleMethodArgumentNotValidException() in GlobalExceptionHandler throw MethodArgumentNotValidException : {}",
				e.getMessage());
		return NofficeResponse.fail(BusinessErrorCode.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	public NofficeResponse<Void> handleException(Exception e) {
		log.error("handleException() in NofficeExceptionHandler throw Exception : {}", e.getMessage());
		return NofficeResponse.fail(BusinessErrorCode.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(NofficeException.class)
	public NofficeResponse<?> handleCustomException(NofficeException e) {
		log.error("handleCustomException() in NofficeExceptionHandler throw NofficeException : {}", e.getMessage());
		return NofficeResponse.fail(e.getErrorCode());
	}
}
