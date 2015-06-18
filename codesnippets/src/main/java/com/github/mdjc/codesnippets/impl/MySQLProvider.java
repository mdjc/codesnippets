package com.github.mdjc.codesnippets.impl;

import javax.sql.DataSource;

import com.github.mdjc.codesnippets.domain.Provider;
import com.github.mdjc.codesnippets.domain.User;
import com.github.mdjc.codesnippets.domain.UserSnippetsRepository;

public class MySQLProvider implements Provider {
	private final DataSource dataSource;

	public MySQLProvider(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public UserSnippetsRepository userSnippetsRepository(User user) {
		return new MySQLUserSnippetsRepository(dataSource, user);
	}
}
