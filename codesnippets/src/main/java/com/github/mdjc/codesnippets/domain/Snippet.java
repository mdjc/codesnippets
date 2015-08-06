package com.github.mdjc.codesnippets.domain;

import com.github.mdjc.commons.IllegalArguments;

public class Snippet implements Comparable<Snippet> {
	public static final Snippet NULL = new Snippet(0, "", "", "", "");

	private final long id;
	private final String title;
	private final String code;
	private final String language;
	private final String description;

	private Snippet(long id, String title, String code, String language, String description) {
		this.id = id;
		this.title = title.trim();
		this.code = code;
		this.language = language;
		this.description = description;
	}

	public long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getCode() {
		return code;
	}

	public String getLanguage() {
		return language;
	}

	public String getDescription() {
		return description;
	}

	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}

		if (!(other instanceof Snippet)) {
			return false;
		}

		Snippet that = (Snippet) other;

		return compareTo(that) == 0;
	}

	@Override
	public int hashCode() {
		return title.hashCode();
	}

	@Override
	public int compareTo(Snippet o) {
		return title.compareToIgnoreCase(o.title);
	}

	@Override
	public String toString() {
		return title;
	}

	public static Snippet of(long id, String title, String code, String language, String description) {
		title = IllegalArguments.checkBlank(title);
		code = IllegalArguments.checkBlank(code);
		return new Snippet(id, title, code, language, description);
	}

	public static Snippet of(long id, String title, String code) {
		return of(id, title, code, null, null);
	}

}