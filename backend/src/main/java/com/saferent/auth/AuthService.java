package com.saferent.auth;

import java.util.Locale;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.saferent.user.User;
import com.saferent.user.UserRepository;
import com.saferent.user.UserRole;

@Service
public class AuthService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Transactional
	public RegisterResponse register(RegisterRequest request) {
		String fullName = requireText(request.fullName(), "Full name is required");
		String email = normalizeEmail(request.email());
		String password = requireText(request.password(), "Password is required");
		UserRole role = parseRole(request.role());

		if (password.length() < 8) {
			throw new InvalidRegistrationException("Password must be at least 8 characters");
		}

		if (userRepository.existsByEmail(email)) {
			throw new DuplicateEmailException("A user with this email already exists");
		}

		User user = new User(fullName, email, passwordEncoder.encode(password), role);
		User savedUser = userRepository.save(user);

		return RegisterResponse.fromUser(savedUser);
	}

	private String requireText(String value, String message) {
		if (value == null || value.trim().isEmpty()) {
			throw new InvalidRegistrationException(message);
		}

		return value.trim();
	}

	private String normalizeEmail(String email) {
		String normalizedEmail = requireText(email, "Email is required").toLowerCase(Locale.ROOT);

		if (!normalizedEmail.contains("@")) {
			throw new InvalidRegistrationException("Email must be a valid email address");
		}

		return normalizedEmail;
	}

	private UserRole parseRole(String role) {
		String normalizedRole = requireText(role, "Role is required").toUpperCase(Locale.ROOT);

		try {
			return UserRole.valueOf(normalizedRole);
		} catch (IllegalArgumentException exception) {
			throw new InvalidRegistrationException("Role must be TENANT, PROPERTY_MANAGER, TECHNICIAN, or ADMIN");
		}
	}
}
