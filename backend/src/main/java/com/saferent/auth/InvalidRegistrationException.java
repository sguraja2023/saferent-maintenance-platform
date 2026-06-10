package com.saferent.auth;

public class InvalidRegistrationException extends RuntimeException {

	public InvalidRegistrationException(String message) {
		super(message);
	}
}
