package com.github.mdjc.codesnippets.impl;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import com.github.mdjc.codesnippets.domain.CategoryRepository;
import com.github.mdjc.codesnippets.domain.Provider;
import com.github.mdjc.codesnippets.domain.User;

public class MySQLCategoryRepositoryTest extends RepositoryTest {
	private static CategoryRepository repository;

	@BeforeClass
	public static void init() throws Exception {
		RepositoryTest.init();
		repository = new MySQLCategoryRepository(dataSource);
	}

	@Test
	public void testFindAllCategories() {
		List<String> expected = new ArrayList<>();
		expected.add("default category");
		expected.add("search");
		Collections.sort(expected);

		List<String> actual = repository.all("e");

		assertEquals(expected, actual);
	}

	@Test
	public void testFindAllFor() {
		List<String> expected = new ArrayList<>();
		expected.add("search");
		Collections.sort(expected);

		User mirnaUser = User.of("testUser", "test123@gmail.com", "test123", Provider.NULL);

		List<String> actual = repository.allFor(mirnaUser);

		assertEquals(expected, actual);
	}
}
