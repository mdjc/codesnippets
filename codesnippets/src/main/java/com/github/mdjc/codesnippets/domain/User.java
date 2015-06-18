package com.github.mdjc.codesnippets.domain;

import java.util.List;
import java.util.NoSuchElementException;

import com.github.mdjc.common.IllegalArguments;

public class User implements Comparable<User> {
	public static User NULL = new User("", "", "", Provider.NULL);

	private final String name;
	private final String email;
	private final String password;

	private final UserSnippetsRepository snippetsRepository;

	private User(String name, String email, String password, Provider provider) {
		this.name = name;
		this.email = email;
		this.password = password;
		snippetsRepository = provider.userSnippetsRepository(this);
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public Snippet addSnippet(Snippet snippet) throws DuplicateSnippetException {
		if ("".equals(snippet.getTitle()) || "".equals(snippet.getCode())) {
			throw new IllegalArgumentException("snippet title and snippet code cannot be empty");
		}

		return snippetsRepository.add(snippet);
	}

	public List<Snippet> findSnippet(String query) {
		return snippetsRepository.find(query);
	}

	public Snippet updateSnippet(Snippet snippet) throws NoSuchElementException {
		return snippetsRepository.update(snippet);
	}

	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}

		if (!(other instanceof User)) {
			return false;
		}

		User that = (User) other;

		return compareTo(that) == 0;

	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}

	@Override
	public int compareTo(User other) {
		return this.name.compareToIgnoreCase(other.name);
	}

	@Override
	public String toString() {
		return name;
	}

	public static User of(String name, String email, String password, Provider provider) {
		name = IllegalArguments.checkBlank(name);
		email = IllegalArguments.checkBlank(email);
		password = IllegalArguments.checkBlank(password);
		provider = IllegalArguments.checkNull(provider);

		return new User(name, email, password, provider);
	}
}
