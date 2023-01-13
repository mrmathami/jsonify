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
import java.util.ArrayList;

public final class JsonArray extends ArrayList<@NotNull JsonElement> implements JsonElement {
	public boolean addValue(boolean value) {
		return add(JsonKeyword.of(value));
	}

	public boolean addValue(@Nullable Boolean value) {
		return add(JsonKeyword.of(value));
	}

	public boolean addValue(char value) {
		return add(new JsonString(value));
	}

	public boolean addValue(@Nullable Character value) {
		return add(value != null ? new JsonString(value) : JsonKeyword.NULL);
	}

	public boolean addValue(@Nullable String value) {
		return add(value != null ? new JsonString(value) : JsonKeyword.NULL);
	}

	public boolean addValue(long value) {
		return add(new JsonNumber(value));
	}

	public boolean addValue(@Nullable Long value) {
		return add(value != null ? new JsonNumber(value) : JsonKeyword.NULL);
	}

	public boolean addValue(double value) {
		return add(new JsonNumber(value));
	}

	public boolean addValue(@Nullable Double value) {
		return add(value != null ? new JsonNumber(value) : JsonKeyword.NULL);
	}

	public boolean addValue(@Nullable BigInteger value) {
		return add(value != null ? new JsonNumber(value) : JsonKeyword.NULL);
	}

	public boolean addValue(@Nullable BigDecimal value) {
		return add(value != null ? new JsonNumber(value) : JsonKeyword.NULL);
	}


	public @Nullable JsonKeyword getAsJsonKeyword(int index) {
		return Jsonify.toJsonKeyword(get(index));
	}

	public @Nullable JsonString getAsJsonString(int index) {
		return Jsonify.toJsonString(get(index));
	}

	public @Nullable JsonNumber getAsJsonNumber(int index) {
		return Jsonify.toJsonNumber(get(index));
	}

	public @Nullable JsonArray getAsJsonArray(int index) {
		return Jsonify.toJsonArray(get(index));
	}

	public @Nullable JsonObject getAsJsonObject(int index) {
		return Jsonify.toJsonObject(get(index));
	}


	public @Nullable Boolean getAsBoolean(int index) {
		return Jsonify.toBoolean(get(index));
	}

	public @Nullable String getAsString(int index) {
		return Jsonify.toString(get(index));
	}

	public @Nullable Character getAsCharacter(int index) {
		return Jsonify.toCharacter(get(index));
	}

	public @Nullable Number getAsNumber(int index) {
		return Jsonify.toNumber(get(index));
	}

	public @Nullable Long getAsLong(int index) {
		return Jsonify.toLong(get(index));
	}

	public @Nullable Double getAsDouble(int index) {
		return Jsonify.toDouble(get(index));
	}

	public @Nullable BigInteger getAsBigInteger(int index) {
		return Jsonify.toBigInteger(get(index));
	}

	public @Nullable BigDecimal getAsBigDecimal(int index) {
		return Jsonify.toBigDecimal(get(index));
	}
}
