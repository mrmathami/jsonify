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
	public boolean add(boolean value) {
		return add(JsonKeyword.of(value));
	}

	public boolean add(@Nullable Boolean value) {
		return add(JsonKeyword.of(value));
	}

	public boolean add(char value) {
		return add(new JsonString(value));
	}

	public boolean add(@Nullable Character value) {
		return add(value != null ? new JsonString(value) : JsonKeyword.NULL);
	}

	public boolean add(@Nullable String value) {
		return add(value != null ? new JsonString(value) : JsonKeyword.NULL);
	}

	public boolean add(long value) {
		return add(new JsonNumber(value));
	}

	public boolean add(@Nullable Long value) {
		return add(value != null ? new JsonNumber(value) : JsonKeyword.NULL);
	}

	public boolean add(double value) {
		return add(new JsonNumber(value));
	}

	public boolean add(@Nullable Double value) {
		return add(value != null ? new JsonNumber(value) : JsonKeyword.NULL);
	}

	public boolean add(@Nullable BigInteger value) {
		return add(value != null ? new JsonNumber(value) : JsonKeyword.NULL);
	}

	public boolean add(@Nullable BigDecimal value) {
		return add(value != null ? new JsonNumber(value) : JsonKeyword.NULL);
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
