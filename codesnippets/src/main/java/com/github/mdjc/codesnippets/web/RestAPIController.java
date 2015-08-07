package com.github.mdjc.codesnippets.web;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.github.mdjc.codesnippets.domain.Snippet;
import com.github.mdjc.codesnippets.domain.SnippetSearchItem;
import com.github.mdjc.codesnippets.domain.SnippetsRepository;
import com.github.mdjc.codesnippets.domain.User;
import com.github.mdjc.codesnippets.domain.UserService;
import com.github.mdjc.codesnippets.domain.UsersRepository;

@RestController
public class RestAPIController {
	@Autowired
	private UserService userService;

	@Autowired
	private UsersRepository usersRepository;

	@Autowired
	private SnippetsRepository snippetsRepository;

	@RequestMapping("/principal")
	public Principal principal(Principal principal) {
		return principal;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/users")
	@ResponseStatus(HttpStatus.CREATED)
	public User register(@RequestBody User user) {
		return userService.addUser(user);
	}

	@RequestMapping(value = "/snippets")
	public List<SnippetSearchItem> allSnippets(@RequestParam(defaultValue = "") String query) {
		return snippetsRepository.allSnippets(query);
	}

	@RequestMapping(value = "/snippets/{id}")
	public SnippetSearchItem allSnippets(@PathVariable("id") long id) {
		return snippetsRepository.get(id);
	}

	@RequestMapping(value = "/users/{name}/snippets")
	public List<Snippet> allSnippets(
			@PathVariable String name,
			@RequestParam(defaultValue = "") String query) {
		User user = usersRepository.get(name);
		return user.getSnippetsRepository().find(query);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/users/{name}/snippets")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Snippet> addSnippet(@PathVariable String name, @RequestBody Snippet snippet) {
		Snippet result = usersRepository.get(name).getSnippetsRepository().add(snippet);

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setLocation(ServletUriComponentsBuilder.fromCurrentContextPath()
				.path("/snippets/{id}")
				.buildAndExpand(result.getId()).toUri());

		return new ResponseEntity<Snippet>(result, httpHeaders, HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/users/{name}/snippets")
	public Snippet updateSnippet(@PathVariable String name, @RequestBody Snippet snippet) {
		return usersRepository.get(name).getSnippetsRepository().update(snippet);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/users/{name}/snippets")
	public Snippet deleteSnippet(@PathVariable String name, @RequestBody Snippet snippet) {
		return usersRepository.get(name).getSnippetsRepository().delete(snippet);
	}
}
