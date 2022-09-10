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

public final class JsonLoader extends JsonReader {
	/**
	 * Creates a json loader.
	 */
	private JsonLoader(@NotNull Reader reader) {
		super(reader);
	}

	/**
	 * Load input json to JSON element.
	 */
	public static @NotNull JsonElement load(@NotNull Reader reader) throws IOException, JsonException {
		try (final JsonLoader loader = new JsonLoader(reader)) {
			final JsonElement value = loader.read(loader.nextToken());
			loader.nextToken();
			return value;
		}
	}

	/**
	 * Get next value from input JSON.
	 */
	private @NotNull JsonElement read(int token) throws IOException, JsonException {
		switch (token) {
			case TOKEN_ARRAY_BEGIN: {
				// already inside array
				final JsonArray array = new JsonArray();
				while (true) {
					final int value = nextToken();
					if (value != TOKEN_ARRAY_END) {
						array.add(read(value));
					} else {
						endStructure();
						return array;
					}
				}
			}
			case TOKEN_OBJECT_BEGIN: {
				// already inside object
				final JsonObject object = new JsonObject();
				while (true) {
					final int name = nextToken();
					if (name == TOKEN_NAME) {
						object.put(getString(), read(nextToken()));
					} else if (name == TOKEN_OBJECT_END) {
						endStructure();
						return object;
					} else {
						throw new AssertionError();
					}
				}
			}
			case TOKEN_STRING:
				return new JsonString(getString());
			case TOKEN_NUMBER:
				return getNumber();
			case TOKEN_TRUE:
				return JsonKeyword.TRUE;
			case TOKEN_FALSE:
				return JsonKeyword.FALSE;
			case TOKEN_NULL:
				return JsonKeyword.NULL;
			default:
				throw new AssertionError("Unknown/unexpected token!");
		}
	}
}