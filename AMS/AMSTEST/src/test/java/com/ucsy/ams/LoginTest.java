package com.ucsy.ams;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.ucsy.ams.configurations.CustomAccountDetailsService;
import com.ucsy.ams.entity.Account;
import com.ucsy.ams.repository.AccountRepo;

@ExtendWith(MockitoExtension.class)
class LoginTest {

	@Mock
	private AccountRepo accRepo;

	@Mock
	private BCryptPasswordEncoder bcpwd;

	@InjectMocks
	private CustomAccountDetailsService customAccountDetailsService;

	private Account user;

	@BeforeEach
	void setUp() {
		user = new Account();
		user.setEmail("test@example.com");
		user.setPassword("password123");
		user.setRole("ROLE_ADMIN");
	}

	@Test
	void UserExists() {
		when(accRepo.findByEmail("test@example.com")).thenReturn(user);
		when(bcpwd.encode("password123")).thenReturn("encodedPassword123");

		UserDetails userDetails = customAccountDetailsService.loadUserByUsername("test@example.com");

		assertNotNull(userDetails);
		assertEquals("test@example.com", userDetails.getUsername());
		assertEquals("encodedPassword123", userDetails.getPassword());
	}

	@Test
	void testLoadUserByEmail_TeacherExists() {
		user.setEmail("teacher@example.com");
		user.setRole("ROLE_TEACHER");
		when(accRepo.findByEmail("teacher@example.com")).thenReturn(user);

		UserDetails userDetails = customAccountDetailsService.loadUserByUsername("teacher@example.com");

		assertNotNull(userDetails);
		assertEquals("teacher@example.com", userDetails.getUsername());
		assertEquals("password123", userDetails.getPassword());
	}

	@Test
	void testLoadUserByEmail_ClassRoasterExists() {
		user.setEmail("classroaster@example.com");
		user.setRole("ROLE_CLASSROASTER");
		when(accRepo.findByEmail("classroaster@example.com")).thenReturn(user);

		UserDetails userDetails = customAccountDetailsService.loadUserByUsername("classroaster@example.com");

		assertNotNull(userDetails);
		assertEquals("classroaster@example.com", userDetails.getUsername());
		assertEquals("password123", userDetails.getPassword());
	}

	@Test
	void UserNotFound() {
		when(accRepo.findByEmail("notfound@example.com")).thenReturn(null);

		assertThrows(UsernameNotFoundException.class, () -> {
			customAccountDetailsService.loadUserByUsername("notfound@example.com");
		});

	}
}
