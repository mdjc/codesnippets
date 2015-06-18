package com.github.mdjc.codesnippets.domain;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

public interface UserSnippetsRepository {
	UserSnippetsRepository NULL = new UserSnippetsRepository() {

		@Override
		public Snippet update(Snippet snippet) throws NoSuchElementException {
			return Snippet.NULL;
		}

		@Override
		public List<Snippet> find(String query) {
			return Collections.emptyList();
		}

		@Override
		public Snippet add(Snippet snippet) throws DuplicateSnippetException {
			return Snippet.NULL;
		}
	};

	Snippet add(Snippet snippet) throws DuplicateSnippetException;

	Snippet update(Snippet snippet) throws NoSuchElementException;

	List<Snippet> find(String query);
}
