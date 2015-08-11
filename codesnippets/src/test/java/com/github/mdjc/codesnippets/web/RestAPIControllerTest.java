package com.github.mdjc.codesnippets.web;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import com.github.mdjc.codesnippets.domain.Provider;
import com.github.mdjc.codesnippets.domain.Snippet;
import com.github.mdjc.codesnippets.domain.SnippetSearchItem;
import com.github.mdjc.codesnippets.domain.SnippetsRepository;
import com.github.mdjc.codesnippets.domain.User;
import com.github.mdjc.codesnippets.domain.UserService;
import com.github.mdjc.codesnippets.domain.UsersRepository;
import com.github.mdjc.codesnippets.test.common.TestUtils;

public class RestAPIControllerTest extends RestControllerTest {
	@Autowired
	private DataSource dataSource;

	@Autowired
	private UserService userService;

	@Autowired
	private UsersRepository usersRepository;

	@Autowired
	private SnippetsRepository snippetsRepository;

	@Autowired
	private Provider provider;

	private List<SnippetSearchItem> snippetSearchItems;

	@Before
	public void setup() throws Exception {
		buildWebContext();
		TestUtils.clearDataBase(dataSource);
		TestUtils.populateDatabase(dataSource);
		snippetSearchItems = snippetsRepository.allSnippets("");
	}

	@Test
	public void registerUser() throws Exception {
		String userJson = json(User.of("lesica", "lesica.ton@gmail.com", "123", provider));
		this.mockMvc.perform(post("/users")
				.contentType(MediaType.APPLICATION_JSON)
				.content(userJson))
				.andExpect(status().isCreated());
	}

	@Test
	public void registerUserBadRquest() throws Exception {
		String userJson = json("Mirna");
		this.mockMvc.perform(post("/users")
				.contentType(MediaType.APPLICATION_JSON)
				.content(userJson))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void registerUserBadRquestUserNull() throws Exception {
		String userJson = json(User.NULL);
		this.mockMvc.perform(post("/users")
				.contentType(MediaType.APPLICATION_JSON)
				.content(userJson))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void allSnippets() throws Exception {
		ResultActions ra = mockMvc.perform(get("/snippets"))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$", hasSize(snippetSearchItems.size())));

		for (int i = 0; i < snippetSearchItems.size(); i++) {
			andExpectSnippet(ra, i, ".snippet");
			andExpectUser(ra, i);
		}
	}

	@Test
	public void singleSnippet() throws Exception {
		Snippet snippet = snippetSearchItems.get(0).getSnippet();
		mockMvc.perform(get("/snippets/" + snippet.getId()))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.snippet.id", is((int) snippet.getId())))
				.andExpect(jsonPath("$.snippet.title", is(snippet.getTitle())))
				.andExpect(jsonPath("$.snippet.code", is(snippet.getCode())));

	}

	@Test
	public void singleSnippetNotFound() throws Exception {
		mockMvc.perform(get("/snippets/425")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}

	@Test
	public void addSnippet() throws Exception {
		Snippet snippet = Snippet.of(0, "my test tile", "some code goes here { .... }", "PHP", "This a sample",
				"sample category");
		String jsonSnippet = json(snippet);
		long maxId = snippetSearchItems.stream().map(e -> e.getSnippet().getId()).max((x, y) -> Long.compare(x, y))
				.get();
		mockMvc.perform(post(String.format("/users/%s/snippets", snippetSearchItems.get(0).getUsername()))
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonSnippet))
				.andExpect(status().isCreated())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id", is((int) maxId + 1)))
				.andExpect(jsonPath("$.title", is(snippet.getTitle())))
				.andExpect(jsonPath("$.code", is(snippet.getCode())))
				.andExpect(jsonPath("$.language", is(snippet.getLanguage())))
				.andExpect(jsonPath("$.description", is(snippet.getDescription())));

	}

	@Test
	public void addDuplicatedSnippet() throws Exception {
		Snippet snippet = snippetSearchItems.get(0).getSnippet();
		mockMvc.perform(post(String.format("/users/%s/snippets", snippetSearchItems.get(0).getUsername()))
				.contentType(MediaType.APPLICATION_JSON)
				.content(json(snippet)))
				.andExpect(status().isUnprocessableEntity());
	}

	@Test
	public void addSnippetInvalidUser() throws Exception {
		Snippet snippet = snippetSearchItems.get(0).getSnippet();
		String jsonSnippet = json(snippet);
		mockMvc.perform(post(String.format("/users/%s/snippets", "unexistentUser"))
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonSnippet))
				.andExpect(status().isNotFound());
	}

	@Test
	public void updateSnippet() throws Exception {
		Snippet snippet = Snippet.of(snippetSearchItems.get(0).getSnippet().getId(), "changed title", "changedCode",
				"JavaScript", "Changed description", "changed category");
		String jsonSnippet = json(snippet);
		mockMvc.perform(put(String.format("/users/%s/snippets", snippetSearchItems.get(0).getUsername()))
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonSnippet))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id", is((int) snippet.getId())))
				.andExpect(jsonPath("$.title", is(snippet.getTitle())))
				.andExpect(jsonPath("$.code", is(snippet.getCode())))
				.andExpect(jsonPath("$.language", is(snippet.getLanguage())))
				.andExpect(jsonPath("$.description", is(snippet.getDescription())));

	}

	@Test
	public void updateSnippetSetDuplicateTitle() throws Exception {
		Snippet snippet = Snippet.of(snippetSearchItems.get(0).getSnippet().getId(), snippetSearchItems.get(1)
				.getSnippet().getTitle(), "changedCode");
		String jsonSnippet = json(snippet);
		mockMvc.perform(put(String.format("/users/%s/snippets", snippetSearchItems.get(0).getUsername()))
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonSnippet))
				.andExpect(status().isUnprocessableEntity());
	}

	@Test
	public void updateSnippetInvalidUser() throws Exception {
		Snippet snippet = snippetSearchItems.get(0).getSnippet();
		String jsonSnippet = json(snippet);
		mockMvc.perform(put(String.format("/users/%s/snippets", "unexistentUser"))
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonSnippet))
				.andExpect(status().isNotFound());
	}

	private void andExpectSnippet(ResultActions ra, int index, String prefix) throws Exception {
		Snippet snippet = snippetSearchItems.get(index).getSnippet();
		ra.andExpect(jsonPath(String.format("$[%d]%s.id", index, prefix), is((int) snippet.getId())))
				.andExpect(
						jsonPath(String.format("$[%d]%s.title", index, prefix), is(snippet.getTitle())))
				.andExpect(
						jsonPath(String.format("$[%d]%s.code", index, prefix), is(snippet.getCode())));
	}

	private void andExpectUser(ResultActions ra, int index) throws Exception {
		ra.andExpect(jsonPath(String.format("$[%d].username", index), is(snippetSearchItems.get(index).getUsername())));
	}
}
