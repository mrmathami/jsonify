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

/**
 * Json number. Can be lazy parsed if came from the reader.
 */
public final class JsonNumber implements JsonElement {
	private @NotNull Object value;

	public JsonNumber(int value) {
		this.value = value;
	}

	public JsonNumber(long value) {
		this.value = value;
	}

	public JsonNumber(float value) {
		this.value = value;
	}

	public JsonNumber(double value) {
		this.value = value;
	}

	public JsonNumber(@NotNull BigDecimal value) {
		this.value = value;
	}

	public JsonNumber(@NotNull BigInteger value) {
		this.value = value;
	}

	JsonNumber(@NotNull String string) {
		this.value = string;
	}

	public @NotNull Number toNumber() {
		if (value instanceof Number) return (Number) value;
		final BigDecimal decimal = new BigDecimal(value.toString());
		this.value = decimal;
		return decimal;
	}

	@NotNull String toStringLazy() {
		return value.toString();
	}

	@Override
	public @NotNull String toString() {
		return toNumber().toString();
	}

	@Override
	public boolean equals(@Nullable Object object) {
		return this == object || object instanceof JsonNumber && toString().equals(object.toString());
	}

	@Override
	public int hashCode() {
		return toNumber().hashCode();
	}
}
