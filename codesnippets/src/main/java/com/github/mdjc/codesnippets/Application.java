package com.github.mdjc.codesnippets;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.github.mdjc.codesnippets.domain.CategoryRepository;
import com.github.mdjc.codesnippets.domain.Provider;
import com.github.mdjc.codesnippets.domain.Snippet;
import com.github.mdjc.codesnippets.domain.SnippetsRepository;
import com.github.mdjc.codesnippets.domain.User;
import com.github.mdjc.codesnippets.domain.UserService;
import com.github.mdjc.codesnippets.domain.UsersRepository;
import com.github.mdjc.codesnippets.impl.DefaultUserService;
import com.github.mdjc.codesnippets.impl.MySQLCategoryRepository;
import com.github.mdjc.codesnippets.impl.MySQLProvider;
import com.github.mdjc.codesnippets.impl.MySQLSnippetsRepository;
import com.github.mdjc.codesnippets.impl.MySQLUsersRepository;
import com.github.mdjc.codesnippets.json.SnippetDeserializer;
import com.github.mdjc.codesnippets.json.UserDeserializer;
import com.github.mdjc.codesnippets.security.ApplicationAuthenticationProvider;

@SpringBootApplication
public class Application {
	@Autowired
	DataSource dataSource;

	@Bean
	public UserService userService() {
		return new DefaultUserService(usersRepository(), provider());
	}

	@Bean
	public UsersRepository usersRepository() {
		return new MySQLUsersRepository(dataSource);
	}

	@Bean
	public SnippetsRepository snippetsRepository() {
		return new MySQLSnippetsRepository(dataSource);
	}

	@Bean
	public CategoryRepository categoryRepository() {
		return new MySQLCategoryRepository(dataSource);
	}

	@Bean
	public Provider provider() {
		return new MySQLProvider(dataSource);
	}

	@Bean
	public Module module() {
		SimpleModule module = new SimpleModule();
		module.addDeserializer(User.class, new UserDeserializer(provider()));
		module.addDeserializer(Snippet.class, new SnippetDeserializer());
		return module;
	}

	@Bean
	public AuthenticationProvider authenticationProvider(UsersRepository userRepository) {
		return new ApplicationAuthenticationProvider(userRepository);
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
