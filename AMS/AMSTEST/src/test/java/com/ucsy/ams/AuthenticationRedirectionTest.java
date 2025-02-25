package com.ucsy.ams;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.ucsy.ams.configurations.CustomAuthSuccessHandler;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.io.IOException;
import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class AuthenticationRedirectionTest {

	@Mock
	private Authentication authentication;

	@InjectMocks
	private CustomAuthSuccessHandler successHandler;

	private MockHttpServletRequest request;
	private MockHttpServletResponse response;

	@BeforeEach
	void setUp() {
		request = new MockHttpServletRequest();
		response = new MockHttpServletResponse();
	}

	@Test
	void teacherAuthenticationRedirectionTest() throws IOException, jakarta.servlet.ServletException {

		when(authentication.getAuthorities()).thenAnswer((Answer) invocation -> {
			return Collections.singletonList(new SimpleGrantedAuthority("ROLE_TEACHER"));
		});

		successHandler.onAuthenticationSuccess(request, response, authentication);

		assertEquals("/teacher/dash", response.getRedirectedUrl());
	}

	@Test
	void classRoasterAuthenticationRedirectionTest() throws IOException, jakarta.servlet.ServletException {

		when(authentication.getAuthorities()).thenAnswer((Answer) invocation -> {
			return Collections.singletonList(new SimpleGrantedAuthority("ROLE_CLASSROASTER"));
		});

		successHandler.onAuthenticationSuccess(request, response, authentication);

		assertEquals("/classRoster/dash", response.getRedirectedUrl());
	}
}
