package com.github.mdjc.codesnippets.impl;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import com.github.mdjc.codesnippets.domain.CategoryRepository;

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
		expected.add("search");

		List<String> actual = repository.all("se");

		assertEquals(expected, actual);
	}
}
