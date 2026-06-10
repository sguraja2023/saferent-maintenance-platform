package com.saferent.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.saferent.auth.DuplicateEmailException;
import com.saferent.auth.InvalidRegistrationException;

@RestControllerAdvice
public class ApiExceptionHandler {

	@ExceptionHandler(InvalidRegistrationException.class)
	public ResponseEntity<ApiErrorResponse> handleInvalidRegistration(InvalidRegistrationException exception) {
		return buildErrorResponse(HttpStatus.BAD_REQUEST, exception.getMessage());
	}

	@ExceptionHandler(DuplicateEmailException.class)
	public ResponseEntity<ApiErrorResponse> handleDuplicateEmail(DuplicateEmailException exception) {
		return buildErrorResponse(HttpStatus.CONFLICT, exception.getMessage());
	}

	private ResponseEntity<ApiErrorResponse> buildErrorResponse(HttpStatus status, String message) {
		return ResponseEntity
				.status(status)
				.body(ApiErrorResponse.of(status.value(), message));
	}
}
