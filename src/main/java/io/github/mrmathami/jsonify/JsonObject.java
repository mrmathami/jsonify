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

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.LinkedHashMap;

public final class JsonObject extends LinkedHashMap<@NotNull String, @NotNull JsonElement> implements JsonElement {
	public @Nullable JsonElement put(@NotNull String key, boolean value) {
		return put(key, JsonKeyword.of(value));
	}

	public @Nullable JsonElement put(@NotNull String key, @Nullable Boolean value) {
		return put(key, JsonKeyword.of(value));
	}

	public @Nullable JsonElement put(@NotNull String key, char value) {
		return put(key, new JsonString(value));
	}

	public @Nullable JsonElement put(@NotNull String key, @Nullable Character value) {
		return put(key, value != null ? new JsonString(value) : JsonKeyword.NULL);
	}

	public @Nullable JsonElement put(@NotNull String key, @Nullable String value) {
		return put(key, value != null ? new JsonString(value) : JsonKeyword.NULL);
	}

	public @Nullable JsonElement put(@NotNull String key, long value) {
		return put(key, new JsonNumber(value));
	}

	public @Nullable JsonElement put(@NotNull String key, @Nullable Long value) {
		return put(key, value != null ? new JsonNumber(value) : JsonKeyword.NULL);
	}

	public @Nullable JsonElement put(@NotNull String key, double value) {
		return put(key, new JsonNumber(value));
	}

	public @Nullable JsonElement put(@NotNull String key, @Nullable Double value) {
		return put(key, value != null ? new JsonNumber(value) : JsonKeyword.NULL);
	}

	public @Nullable JsonElement put(@NotNull String key, @Nullable BigInteger value) {
		return put(key, value != null ? new JsonNumber(value) : JsonKeyword.NULL);
	}

	public @Nullable JsonElement put(@NotNull String key, @Nullable BigDecimal value) {
		return put(key, value != null ? new JsonNumber(value) : JsonKeyword.NULL);
	}


	public @Nullable Boolean getAsBoolean(@NotNull String key) {
		final JsonElement element = get(key);
		if (element == null || element == JsonKeyword.NULL) return null;
		if (element == JsonKeyword.TRUE) return Boolean.TRUE;
		if (element == JsonKeyword.FALSE) return Boolean.FALSE;
		throw new IllegalArgumentException("Not a Boolean!");
	}

	public @Nullable String getAsString(@NotNull String key) {
		final JsonElement element = get(key);
		if (element == null || element == JsonKeyword.NULL) return null;
		if (element instanceof JsonString) return element.toString();
		throw new IllegalArgumentException("Not a String!");
	}

	public @Nullable Character getAsCharacter(@NotNull String key) {
		final JsonElement element = get(key);
		if (element == null || element == JsonKeyword.NULL) return null;
		if (element instanceof JsonString) {
			final JsonString string = (JsonString) element;
			final Character character = string.toCharacter();
			if (character != null) return character;
		}
		throw new IllegalArgumentException("Not a Character!");
	}

	public @Nullable Number getAsNumber(@NotNull String key) {
		final JsonElement element = get(key);
		if (element == null || element == JsonKeyword.NULL) return null;
		if (element instanceof JsonNumber) {
			final JsonNumber number = (JsonNumber) element;
			return number.getValue();
		}
		throw new IllegalArgumentException("Not a Number!");
	}

	public @Nullable Long getAsLong(@NotNull String key) {
		final JsonElement element = get(key);
		if (element == null || element == JsonKeyword.NULL) return null;
		if (element instanceof JsonNumber) {
			final JsonNumber number = (JsonNumber) element;
			final Long value = number.getAsLong();
			if (value != null) return value;
		}
		throw new IllegalArgumentException("Not a Long!");
	}

	public @Nullable Double getAsDouble(@NotNull String key) {
		final JsonElement element = get(key);
		if (element == null || element == JsonKeyword.NULL) return null;
		if (element instanceof JsonNumber) {
			final JsonNumber number = (JsonNumber) element;
			final Double value = number.getAsDouble();
			if (value != null) return value;
		}
		throw new IllegalArgumentException("Not a Double!");
	}

	public @Nullable BigInteger getAsBigInteger(@NotNull String key) {
		final JsonElement element = get(key);
		if (element == null || element == JsonKeyword.NULL) return null;
		if (element instanceof JsonNumber) {
			final JsonNumber number = (JsonNumber) element;
			final BigInteger value = number.getAsBigInteger();
			if (value != null) return value;
		}
		throw new IllegalArgumentException("Not a BigInteger!");
	}

	public @Nullable BigDecimal getAsBigDecimal(@NotNull String key) {
		final JsonElement element = get(key);
		if (element == null || element == JsonKeyword.NULL) return null;
		if (element instanceof JsonNumber) {
			final JsonNumber number = (JsonNumber) element;
			final BigDecimal value = number.getAsBigDecimal();
			if (value != null) return value;
		}
		throw new IllegalArgumentException("Not a BigDecimal");
	}
}
