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

import java.util.Objects;

public final class JsonPrimitive implements JsonElement {
	public static final @NotNull JsonPrimitive NULL = new JsonPrimitive(null);
	public static final @NotNull JsonPrimitive TRUE = new JsonPrimitive(Boolean.TRUE);
	public static final @NotNull JsonPrimitive FALSE = new JsonPrimitive(Boolean.FALSE);

	private final @Nullable Object value;

	private JsonPrimitive(@Nullable Object value) {
		this.value = value;
	}

	public static @NotNull JsonPrimitive of(@NotNull Boolean value) {
		return new JsonPrimitive(value);
	}

	public static @NotNull JsonPrimitive of(@NotNull Number value) {
		return new JsonPrimitive(value);
	}

	public static @NotNull JsonPrimitive of(@NotNull String value) {
		return new JsonPrimitive(value);
	}

	public static @NotNull JsonPrimitive of(@Nullable Object value) {
		if (value == null) return NULL;
		if (value instanceof Boolean) return (Boolean) value ? TRUE : FALSE;
		if (value instanceof Number || value instanceof String) return new JsonPrimitive(value);
		throw new IllegalArgumentException("Invalid input value type!");
	}

	public boolean isNull() {
		return this == NULL;
	}

	public boolean isBoolean() {
		return value instanceof Boolean;
	}

	public boolean isNumber() {
		return value instanceof Number;
	}

	public boolean isString() {
		return value instanceof String;
	}

	public @NotNull Boolean getBoolean() {
		if (value instanceof Boolean) return (Boolean) value;
		throw new IllegalStateException();
	}

	public @NotNull Number getNumber() {
		if (value instanceof Number) return (Number) value;
		throw new IllegalStateException();
	}

	public @NotNull String getString() {
		if (value instanceof String) return (String) value;
		throw new IllegalStateException();
	}

	@Override
	public boolean equals(@Nullable Object object) {
		if (this == object) return true;
		if (!(object instanceof JsonPrimitive)) return false;
		return Objects.equals(value, ((JsonPrimitive) object).value);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(value);
	}

	@Override
	public @NotNull String toString() {
		return Objects.toString(value);
	}
}
