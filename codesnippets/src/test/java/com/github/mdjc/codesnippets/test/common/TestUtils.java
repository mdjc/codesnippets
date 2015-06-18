package com.github.mdjc.codesnippets.test.common;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.h2.jdbcx.JdbcConnectionPool;
import org.springframework.jdbc.core.JdbcTemplate;

import com.github.mdjc.common.Utils;

public class TestUtils {
	public static JdbcConnectionPool buildDataSource() throws SQLException, IOException {
		JdbcConnectionPool dataSource = JdbcConnectionPool.create(
				"jdbc:h2:mem:db;MODE=MySQL;DB_CLOSE_DELAY=-1", "sa", "");
		return dataSource;
	}

	public static void clearDataBase(DataSource dataSource) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcTemplate.update("DELETE FROM snippets");
		jdbcTemplate.update("DELETE FROM users");
	}

	public static void clearDatabase(DataSource dataSource) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcTemplate.update("drop all objects");
	}

	public static void populateDatabase(DataSource dataSource) throws Exception {
		String users = String.format("scripts-test%s%s", File.separator, "test-users-conf.sql");
		String snippets = String.format("scripts-test%s%s", File.separator, "test-snippet-conf.sql");
		String script = Utils.readFile(users) + Utils.readFile(snippets);
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcTemplate.update(script);
	}

	public static void configureDatabase(DataSource dataSource) throws SQLException, IOException {
		String dbScript = readScript();

		try (Connection connection = dataSource.getConnection();
				PreparedStatement prepStmt = connection.prepareStatement(dbScript);) {
			prepStmt.execute();
		}
	}

	private static String readScript() throws IOException {
		String filePath = String.format("src%smain%sresources%s%s", File.separator, File.separator, File.separator,
				"schema.sql");
		return Utils.readFile(filePath);
	}
}
