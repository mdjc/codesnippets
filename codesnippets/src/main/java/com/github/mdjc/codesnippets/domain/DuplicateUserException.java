package com.github.mdjc.codesnippets.domain;

public class DuplicateUserException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public DuplicateUserException(User user) {
		super(String.format("user:%s already exists", user.toString()));
	}
}
