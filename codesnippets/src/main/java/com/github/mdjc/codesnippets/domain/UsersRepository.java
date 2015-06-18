package com.github.mdjc.codesnippets.domain;

public interface UsersRepository {
	User get(String name);

	User add(User user);
}
