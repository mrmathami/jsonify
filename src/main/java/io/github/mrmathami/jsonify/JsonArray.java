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


	public @Nullable Boolean getValueAsBoolean(int index) {
		final JsonElement element = get(index);
		return element instanceof JsonKeyword && element != JsonKeyword.NULL ? element == JsonKeyword.TRUE : null;
	}

	public @Nullable String getValueAsString(int index) {
		final JsonElement element = get(index);
		return element instanceof JsonString ? element.toString() : null;
	}

	public @Nullable Character getValueAsCharacter(int index) {
		final JsonElement element = get(index);
		return element instanceof JsonString ? ((JsonString) element).toCharacter() : null;
	}

	public @Nullable Number getValueAsNumber(int index) {
		final JsonElement element = get(index);
		return element instanceof JsonNumber ? ((JsonNumber) element).getValue() : null;
	}

	public @Nullable Long getValueAsLong(int index) {
		final JsonElement element = get(index);
		return element instanceof JsonNumber ? ((JsonNumber) element).getValueAsLong() : null;
	}

	public @Nullable Double getValueAsDouble(int index) {
		final JsonElement element = get(index);
		return element instanceof JsonNumber ? ((JsonNumber) element).getValueAsDouble() : null;
	}

	public @Nullable BigInteger getValueAsBigInteger(int index) {
		final JsonElement element = get(index);
		return element instanceof JsonNumber ? ((JsonNumber) element).getValueAsBigInteger() : null;
	}

	public @Nullable BigDecimal getValueAsBigDecimal(int index) {
		final JsonElement element = get(index);
		return element instanceof JsonNumber ? ((JsonNumber) element).getValueAsBigDecimal() : null;
	}
}
