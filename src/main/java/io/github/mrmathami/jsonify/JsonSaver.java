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
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.Writer;
import java.util.IdentityHashMap;
import java.util.Map;

final class JsonSaver extends JsonWriter {
	/**
	 * The active element stack, use to detect recursive write of the same element.
	 */
	private final @Nullable Map<@NotNull JsonElement, @NotNull JsonElement> stack;

	/**
	 * Creates a json saver.
	 */
	private JsonSaver(@NotNull Writer writer, boolean enableRecursiveCheck) {
		super(writer);
		this.stack = enableRecursiveCheck ? new IdentityHashMap<>() : null;
	}

	/**
	 * Save JSON element to output json.
	 */
	static void save(@NotNull Writer writer, @NotNull JsonElement element) throws IOException, JsonException {
		try (final JsonSaver saver = new JsonSaver(writer, true)) {
			saver.write(element);
			if (!saver.isDone()) throw new AssertionError(); // safeguard
		}
	}

	/**
	 * Write an element to the output json.
	 */
	private void write(@NotNull JsonElement element) throws IOException, JsonException {
		if (element instanceof JsonNumber) {
			valueNumber((JsonNumber) element);
		} else if (element instanceof JsonString) {
			valueString(element.toString());
		} else if (element instanceof JsonKeyword) {
			if (element == JsonKeyword.TRUE) {
				valueBoolean(true);
			} else if (element == JsonKeyword.FALSE) {
				valueBoolean(false);
			} else if (element == JsonKeyword.NULL) {
				valueNull();
			} else {
				throw new AssertionError("Unknown keyword!");
			}
		} else if (element instanceof JsonArray) {
			final JsonArray array = (JsonArray) element;
			if (stack != null && stack.put(array, array) != null) {
				throw new JsonException("Recursive JSON detected!");
			}
			beginArray();
			for (final JsonElement arrayElement : array) {
				write(arrayElement);
			}
			endArray();
			if (stack != null && stack.remove(array) != array) {
				throw new AssertionError();
			}
		} else if (element instanceof JsonObject) {
			final JsonObject object = (JsonObject) element;
			if (stack != null && stack.put(object, object) != null) {
				throw new JsonException("Recursive JSON detected!");
			}
			beginObject();
			for (final Map.Entry<String, JsonElement> entry : object.entrySet()) {
				name(entry.getKey());
				write(entry.getValue());
			}
			endObject();
			if (stack != null && stack.remove(object) != object) {
				throw new AssertionError();
			}
		} else {
			throw new JsonException("Unknown element type!");
		}
	}
}
