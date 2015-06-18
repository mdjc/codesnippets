package com.github.mdjc.codesnippets.json;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;

public abstract class SimpleDeserializer<T> extends JsonDeserializer<T> {

	@Override
	public T deserialize(JsonParser parser, DeserializationContext contex) throws IOException,
			JsonProcessingException {
		JsonNode tree = null;
		try {
			ObjectCodec codec = parser.getCodec();
			tree = codec.readTree(parser);
			return deserialize(tree);
		} catch (JsonProcessingException e) {
			throw e;
		} catch (Exception e) {
			throw new JsonMappingException(this.getClass() + " unable to deserialize object " + tree, e);
		}
	}

	protected abstract T deserialize(JsonNode tree) throws Exception;

}
