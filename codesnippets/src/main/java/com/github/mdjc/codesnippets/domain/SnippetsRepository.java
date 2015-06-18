package com.github.mdjc.codesnippets.domain;

import java.util.List;

public interface SnippetsRepository {
	List<SnippetSearchItem> allSnippets(String query);

	SnippetSearchItem get(long id);
}
