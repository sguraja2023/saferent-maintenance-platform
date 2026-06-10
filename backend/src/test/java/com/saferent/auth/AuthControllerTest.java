package com.saferent.auth;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.Instant;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.saferent.common.ApiExceptionHandler;
import com.saferent.user.UserRole;

@WebMvcTest(AuthController.class)
@Import(ApiExceptionHandler.class)
class AuthControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private AuthService authService;

	@Test
	void registerReturnsCreatedUserWithoutPasswordHash() throws Exception {
		RegisterResponse response = new RegisterResponse(
				1L,
				"Taylor Tenant",
				"taylor.tenant@example.com",
				UserRole.TENANT,
				Instant.parse("2026-06-09T12:00:00Z"));

		when(authService.register(any(RegisterRequest.class))).thenReturn(response);

		mockMvc.perform(post("/api/auth/register")
				.contentType(MediaType.APPLICATION_JSON)
				.content("""
						{
						  "fullName": "Taylor Tenant",
						  "email": "taylor.tenant@example.com",
						  "password": "password123",
						  "role": "TENANT"
						}
						"""))
				.andExpect(status().isCreated())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.fullName").value("Taylor Tenant"))
				.andExpect(jsonPath("$.email").value("taylor.tenant@example.com"))
				.andExpect(jsonPath("$.role").value("TENANT"))
				.andExpect(jsonPath("$.passwordHash").doesNotExist());
	}

	@Test
	void registerReturnsConflictForDuplicateEmail() throws Exception {
		when(authService.register(any(RegisterRequest.class)))
				.thenThrow(new DuplicateEmailException("A user with this email already exists"));

		mockMvc.perform(post("/api/auth/register")
				.contentType(MediaType.APPLICATION_JSON)
				.content("""
						{
						  "fullName": "Taylor Tenant",
						  "email": "taylor.tenant@example.com",
						  "password": "password123",
						  "role": "TENANT"
						}
						"""))
				.andExpect(status().isConflict())
				.andExpect(jsonPath("$.status").value(409))
				.andExpect(jsonPath("$.message").value("A user with this email already exists"));
	}
}
