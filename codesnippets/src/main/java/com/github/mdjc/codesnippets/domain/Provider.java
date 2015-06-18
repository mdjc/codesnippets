package com.github.mdjc.codesnippets.domain;

public interface Provider {
	UserSnippetsRepository userSnippetsRepository(User user);

	public static Provider NULL = new Provider() {
		@Override
		public UserSnippetsRepository userSnippetsRepository(User user) {
			return UserSnippetsRepository.NULL;
		}
	};
}
