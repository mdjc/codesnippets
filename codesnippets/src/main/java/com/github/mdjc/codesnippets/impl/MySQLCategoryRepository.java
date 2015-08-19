package com.github.mdjc.codesnippets.impl;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.github.mdjc.codesnippets.domain.CategoryRepository;
import com.github.mdjc.codesnippets.domain.User;

public class MySQLCategoryRepository extends JdbcDaoSupport implements CategoryRepository {

	public MySQLCategoryRepository(DataSource dataSource) {
		setDataSource(dataSource);
	}

	@Override
	public List<String> all(String filter) {
		return getJdbcTemplate()
				.queryForList(
						"SELECT distinct snippet_category FROM (SELECT snippet_category from snippets UNION select category_description from snippet_categories) "
								+ "where snippet_category like concat('%', ?, '%') "
								+ "order by snippet_category",
						String.class, filter);
	}

	@Override
	public List<String> allFor(User user) {
		return getJdbcTemplate()
				.queryForList(
						"SELECT distinct snippet_category FROM snippets "
								+ " where snippet_user = (select user_id from users where user_name = ?) "
								+ "order by snippet_category",
						String.class, user.getName());
	}
}
