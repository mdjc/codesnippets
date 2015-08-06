package com.github.mdjc.codesnippets.impl;

import java.util.List;
import java.util.NoSuchElementException;

import javax.sql.DataSource;

import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.github.mdjc.codesnippets.domain.SnippetSearchItem;
import com.github.mdjc.codesnippets.domain.SnippetsRepository;

public class MySQLSnippetsRepository extends JdbcDaoSupport implements SnippetsRepository {
	private final RowMapper<SnippetSearchItem> SEARCH_IT_MAPPER = (rs, row) ->
			new SnippetSearchItem(rs.getString("user_name"),
					MySQLUserSnippetsRepository.SNIPPET_MAPPER.mapRow(rs, row));

	public MySQLSnippetsRepository(DataSource dataSource) {
		setDataSource(dataSource);
	}

	@Override
	public SnippetSearchItem get(long id) {
		try {
			return getJdbcTemplate().queryForObject(
					"SELECT * FROM snippets s "
							+ "JOIN users u on u.user_id = s.snippet_user WHERE s.snippet_id = ? ",
					SEARCH_IT_MAPPER, id);
		} catch (IncorrectResultSizeDataAccessException e) {
			throw new NoSuchElementException(String.format("snippet: %d does not exits", id));
		}
	}

	@Override
	public List<SnippetSearchItem> allSnippets(String query) {
		return getJdbcTemplate()
				.query(
						"SELECT * FROM snippets s "
								+ "JOIN users u on u.user_id = s.snippet_user"
								+ " WHERE concat(s.snippet_title, s.snippet_code) like concat('%', ?, '%') order by s.snippet_title",
						SEARCH_IT_MAPPER, query);
	}
}
