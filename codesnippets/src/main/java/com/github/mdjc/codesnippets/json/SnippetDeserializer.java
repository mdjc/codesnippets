package com.github.mdjc.codesnippets.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.mdjc.codesnippets.domain.Snippet;

public class SnippetDeserializer extends SimpleDeserializer<Snippet> {

	@Override
	protected Snippet deserialize(JsonNode tree) throws Exception {
		try {
			long id = tree.get("id").asLong();
			String title = tree.get("title").asText();
			String code = tree.get("code").asText();

			return new Snippet(id, title, code);
		} catch (Exception e) {
			throw new IllegalArgumentException(
					"Snippet was no received properly. Json content required : {id:0, title : <title>, code: <code>}");
		}
	}

}
