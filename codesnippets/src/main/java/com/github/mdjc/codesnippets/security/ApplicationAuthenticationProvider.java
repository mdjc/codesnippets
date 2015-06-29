package com.github.mdjc.codesnippets.security;

import java.util.Collections;
import java.util.NoSuchElementException;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.github.mdjc.codesnippets.domain.User;
import com.github.mdjc.codesnippets.domain.UsersRepository;

public class ApplicationAuthenticationProvider implements AuthenticationProvider {
	private final UsersRepository userRepository;

	public ApplicationAuthenticationProvider(UsersRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
		String username = token.getName();
		String plainPassword = (String) token.getCredentials();

		User user = null;

		try {
			user = userRepository.get(username);
		} catch (NoSuchElementException e) {
			reject();
		}

		PasswordEncoder encoder = new BCryptPasswordEncoder();

		if (!encoder.matches(plainPassword, user.getPassword())) {
			reject();
		}

		return new UsernamePasswordAuthenticationToken(username, "", Collections.<GrantedAuthority> emptyList());
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
	}

	private static void reject() {
		throw new BadCredentialsException("Invalid username or password");
	}
}