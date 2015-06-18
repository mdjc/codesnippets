package com.github.mdjc.codesnippets.impl;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.BeforeClass;
import org.junit.Test;

import com.github.mdjc.codesnippets.domain.Provider;
import com.github.mdjc.codesnippets.domain.Snippet;
import com.github.mdjc.codesnippets.domain.SnippetSearchItem;
import com.github.mdjc.codesnippets.domain.SnippetsRepository;
import com.github.mdjc.codesnippets.domain.User;

public class MySQLSnippetsRepositoryTest extends RepositoryTest {
	private static SnippetsRepository snippetsRepository;
	private static User testUser;
	private static User mirnaUser;

	@BeforeClass
	public static void init() throws Exception {
		RepositoryTest.init();
		snippetsRepository = new MySQLSnippetsRepository(dataSource);
		testUser = User.of("testUser", "test123@gmail.com", "test123", Provider.NULL);
		mirnaUser = User.of("mirna", "mdjc@gmail.com", "mirna123", Provider.NULL);
	}

	@Test
	public void testGetExistentSnippet() {
		Snippet snippet = new Snippet(1L, "Binary Search", "public class search() {}");
		SnippetSearchItem expected = new SnippetSearchItem(testUser.getName(), snippet);
		SnippetSearchItem actual = snippetsRepository.get(1L);

		assertEquals(expected, actual);
		assertEquals(snippet.getCode(), actual.getSnippet().getCode());
	}

	@Test(expected = NoSuchElementException.class)
	public void testGetNonExistent() {
		snippetsRepository.get(56565L);
	}

	@Test
	public void testFindByCriteria1() {
		List<SnippetSearchItem> expected = new ArrayList<>();
		Snippet snippet1 = new Snippet(1, "Binary Search", "public class search() {}");
		Snippet snippet2 = new Snippet(2, "Find Object", "public class search() {}");
		expected.add(new SnippetSearchItem(testUser.getName(), snippet1));
		expected.add(new SnippetSearchItem(testUser.getName(), snippet2));

		List<SnippetSearchItem> actual = snippetsRepository.allSnippets("search");
		assertTrue(actual.size() == expected.size());
		for (SnippetSearchItem item : expected) {
			assertTrue(actual.contains(item));
		}
	}

	@Test
	public void testFindEmpty() {
		List<SnippetSearchItem> actual = snippetsRepository.allSnippets("search for no found text");
		assertTrue(actual.isEmpty());
	}

	@Test
	public void testFindAllExistent() {
		List<SnippetSearchItem> expected = new ArrayList<>();
		expected.add(new SnippetSearchItem(testUser.getName(), new Snippet(1, "Binary Search",
				"public class search() {}")));
		expected.add(new SnippetSearchItem(testUser.getName(),
				new Snippet(2, "Find Object", "public class search() {}")));
		expected.add(new SnippetSearchItem(mirnaUser.getName(), new Snippet(3, "Heap Sort",
				"public class MergeSort() {}")));
		expected.add(new SnippetSearchItem(mirnaUser.getName(), new Snippet(4, "Merge Sort",
				"public class MergeSort() {}")));
		expected.add(new SnippetSearchItem(mirnaUser.getName(), new Snippet(5, "Quit Sort",
				"public class MergeSort() {}")));

		List<SnippetSearchItem> actual = snippetsRepository.allSnippets("");

		assertEquals(expected, actual);
	}
}
