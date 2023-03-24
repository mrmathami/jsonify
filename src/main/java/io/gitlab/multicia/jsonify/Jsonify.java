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

package io.gitlab.multicia.jsonify;

import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;
import java.math.BigInteger;

public final class Jsonify {
	private Jsonify() {
	}


	public static @Nullable JsonKeyword toJsonKeyword(@Nullable JsonElement element) {
		if (element == null) return null;
		if (element instanceof JsonKeyword keyword) return keyword;
		throw new JsonValueException("Not a JsonKeyword!");
	}

	public static @Nullable JsonString toJsonString(@Nullable JsonElement element) {
		if (element == null) return null;
		if (element instanceof JsonString string) return string;
		throw new JsonValueException("Not a JsonString!");
	}

	public static @Nullable JsonNumber toJsonNumber(@Nullable JsonElement element) {
		if (element == null) return null;
		if (element instanceof JsonNumber number) return number;
		throw new JsonValueException("Not a JsonNumber!");
	}

	public static @Nullable JsonArray toJsonArray(@Nullable JsonElement element) {
		if (element == null) return null;
		if (element instanceof JsonArray array) return array;
		throw new JsonValueException("Not a JsonArray!");
	}

	public static @Nullable JsonObject toJsonObject(@Nullable JsonElement element) {
		if (element == null) return null;
		if (element instanceof JsonObject object) return object;
		throw new JsonValueException("Not a JsonObject!");
	}


	public static @Nullable Boolean toBoolean(@Nullable JsonElement element) {
		if (element == null || element == JsonKeyword.NULL) return null;
		if (element == JsonKeyword.TRUE) return Boolean.TRUE;
		if (element == JsonKeyword.FALSE) return Boolean.FALSE;
		throw new JsonValueException("Not a Boolean!");
	}

	public static @Nullable String toString(@Nullable JsonElement element) {
		if (element == null || element == JsonKeyword.NULL) return null;
		if (element instanceof JsonString) return element.toString();
		throw new JsonValueException("Not a String!");
	}

	public static @Nullable Character toCharacter(@Nullable JsonElement element) {
		if (element == null || element == JsonKeyword.NULL) return null;
		if (element instanceof JsonString string) {
			final Character character = string.toCharacter();
			if (character != null) return character;
		}
		throw new JsonValueException("Not a Character!");
	}

	public static @Nullable Number toNumber(@Nullable JsonElement element) {
		if (element == null || element == JsonKeyword.NULL) return null;
		if (element instanceof JsonNumber number) {
			return number.getValue();
		}
		throw new JsonValueException("Not a Number!");
	}

	public static @Nullable Long toLong(@Nullable JsonElement element) {
		if (element == null || element == JsonKeyword.NULL) return null;
		if (element instanceof JsonNumber number) {
			final Long value = number.getAsLong();
			if (value != null) return value;
		}
		throw new JsonValueException("Not a Long!");
	}

	public static @Nullable Double toDouble(@Nullable JsonElement element) {
		if (element == null || element == JsonKeyword.NULL) return null;
		if (element instanceof JsonNumber number) {
			final Double value = number.getAsDouble();
			if (value != null) return value;
		}
		throw new JsonValueException("Not a Double!");
	}

	public static @Nullable BigInteger toBigInteger(@Nullable JsonElement element) {
		if (element == null || element == JsonKeyword.NULL) return null;
		if (element instanceof JsonNumber number) {
			final BigInteger value = number.getAsBigInteger();
			if (value != null) return value;
		}
		throw new JsonValueException("Not a BigInteger!");
	}

	public static @Nullable BigDecimal toBigDecimal(@Nullable JsonElement element) {
		if (element == null || element == JsonKeyword.NULL) return null;
		if (element instanceof JsonNumber number) {
			final BigDecimal value = number.getAsBigDecimal();
			if (value != null) return value;
		}
		throw new JsonValueException("Not a BigDecimal!");
	}
}
