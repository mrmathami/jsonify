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
	public @Nullable JsonElement putValue(@NotNull String key, boolean value) {
		return put(key, JsonKeyword.of(value));
	}

	public @Nullable JsonElement putValue(@NotNull String key, @Nullable Boolean value) {
		return put(key, JsonKeyword.of(value));
	}

	public @Nullable JsonElement putValue(@NotNull String key, char value) {
		return put(key, new JsonString(value));
	}

	public @Nullable JsonElement putValue(@NotNull String key, @Nullable Character value) {
		return put(key, value != null ? new JsonString(value) : JsonKeyword.NULL);
	}

	public @Nullable JsonElement putValue(@NotNull String key, @Nullable String value) {
		return put(key, value != null ? new JsonString(value) : JsonKeyword.NULL);
	}

	public @Nullable JsonElement putValue(@NotNull String key, long value) {
		return put(key, new JsonNumber(value));
	}

	public @Nullable JsonElement putValue(@NotNull String key, @Nullable Long value) {
		return put(key, value != null ? new JsonNumber(value) : JsonKeyword.NULL);
	}

	public @Nullable JsonElement putValue(@NotNull String key, double value) {
		return put(key, new JsonNumber(value));
	}

	public @Nullable JsonElement putValue(@NotNull String key, @Nullable Double value) {
		return put(key, value != null ? new JsonNumber(value) : JsonKeyword.NULL);
	}

	public @Nullable JsonElement putValue(@NotNull String key, @Nullable BigInteger value) {
		return put(key, value != null ? new JsonNumber(value) : JsonKeyword.NULL);
	}

	public @Nullable JsonElement putValue(@NotNull String key, @Nullable BigDecimal value) {
		return put(key, value != null ? new JsonNumber(value) : JsonKeyword.NULL);
	}


	public @Nullable JsonKeyword getAsJsonKeyword(@NotNull String key) {
		return Jsonify.toJsonKeyword(get(key));
	}

	public @Nullable JsonString getAsJsonString(@NotNull String key) {
		return Jsonify.toJsonString(get(key));
	}

	public @Nullable JsonNumber getAsJsonNumber(@NotNull String key) {
		return Jsonify.toJsonNumber(get(key));
	}

	public @Nullable JsonArray getAsJsonArray(@NotNull String key) {
		return Jsonify.toJsonArray(get(key));
	}

	public @Nullable JsonObject getAsJsonObject(@NotNull String key) {
		return Jsonify.toJsonObject(get(key));
	}


	public @Nullable Boolean getAsBoolean(@NotNull String key) {
		return Jsonify.toBoolean(get(key));
	}

	public @Nullable String getAsString(@NotNull String key) {
		return Jsonify.toString(get(key));
	}

	public @Nullable Character getAsCharacter(@NotNull String key) {
		return Jsonify.toCharacter(get(key));
	}

	public @Nullable Number getAsNumber(@NotNull String key) {
		return Jsonify.toNumber(get(key));
	}

	public @Nullable Long getAsLong(@NotNull String key) {
		return Jsonify.toLong(get(key));
	}

	public @Nullable Double getAsDouble(@NotNull String key) {
		return Jsonify.toDouble(get(key));
	}

	public @Nullable BigInteger getAsBigInteger(@NotNull String key) {
		return Jsonify.toBigInteger(get(key));
	}

	public @Nullable BigDecimal getAsBigDecimal(@NotNull String key) {
		return Jsonify.toBigDecimal(get(key));
	}
}
