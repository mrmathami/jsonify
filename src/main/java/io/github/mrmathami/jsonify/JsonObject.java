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
		return element instanceof JsonKeyword && element != JsonKeyword.NULL ? element == JsonKeyword.TRUE : null;
	}

	public @Nullable String getAsString(@NotNull String key) {
		final JsonElement element = get(key);
		return element instanceof JsonString ? element.toString() : null;
	}

	public @Nullable Character getAsCharacter(@NotNull String key) {
		final JsonElement element = get(key);
		return element instanceof JsonString ? ((JsonString) element).toCharacter() : null;
	}

	public @Nullable Number getAsNumber(@NotNull String key) {
		final JsonElement element = get(key);
		return element instanceof JsonNumber ? ((JsonNumber) element).getValue() : null;
	}

	public @Nullable Long getAsLong(@NotNull String key) {
		final JsonElement element = get(key);
		return element instanceof JsonNumber ? ((JsonNumber) element).getValueAsLong() : null;
	}

	public @Nullable Double getAsDouble(@NotNull String key) {
		final JsonElement element = get(key);
		return element instanceof JsonNumber ? ((JsonNumber) element).getValueAsDouble() : null;
	}

	public @Nullable BigInteger getAsBigInteger(@NotNull String key) {
		final JsonElement element = get(key);
		return element instanceof JsonNumber ? ((JsonNumber) element).getValueAsBigInteger() : null;
	}

	public @Nullable BigDecimal getAsBigDecimal(@NotNull String key) {
		final JsonElement element = get(key);
		return element instanceof JsonNumber ? ((JsonNumber) element).getValueAsBigDecimal() : null;
	}
}
