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
import java.io.Writer;
import java.util.IdentityHashMap;
import java.util.Map;

public final class JsonSaver extends JsonWriter {
	/**
	 * The active element stack, use to detect recursive write of the same element.
	 */
	private final @NotNull Map<@NotNull JsonElement, @NotNull JsonElement> recursionStack = new IdentityHashMap<>();

	/**
	 * Creates a json saver.
	 */
	private JsonSaver(@NotNull Writer writer) {
		super(writer);
	}

	/**
	 * Save JSON element to output json.
	 */
	public static void save(@NotNull Writer writer, @NotNull JsonElement element) throws IOException {
		try (final JsonSaver saver = new JsonSaver(writer)) {
			saver.writeElement(element);
			if (!saver.isDone()) throw new AssertionError(); // safeguard
		}
	}

	/**
	 * Write an element to the output json.
	 */
	private void writeElement(@NotNull JsonElement element) throws IOException {
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
				throw new AssertionError(); // safeguard
			}
		} else if (element instanceof JsonArray) {
			writeArray((JsonArray) element);
		} else if (element instanceof JsonObject) {
			writeObject((JsonObject) element);
		} else {
			throw new JsonException("Unknown element!");
		}
	}

	/**
	 * Write an array to the output json.
	 */
	private void writeArray(@NotNull JsonArray array) throws IOException {
		// check recursion
		if (recursionStack.put(array, array) != null) throw new JsonException("Recursive structure detected!");
		// write array
		beginArray();
		for (final JsonElement arrayElement : array) {
			writeElement(arrayElement);
		}
		endArray();
		// remove from recursion stack
		if (recursionStack.remove(array) != array) throw new AssertionError(); // safeguard
	}

	/**
	 * Write an object to the output json.
	 */
	private void writeObject(@NotNull JsonObject object) throws IOException {
		// check recursion
		if (recursionStack.put(object, object) != null) throw new JsonException("Recursive structure detected!");
		// write object
		beginObject();
		for (final Map.Entry<String, JsonElement> entry : object.entrySet()) {
			name(entry.getKey());
			writeElement(entry.getValue());
		}
		endObject();
		// remove from recursion stack
		if (recursionStack.remove(object) != object) throw new AssertionError(); // safeguard
	}

}
