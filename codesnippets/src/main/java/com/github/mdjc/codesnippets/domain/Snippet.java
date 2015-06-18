package com.github.mdjc.codesnippets.domain;

public class Snippet implements Comparable<Snippet> {
	public static final Snippet NULL = new Snippet(0, "", "");

	private final long id;
	private final String title;
	private final String code;

	public Snippet(long id, String title, String code) {
		this.id = id;
		this.title = title.trim();
		this.code = code;
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

}