package com.github.mdjc.codesnippets.domain;

public class SnippetSearchItem {
	private final String username;
	private final Snippet snippet;

	public SnippetSearchItem(String username, Snippet snippet) {
		this.username = username;
		this.snippet = snippet;
	}

	public String getUsername() {
		return username;
	}

	public Snippet getSnippet() {
		return snippet;
	}

	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}

		if (!(other instanceof SnippetSearchItem)) {
			return false;
		}

		SnippetSearchItem that = (SnippetSearchItem) other;

		return this.username.equalsIgnoreCase(that.username) && this.snippet.equals(that.snippet);
	}

	@Override
	public int hashCode() {
		int hashUser = username.hashCode();
		int hashSnippet = snippet.hashCode();
		return 31 * hashUser + hashSnippet;
	}
}