package com.github.mdjc.codesnippets.impl;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.NoSuchElementException;

import javax.sql.DataSource;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.github.mdjc.codesnippets.domain.DuplicateSnippetException;
import com.github.mdjc.codesnippets.domain.Snippet;
import com.github.mdjc.codesnippets.domain.User;
import com.github.mdjc.codesnippets.domain.UserSnippetsRepository;

public class MySQLUserSnippetsRepository extends JdbcDaoSupport implements UserSnippetsRepository {
	public static final RowMapper<Snippet> SNIPPET_MAPPER = (rs, row) -> Snippet.of(rs.getLong("snippet_id"),
			rs.getString("snippet_title"),
			rs.getString("snippet_code"),
			rs.getString("snippet_language"),
			rs.getString("snippet_description"),
			rs.getString("snippet_category"));

	private final User user;
	private final NamedParameterJdbcTemplate namedParamJdbcTemplate;

	public MySQLUserSnippetsRepository(DataSource dataSource, User user) {
		setDataSource(dataSource);
		this.namedParamJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		this.user = user;
	}

	@Override
	public Snippet add(Snippet snippet) throws DuplicateSnippetException {
		try {
			PreparedStatementCreator psc = con -> {
				PreparedStatement ps = con
						.prepareStatement(
								"INSERT INTO snippets set snippet_user = (select user_id from users where user_name = ?),"
										+ " snippet_title = ?, snippet_code = ?, snippet_language = ?, snippet_description = ?, snippet_category = ?",
								Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, user.getName());
				ps.setString(2, snippet.getTitle());
				ps.setString(3, snippet.getCode());
				ps.setString(4, snippet.getLanguage());
				ps.setString(5, snippet.getDescription());
				ps.setString(6, snippet.getCategory());

				return ps;
			};

			KeyHolder keyHolder = new GeneratedKeyHolder();
			getJdbcTemplate().update(psc, keyHolder);

			return Snippet.of(keyHolder.getKey().longValue(), snippet.getTitle(), snippet.getCode(),
					snippet.getLanguage(), snippet.getDescription(), snippet.getCategory());
		} catch (DuplicateKeyException e) {
			throw new DuplicateSnippetException(user, snippet);
		}

	}

	@Override
	public Snippet update(Snippet snippet) throws NoSuchElementException {
		try {
			int affectedRows = getJdbcTemplate()
					.update(
							"UPDATE snippets SET"
									+ " snippet_title = ?, snippet_code = ?, snippet_language = ?, snippet_description = ?, snippet_category = ?"
									+ " WHERE snippet_user = (select user_id from users where user_name = ?) AND snippet_id = ?",
							snippet.getTitle(), snippet.getCode(), snippet.getLanguage(), snippet.getDescription(),
							snippet.getCategory(), user.getName(), snippet.getId());

			if (affectedRows == 0) {
				throw new NoSuchElementException(String.format("snippet: %s does not exists", snippet));
			}

			return snippet;
		} catch (DuplicateKeyException e) {
			throw new DuplicateSnippetException(user, snippet);
		}
	}

	@Override
	public List<Snippet> find(String query, String category) {
		StringBuilder sbQuery = new StringBuilder();
		sbQuery.append("SELECT * FROM snippets s");
		sbQuery.append(" JOIN users u on s.snippet_user=u.user_id");
		sbQuery.append(" WHERE u.user_name = :username");
		sbQuery.append(" AND concat(s.snippet_title, s.snippet_code, s.snippet_category, s.snippet_description) like concat('%', :query, '%')");

		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("query", query);
		paramSource.addValue("username", user.getName());

		if (!category.isEmpty()) {
			sbQuery.append("AND s.snippet_category = :category");
			paramSource.addValue("category", category);
		}

		sbQuery.append(" ORDER BY snippet_title");
		return namedParamJdbcTemplate.query(sbQuery.toString(), paramSource, SNIPPET_MAPPER);
	}

	@Override
	public Snippet delete(Snippet snippet) {
		int affectedRows = getJdbcTemplate()
				.update(
						"DELETE from snippets where snippet_user = (select user_id from users where user_name = ?) AND snippet_id = ?",
						user.getName(), snippet.getId());

		if (affectedRows == 0) {
			throw new NoSuchElementException(String.format("snippet: %s does not exists", snippet));
		}

		return snippet;
	}

}
