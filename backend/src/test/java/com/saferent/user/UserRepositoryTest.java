package com.saferent.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class UserRepositoryTest {

	@Autowired
	private UserRepository userRepository;

	@Test
	void savesAndFindsUserByEmail() {
		User user = new User(
				"Taylor Tenant",
				"taylor.tenant@example.com",
				"fake-hash-for-step-1-2",
				UserRole.TENANT);

		userRepository.save(user);

		Optional<User> savedUser = userRepository.findByEmail("taylor.tenant@example.com");

		assertThat(savedUser).isPresent();
		assertThat(savedUser.get().getId()).isNotNull();
		assertThat(savedUser.get().getFullName()).isEqualTo("Taylor Tenant");
		assertThat(savedUser.get().getRole()).isEqualTo(UserRole.TENANT);
		assertThat(savedUser.get().getCreatedAt()).isNotNull();
		assertThat(userRepository.existsByEmail("taylor.tenant@example.com")).isTrue();
	}
}
