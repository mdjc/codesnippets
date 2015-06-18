package com.github.mdjc.codesnippets.impl;

import javax.sql.DataSource;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;

import com.github.mdjc.codesnippets.test.common.TestUtils;

public abstract class RepositoryTest {
	protected static DataSource dataSource;

	@BeforeClass
	public static void init() throws Exception {
		dataSource = TestUtils.buildDataSource();
	}

	@Before
	public void setUp() throws Exception {
		TestUtils.configureDatabase(dataSource);
		TestUtils.populateDatabase(dataSource);
	}

	@After
	public void tearDown() throws Exception {
		TestUtils.clearDatabase(dataSource);
	}
}
