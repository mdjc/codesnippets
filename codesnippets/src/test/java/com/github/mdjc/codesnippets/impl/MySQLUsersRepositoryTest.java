package com.github.mdjc.codesnippets.impl;

import static org.junit.Assert.*;

import java.util.NoSuchElementException;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.github.mdjc.codesnippets.domain.DuplicateUserException;
import com.github.mdjc.codesnippets.domain.Provider;
import com.github.mdjc.codesnippets.domain.User;
import com.github.mdjc.codesnippets.domain.UsersRepository;

public class MySQLUsersRepositoryTest extends RepositoryTest {
	private static UsersRepository usersRepository;

	private final RowMapper<User> USER_MAPPER = (rs, row) -> User.of(rs.getString("user_name"),
			rs.getString("user_email"), rs.getString("user_password"), new MySQLProvider(dataSource));

	@BeforeClass
	public static void init() throws Exception {
		RepositoryTest.init();
		usersRepository = new MySQLUsersRepository(dataSource);
	}

	@Test
	public void testAddUnexistenUser() {
		User expected = User.of("hector", "email@gmail.com", "mypassword", Provider.NULL);
		usersRepository.add(expected);
		User actual = getUser("hector");
		assertEquals(expected, actual);
	}

	@Test(expected = DuplicateUserException.class)
	public void testAddDuplicateUser() {
		usersRepository.add(User.of("mirna", "mirnadjc@gmail.com", "mypassword", Provider.NULL));
	}

	@Test
	public void testGetExistentUser() {
		User expected = User.of("mirna", "mdjc@gmail.com", "mirna123", Provider.NULL);
		User actual = usersRepository.get("mirna");

		assertEquals(expected, actual);
		assertEquals(actual.getEmail(), expected.getEmail());
		assertEquals(actual.getPassword(), expected.getPassword());
	}

	@Test(expected = NoSuchElementException.class)
	public void testGetUnexistentUser() {
		usersRepository.get("unexistenuser");
	}

	private User getUser(String username) {
		try {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
			return jdbcTemplate.queryForObject("SELECT * FROM USERS where user_name = ?", USER_MAPPER, username);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}
}
