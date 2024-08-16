package com.ucsy.ams.configurations;

import java.io.IOException;
import java.util.Set;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAuthSuccessHandler implements AuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {

		Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());

		if (roles.contains("ROLE_ADMIN")) {
			response.sendRedirect("/admin/dash");
			
		} else if (roles.contains("ROLE_CLASSROASTER")) {
			response.sendRedirect("/classRoster/dash");
			
		} else if (roles.contains("ROLE_TEACHER")) {
			response.sendRedirect("/teacher/dash");
			
		} else {
			response.sendRedirect("/student/dash");
		}
	}

}
