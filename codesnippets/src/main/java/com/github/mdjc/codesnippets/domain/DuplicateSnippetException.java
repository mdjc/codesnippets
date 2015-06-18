package com.github.mdjc.codesnippets.domain;

public class DuplicateSnippetException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private final User user;
	private final Snippet snippet;

	public DuplicateSnippetException(User user, Snippet snippet) {
		super(String.format("snippet:'%s' for user:'%s' already exists", snippet, user));
		this.user = user;
		this.snippet = snippet;
	}

	public User getUser() {
		return user;
	}

	public Snippet getSnippet() {
		return snippet;
	}
}
