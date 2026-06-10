package com.saferent.auth;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.saferent.user.User;
import com.saferent.user.UserRepository;
import com.saferent.user.UserRole;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

	@Mock
	private UserRepository userRepository;

	@Mock
	private PasswordEncoder passwordEncoder;

	@InjectMocks
	private AuthService authService;

	@Test
	void registerHashesPasswordAndSavesNormalizedUser() {
		when(userRepository.existsByEmail("taylor.tenant@example.com")).thenReturn(false);
		when(passwordEncoder.encode("password123")).thenReturn("hashed-password");
		when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

		RegisterResponse response = authService.register(new RegisterRequest(
				" Taylor Tenant ",
				" Taylor.Tenant@Example.com ",
				"password123",
				"tenant"));

		ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
		verify(userRepository).save(userCaptor.capture());
		User savedUser = userCaptor.getValue();

		assertThat(savedUser.getFullName()).isEqualTo("Taylor Tenant");
		assertThat(savedUser.getEmail()).isEqualTo("taylor.tenant@example.com");
		assertThat(savedUser.getPasswordHash()).isEqualTo("hashed-password");
		assertThat(savedUser.getRole()).isEqualTo(UserRole.TENANT);
		assertThat(response.email()).isEqualTo("taylor.tenant@example.com");
		assertThat(response.role()).isEqualTo(UserRole.TENANT);
	}

	@Test
	void registerRejectsDuplicateEmail() {
		when(userRepository.existsByEmail("taylor.tenant@example.com")).thenReturn(true);

		assertThatThrownBy(() -> authService.register(new RegisterRequest(
				"Taylor Tenant",
				"taylor.tenant@example.com",
				"password123",
				"TENANT")))
				.isInstanceOf(DuplicateEmailException.class)
				.hasMessage("A user with this email already exists");

		verify(userRepository, never()).save(any(User.class));
	}

	@Test
	void registerRejectsShortPassword() {
		assertThatThrownBy(() -> authService.register(new RegisterRequest(
				"Taylor Tenant",
				"taylor.tenant@example.com",
				"short",
				"TENANT")))
				.isInstanceOf(InvalidRegistrationException.class)
				.hasMessage("Password must be at least 8 characters");

		verify(userRepository, never()).save(any(User.class));
	}
}
