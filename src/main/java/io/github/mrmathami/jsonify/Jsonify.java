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

public final class Jsonify {
	private Jsonify() {
	}

	public static @NotNull JsonElement load(@NotNull Reader inputReader)
			throws IOException, JsonException {
		try (final JsonReader reader = new JsonReader(inputReader)) {
			final JsonElement value = getValue(reader, reader.nextToken());
			if (reader.nextToken() != JsonToken.EOF) throw new AssertionError();
			return value;
		}
	}

	private static @NotNull JsonElement getValue(@NotNull JsonReader reader, @NotNull JsonToken token)
			throws IOException, JsonException {
		return switch (token) {
			case ARRAY_BEGIN -> loadArray(reader);
			case OBJECT_BEGIN -> loadObject(reader);
			case STRING -> JsonPrimitive.of(reader.getString());
			case NUMBER -> JsonPrimitive.of(reader.getNumber());
			case TRUE -> JsonPrimitive.TRUE;
			case FALSE -> JsonPrimitive.FALSE;
			case NULL -> JsonPrimitive.NULL;
			default -> throw new AssertionError();
		};
	}

	private static @NotNull JsonElement loadArray(@NotNull JsonReader reader)
			throws IOException, JsonException {
		// already in array
		final JsonArray array = new JsonArray();
		while (true) {
			final JsonToken value = reader.nextToken();
			if (value != JsonToken.ARRAY_END) {
				array.add(getValue(reader, value));
			} else {
				reader.endStructure();
				return array;
			}
		}
	}

	private static @NotNull JsonElement loadObject(@NotNull JsonReader reader)
			throws IOException, JsonException {
		// already in object
		final JsonObject object = new JsonObject();
		while (true) {
			JsonToken jsonToken = reader.nextToken();
			if (jsonToken == JsonToken.NAME) {
				object.put(reader.getString(), getValue(reader, reader.nextToken()));
			} else if (jsonToken == JsonToken.OBJECT_END) {
				reader.endStructure();
				return object;
			} else {
				throw new AssertionError();
			}

		}
	}
}
