package com.github.mdjc.codesnippets.domain;

import java.util.List;

public interface CategoryRepository {
	List<String> all(String filter);

	List<String> allFor(User user);
}
