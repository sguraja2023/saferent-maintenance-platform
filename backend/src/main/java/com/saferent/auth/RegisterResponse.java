package com.saferent.auth;

import java.time.Instant;

import com.saferent.user.User;
import com.saferent.user.UserRole;

public record RegisterResponse(
		Long id,
		String fullName,
		String email,
		UserRole role,
		Instant createdAt) {

	public static RegisterResponse fromUser(User user) {
		return new RegisterResponse(
				user.getId(),
				user.getFullName(),
				user.getEmail(),
				user.getRole(),
				user.getCreatedAt());
	}
}
