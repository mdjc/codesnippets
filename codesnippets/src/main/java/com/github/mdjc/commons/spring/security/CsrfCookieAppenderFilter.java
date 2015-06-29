package com.github.mdjc.commons.spring.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

public class CsrfCookieAppenderFilter extends OncePerRequestFilter {
	private final String cookieName;

	public CsrfCookieAppenderFilter(String cookieName) {
		this.cookieName = cookieName;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());

		if (csrfToken == null || csrfToken.getToken() == null) {
			filterChain.doFilter(request, response);
			return;
		}

		Cookie cookie = WebUtils.getCookie(request, cookieName);

		if (cookie != null && csrfToken.getToken().equals(cookie.getValue())) {
			filterChain.doFilter(request, response);
			return;
		}

		String path = request.getContextPath() + "/";
		cookie = new Cookie(cookieName, csrfToken.getToken());
		cookie.setPath(path);
		response.addCookie(cookie);
		filterChain.doFilter(request, response);
	}
}