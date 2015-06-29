package com.github.mdjc.codesnippets.impl;

import java.util.NoSuchElementException;

import javax.sql.DataSource;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.github.mdjc.codesnippets.domain.DuplicateUserException;
import com.github.mdjc.codesnippets.domain.User;
import com.github.mdjc.codesnippets.domain.UsersRepository;
import com.github.mdjc.commons.IllegalArguments;

public class MySQLUsersRepository extends JdbcDaoSupport implements UsersRepository {
	private final RowMapper<User> mapper = (rs, row) -> User.of(
			rs.getString("user_name"),
			rs.getString("user_email"),
			rs.getString("user_password"),
			new MySQLProvider(getDataSource()));

	public MySQLUsersRepository(DataSource dataSource) {
		setDataSource(dataSource);
	}

	@Override
	public User get(String name) {
		try {
			User user = getJdbcTemplate().queryForObject("SELECT * FROM users where user_name=?", mapper, name);
			return user;
		} catch (EmptyResultDataAccessException e) {
			throw new NoSuchElementException(String.format("User %s does not exists", name));
		}
	}

	@Override
	public User add(User user) {
		IllegalArguments.checkBlank(user.getName());
		IllegalArguments.checkBlank(user.getEmail());
		IllegalArguments.checkBlank(user.getPassword());

		try {
			getJdbcTemplate().update("INSERT INTO users (user_name, user_email, user_password) values(?, ?, ?)",
					user.getName(), user.getEmail(), user.getPassword());

			return user;
		} catch (DuplicateKeyException e) {
			throw new DuplicateUserException(user);
		}
	}

}
