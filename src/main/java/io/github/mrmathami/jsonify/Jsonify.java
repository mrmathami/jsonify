/*
 * This file is part of jsonify.
 *
 * jsonify is free software: you can redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * jsonify is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with jsonify. If not, see <https://www.gnu.org/licenses/>.
 */

package io.github.mrmathami.jsonify;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.Map;

import static io.github.mrmathami.jsonify.JsonToken.TOKEN_ARRAY_BEGIN;
import static io.github.mrmathami.jsonify.JsonToken.TOKEN_ARRAY_END;
import static io.github.mrmathami.jsonify.JsonToken.TOKEN_EOF;
import static io.github.mrmathami.jsonify.JsonToken.TOKEN_FALSE;
import static io.github.mrmathami.jsonify.JsonToken.TOKEN_NAME;
import static io.github.mrmathami.jsonify.JsonToken.TOKEN_NULL;
import static io.github.mrmathami.jsonify.JsonToken.TOKEN_NUMBER;
import static io.github.mrmathami.jsonify.JsonToken.TOKEN_OBJECT_BEGIN;
import static io.github.mrmathami.jsonify.JsonToken.TOKEN_OBJECT_END;
import static io.github.mrmathami.jsonify.JsonToken.TOKEN_STRING;
import static io.github.mrmathami.jsonify.JsonToken.TOKEN_TRUE;

public final class Jsonify {
	private Jsonify() {
	}

	/**
	 * Load input json to JSON element.
	 */
	public static @NotNull JsonElement load(@NotNull Reader inputReader)
			throws IOException, JsonException {
		try (final JsonReader reader = new JsonReader(inputReader)) {
			final JsonElement value = nextValue(reader, reader.nextTokenIndex());
			if (reader.nextTokenIndex() != TOKEN_EOF) throw new AssertionError();
			return value;
		}
	}

	/**
	 * Get next value from input JSON.
	 */
	private static @NotNull JsonElement nextValue(@NotNull JsonReader reader, int token)
			throws IOException, JsonException {
		switch (token) {
			case TOKEN_ARRAY_BEGIN:
				return getArray(reader);
			case TOKEN_OBJECT_BEGIN:
				return getObject(reader);
			case TOKEN_STRING:
				return JsonPrimitive.of(reader.getString());
			case TOKEN_NUMBER:
				return JsonPrimitive.of(reader.getNumber());
			case TOKEN_TRUE:
				return JsonPrimitive.TRUE;
			case TOKEN_FALSE:
				return JsonPrimitive.FALSE;
			case TOKEN_NULL:
				return JsonPrimitive.NULL;
			default:
				throw new AssertionError();
		}
	}

	/**
	 * Get array from input JSON.
	 */
	private static @NotNull JsonArray getArray(@NotNull JsonReader reader)
			throws IOException, JsonException {
		// already inside array
		final JsonArray array = new JsonArray();
		while (true) {
			final int value = reader.nextTokenIndex();
			if (value != TOKEN_ARRAY_END) {
				array.add(nextValue(reader, value));
			} else {
				reader.endStructure();
				return array;
			}
		}
	}

	/**
	 * Get object from input JSON.
	 */
	private static @NotNull JsonObject getObject(@NotNull JsonReader reader)
			throws IOException, JsonException {
		// already inside object
		final JsonObject object = new JsonObject();
		while (true) {
			final int name = reader.nextTokenIndex();
			if (name == TOKEN_NAME) {
				object.put(reader.getString(), nextValue(reader, reader.nextTokenIndex()));
			} else if (name == TOKEN_OBJECT_END) {
				reader.endStructure();
				return object;
			} else {
				throw new AssertionError();
			}
		}
	}

	//========================================

	/**
	 * Save JSON element to output json.
	 */
	public static void save(@NotNull Writer outputWriter, @NotNull JsonElement element)
			throws IOException, JsonException {
		try (final JsonWriter writer = new JsonWriter(outputWriter)) {
			putElement(writer, element);
			if (!writer.isDone()) throw new AssertionError(); // safeguard
		}
	}

	/**
	 * Put an element to the output json.
	 */
	private static void putElement(@NotNull JsonWriter writer, @NotNull JsonElement element)
			throws IOException, JsonException {
		if (element instanceof JsonArray) {
			final JsonArray array = (JsonArray) element;
			writer.beginArray();
			for (final JsonElement arrayElement : array) {
				putElement(writer, arrayElement);
			}
			writer.endArray();
		} else if (element instanceof JsonObject) {
			final JsonObject object = (JsonObject) element;
			writer.beginObject();
			for (final Map.Entry<String, JsonElement> entry : object.entrySet()) {
				writer.name(entry.getKey());
				putElement(writer, entry.getValue());
			}
			writer.endObject();
		} else if (element instanceof JsonPrimitive) {
			final JsonPrimitive primitive = (JsonPrimitive) element;
			if (primitive.isBoolean()) {
				writer.valueBoolean(primitive.getBoolean());
			} else if (primitive.isNumber()) {
				writer.valueNumber(primitive.getNumber());
			} else if (primitive.isString()) {
				writer.valueString(primitive.getString());
			} else if (primitive.isNull()) {
				writer.valueNull();
			} else {
				throw new AssertionError();
			}
		} else {
			throw new AssertionError();
		}
	}
}
