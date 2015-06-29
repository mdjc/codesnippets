package com.github.mdjc.codesnippets.impl;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.github.mdjc.codesnippets.domain.Provider;
import com.github.mdjc.codesnippets.domain.User;
import com.github.mdjc.codesnippets.domain.UserService;
import com.github.mdjc.codesnippets.domain.UsersRepository;

public class DefaultUserService implements UserService {
	private final UsersRepository usersRepository;
	private final Provider provider;

	public DefaultUserService(UsersRepository usersRepository, Provider provider) {
		this.usersRepository = usersRepository;
		this.provider = provider;
	}

	@Override
	public User addUser(User user) {
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		String password = encoder.encode(user.getPassword());
		return usersRepository.add(User.of(user.getName(), user.getEmail(), password, provider));
	}
}
