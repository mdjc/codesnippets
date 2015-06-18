package com.github.mdjc.codesnippets.impl;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.NoSuchElementException;

import javax.sql.DataSource;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.github.mdjc.codesnippets.domain.DuplicateSnippetException;
import com.github.mdjc.codesnippets.domain.Snippet;
import com.github.mdjc.codesnippets.domain.User;
import com.github.mdjc.codesnippets.domain.UserSnippetsRepository;

public class MySQLUserSnippetsRepository extends JdbcDaoSupport implements UserSnippetsRepository {
	public static final RowMapper<Snippet> SNIPPET_MAPPER = (rs, row) -> new Snippet(rs.getLong("snippet_id"),
			rs.getString("snippet_title"),
			rs.getString("snippet_code"));

	private final User user;

	public MySQLUserSnippetsRepository(DataSource dataSource, User user) {
		setDataSource(dataSource);
		this.user = user;
	}

	@Override
	public Snippet add(Snippet snippet) throws DuplicateSnippetException {
		try {
			PreparedStatementCreator psc = con -> {
				PreparedStatement ps = con
						.prepareStatement(
								"INSERT INTO snippets (snippet_user, snippet_title, snippet_code) "
										+ " VALUES((select user_id from users where user_name = ?), ?, ?)",
								Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, user.getName());
				ps.setString(2, snippet.getTitle());
				ps.setString(3, snippet.getCode());
				return ps;
			};

			KeyHolder keyHolder = new GeneratedKeyHolder();
			getJdbcTemplate().update(psc, keyHolder);

			return new Snippet(keyHolder.getKey().longValue(), snippet.getTitle(), snippet.getCode());
		} catch (DuplicateKeyException e) {
			throw new DuplicateSnippetException(user, snippet);
		}

	}

	@Override
	public Snippet update(Snippet snippet) throws NoSuchElementException {
		try {
			int affectedRows = getJdbcTemplate()
					.update(
							"UPDATE snippets SET snippet_title = ?, snippet_code = ? "
									+ " WHERE snippet_user = (select user_id from users where user_name = ?) AND snippet_id = ?",
							snippet.getTitle(), snippet.getCode(), user.getName(), snippet.getId());

			if (affectedRows == 0) {
				throw new NoSuchElementException(String.format("snippet: %s does not exists", snippet));
			}

			return snippet;
		} catch (DuplicateKeyException e) {
			throw new DuplicateSnippetException(user, snippet);
		}
	}

	@Override
	public List<Snippet> find(String query) {
		return getJdbcTemplate().query(
				"SELECT snippet_id, snippet_title, snippet_code FROM snippets "
						+ " JOIN users on snippet_user=user_id "
						+ " WHERE user_name = ?"
						+ " AND concat(snippet_title, snippet_code) like concat('%', ?, '%') order by snippet_title",
				SNIPPET_MAPPER, user.getName(), query.trim());
	}

}
