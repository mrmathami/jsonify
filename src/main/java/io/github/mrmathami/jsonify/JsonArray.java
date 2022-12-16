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
		return element instanceof JsonKeyword && element != JsonKeyword.NULL ? element == JsonKeyword.TRUE : null;
	}

	public @Nullable String getAsString(int index) {
		final JsonElement element = get(index);
		return element instanceof JsonString ? element.toString() : null;
	}

	public @Nullable Character getAsCharacter(int index) {
		final JsonElement element = get(index);
		return element instanceof JsonString ? ((JsonString) element).toCharacter() : null;
	}

	public @Nullable Number getAsNumber(int index) {
		final JsonElement element = get(index);
		return element instanceof JsonNumber ? ((JsonNumber) element).getValue() : null;
	}

	public @Nullable Long getAsLong(int index) {
		final JsonElement element = get(index);
		return element instanceof JsonNumber ? ((JsonNumber) element).getValueAsLong() : null;
	}

	public @Nullable Double getAsDouble(int index) {
		final JsonElement element = get(index);
		return element instanceof JsonNumber ? ((JsonNumber) element).getValueAsDouble() : null;
	}

	public @Nullable BigInteger getAsBigInteger(int index) {
		final JsonElement element = get(index);
		return element instanceof JsonNumber ? ((JsonNumber) element).getValueAsBigInteger() : null;
	}

	public @Nullable BigDecimal getAsBigDecimal(int index) {
		final JsonElement element = get(index);
		return element instanceof JsonNumber ? ((JsonNumber) element).getValueAsBigDecimal() : null;
	}
}
