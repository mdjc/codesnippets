package com.github.mdjc.codesnippets.impl;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.github.mdjc.codesnippets.domain.CategoryRepository;

public class MySQLCategoryRepository extends JdbcDaoSupport implements CategoryRepository {

	public MySQLCategoryRepository(DataSource dataSource) {
		setDataSource(dataSource);
	}

	@Override
	public List<String> all(String filter) {
		return getJdbcTemplate()
				.queryForList(
						"SELECT distinct snippet_category FROM (SELECT snippet_category from snippets UNION select category_description from snippet_categories) "
								+ " where snippet_category like concat('%', ?, '%')"
								+ "order by snippet_category",
						String.class, filter);
	}
}
