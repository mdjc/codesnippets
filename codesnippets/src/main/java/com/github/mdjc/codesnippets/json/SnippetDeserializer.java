package com.github.mdjc.codesnippets.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.mdjc.codesnippets.domain.Snippet;

public class SnippetDeserializer extends SimpleDeserializer<Snippet> {

	@Override
	protected Snippet deserialize(JsonNode tree) throws Exception {
		long id = tree.get("id").asLong();
		String title = tree.get("title").asText();
		String code = tree.get("code").asText();
		String language = tree.get("language").asText();
		String description = tree.get("description").asText();
		String category = tree.get("category").asText();
		return Snippet.of(id, title, code, language, description, category);
	}

}
