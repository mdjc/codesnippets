package com.github.mdjc.codesnippets.impl;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.BeforeClass;
import org.junit.Test;

import com.github.mdjc.codesnippets.domain.DuplicateSnippetException;
import com.github.mdjc.codesnippets.domain.Provider;
import com.github.mdjc.codesnippets.domain.Snippet;
import com.github.mdjc.codesnippets.domain.User;
import com.github.mdjc.codesnippets.domain.UserSnippetsRepository;

public class MySQLUserSnippetsRepositoryTest extends RepositoryTest {
	private static User mirnaUser;
	private static UserSnippetsRepository mirnaRepository;

	@BeforeClass
	public static void init() throws Exception {
		RepositoryTest.init();
		mirnaUser = User.of("mirna", "mdjc@gmail.com", "mirna123", Provider.NULL);
		mirnaRepository = new MySQLUserSnippetsRepository(dataSource, mirnaUser);
	}

	@Test
	public void testAdd() {
		Snippet actual = mirnaRepository.add(Snippet.of(0, "Draw Line", "public class Draw { .... }", "Java",
				"Draw description"));
		Snippet expected = Snippet.of(6, "Draw Line", "public class Draw { .... }", "Java", "Draw description");

		assertEquals(expected, actual);
		assertEquals(expected.getCode(), actual.getCode());
		assertEquals(expected.getLanguage(), actual.getLanguage());
		assertEquals(expected.getDescription(), actual.getDescription());
	}

	@Test(expected = DuplicateSnippetException.class)
	public void testAddDuplicates() {
		mirnaRepository.add(Snippet.of(0L, "Draw Line", "public class Draw { .... }"));
		mirnaRepository.add(Snippet.of(0L, "Draw Line", "public class Draw { .... }"));
	}

	@Test
	public void testUpdate() {
		Snippet expected = Snippet.of(5, "Quick Sort", "public class QuickSort() {}");
		Snippet actual = mirnaRepository.update(expected);
		assertEquals(expected, actual);
		assertEquals(expected, actual);
		assertEquals(expected.getCode(), actual.getCode());
		assertEquals(expected.getLanguage(), actual.getLanguage());
		assertEquals(expected.getDescription(), actual.getDescription());
	}

	@Test(expected = NoSuchElementException.class)
	public void testUpdateUnexistent() {
		Snippet snippet = Snippet.of(6, "Quit Sort2356 Unknown", "public class MergeSort() {}");
		mirnaRepository.update(snippet);
	}

	@Test
	public void testFindEmpty() {
		List<Snippet> actual = mirnaRepository.find("query unexistent");
		assertTrue(actual.isEmpty());
	}

	@Test
	public void testFindAllExistent() {
		List<Snippet> expected = new ArrayList<>();
		expected.add(Snippet.of(3, "Heap Sort", "public class MergeSort() {}"));
		expected.add(Snippet.of(4, "Merge Sort", "public class MergeSort() {}"));
		expected.add(Snippet.of(5, "Quit Sort", "public class MergeSort() {}"));
		List<Snippet> actual = mirnaRepository.find("");
		assertEquals(expected, actual);
	}

	@Test
	public void testFindAllByCriteria() {
		List<Snippet> expected = new ArrayList<>();
		expected.add(Snippet.of(4, "Heap Sort", "public class MergeSort() {}"));
		List<Snippet> actual = mirnaRepository.find("Heap");
		assertEquals(expected, actual);
	}

	@Test
	public void testFindAllEmpty() {
		List<Snippet> expected = new ArrayList<>();
		List<Snippet> actual = mirnaRepository.find("sorting method unexistent");
		assertEquals(expected, actual);
	}

}
