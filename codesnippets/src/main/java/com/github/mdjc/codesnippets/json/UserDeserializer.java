package com.github.mdjc.codesnippets.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.mdjc.codesnippets.domain.Provider;
import com.github.mdjc.codesnippets.domain.User;

public class UserDeserializer extends SimpleDeserializer<User> {
	private final Provider provider;

	public UserDeserializer(Provider provider) {
		this.provider = provider;
	}

	@Override
	protected User deserialize(JsonNode tree) throws Exception {
		String name = tree.get("name").asText();
		String email = tree.get("email").asText();
		String password = tree.get("password").asText();
		return User.of(name, email, password, provider);
	}

}
