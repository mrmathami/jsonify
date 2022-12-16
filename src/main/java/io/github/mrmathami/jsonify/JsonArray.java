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
		final JsonElement element = get(index);
		if (element == JsonKeyword.NULL) return null;
		if (element == JsonKeyword.TRUE) return Boolean.TRUE;
		if (element == JsonKeyword.FALSE) return Boolean.FALSE;
		throw new IllegalArgumentException("Not a Boolean!");
	}

	public @Nullable String getAsString(int index) {
		final JsonElement element = get(index);
		if (element == JsonKeyword.NULL) return null;
		if (element instanceof JsonString) return element.toString();
		throw new IllegalArgumentException("Not a String!");
	}

	public @Nullable Character getAsCharacter(int index) {
		final JsonElement element = get(index);
		if (element == JsonKeyword.NULL) return null;
		if (element instanceof JsonString) {
			final JsonString string = (JsonString) element;
			final Character character = string.toCharacter();
			if (character != null) return character;
		}
		throw new IllegalArgumentException("Not a Character!");
	}

	public @Nullable Number getAsNumber(int index) {
		final JsonElement element = get(index);
		if (element == JsonKeyword.NULL) return null;
		if (element instanceof JsonNumber) {
			final JsonNumber number = (JsonNumber) element;
			return number.getValue();
		}
		throw new IllegalArgumentException("Not a Number!");
	}

	public @Nullable Long getAsLong(int index) {
		final JsonElement element = get(index);
		if (element == JsonKeyword.NULL) return null;
		if (element instanceof JsonNumber) {
			final JsonNumber number = (JsonNumber) element;
			final Long value = number.getAsLong();
			if (value != null) return value;
		}
		throw new IllegalArgumentException("Not a Long!");
	}

	public @Nullable Double getAsDouble(int index) {
		final JsonElement element = get(index);
		if (element == JsonKeyword.NULL) return null;
		if (element instanceof JsonNumber) {
			final JsonNumber number = (JsonNumber) element;
			final Double value = number.getAsDouble();
			if (value != null) return value;
		}
		throw new IllegalArgumentException("Not a Double!");
	}

	public @Nullable BigInteger getAsBigInteger(int index) {
		final JsonElement element = get(index);
		if (element == JsonKeyword.NULL) return null;
		if (element instanceof JsonNumber) {
			final JsonNumber number = (JsonNumber) element;
			final BigInteger value = number.getAsBigInteger();
			if (value != null) return value;
		}
		throw new IllegalArgumentException("Not a BigInteger!");
	}

	public @Nullable BigDecimal getAsBigDecimal(int index) {
		final JsonElement element = get(index);
		if (element == JsonKeyword.NULL) return null;
		if (element instanceof JsonNumber) {
			final JsonNumber number = (JsonNumber) element;
			final BigDecimal value = number.getAsBigDecimal();
			if (value != null) return value;
		}
		throw new IllegalArgumentException("Not a BigDecimal");
	}
}
