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

final class JsonLoader extends JsonReader {
	/**
	 * Creates a json loader.
	 */
	private JsonLoader(@NotNull Reader reader) {
		super(reader);
	}

	/**
	 * Load input json to JSON element.
	 */
	static @NotNull JsonElement load(@NotNull Reader reader) throws IOException, JsonException {
		try (final JsonLoader loader = new JsonLoader(reader)) {
			final JsonElement value = loader.nextValue(loader.nextToken());
			loader.nextToken();
			return value;
		}
	}

	/**
	 * Get next value from input JSON.
	 */
	private @NotNull JsonElement nextValue(int token) throws IOException, JsonException {
		switch (token) {
			case TOKEN_ARRAY_BEGIN:
				return getArray();
			case TOKEN_OBJECT_BEGIN:
				return getObject();
			case TOKEN_STRING:
				return new JsonString(getString());
			case TOKEN_NUMBER:
				return getNumber();
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
	private @NotNull JsonArray getArray() throws IOException, JsonException {
		// already inside array
		final JsonArray array = new JsonArray();
		while (true) {
			final int value = nextToken();
			if (value != TOKEN_ARRAY_END) {
				array.add(nextValue(value));
			} else {
				endStructure();
				return array;
			}
		}
	}

	/**
	 * Get object from input JSON.
	 */
	private @NotNull JsonObject getObject() throws IOException, JsonException {
		// already inside object
		final JsonObject object = new JsonObject();
		while (true) {
			final int name = nextToken();
			if (name == TOKEN_NAME) {
				object.put(getString(), nextValue(nextToken()));
			} else if (name == TOKEN_OBJECT_END) {
				endStructure();
				return object;
			} else {
				throw new AssertionError();
			}
		}
	}
}
