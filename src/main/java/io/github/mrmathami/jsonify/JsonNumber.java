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
 * JSON number. Note that JSON standard doesn't allow NaN and Infinity.
 */
public final class JsonNumber extends Number implements JsonElement {
	/**
	 * If able, {@link Long} or {@link Double} type without losing precision.
	 */
	private final @NotNull Number value;


	public JsonNumber(long value) {
		this.value = value;
	}

	/**
	 * Throws {@link NumberFormatException} when {@code value} is NaN, +Infinity or -Infinity.
	 */
	public JsonNumber(double value) {
		if (Double.isFinite(value)) {
			this.value = value;
		} else {
			throw new NumberFormatException("JSON number cannot be NaN or Infinity.");
		}
	}

	public JsonNumber(@NotNull BigInteger value) {
		this.value = tryIntegerToLong(value);
	}

	public JsonNumber(@NotNull BigDecimal value) {
		this.value = tryDecimalToPrimitive(value);
	}


	private static @NotNull Number tryIntegerToLong(@NotNull BigInteger integer) {
		try {
			return integer.longValueExact();
		} catch (final ArithmeticException ignored) {
		}
		return integer;
	}

	private static @NotNull Number tryDecimalToPrimitive(@NotNull BigDecimal decimal) {
		final int precision = decimal.precision();
		final int scale = decimal.scale();
		// double is precision(15..17) scale[-1022;1023]
		if (precision <= 17 && scale >= -1022 && scale <= 1023) {
			final String formatString = "%." + precision + 'e';
			final double doubleValue = decimal.doubleValue();
			if (Double.isFinite(doubleValue)) {
				final String doubleString = String.format(formatString, doubleValue);
				final String decimalString = String.format(formatString, decimal);
				if (doubleString.equals(decimalString)) return doubleValue;
			}
		}
		return decimal;
	}


	public boolean isInteger() {
		return value instanceof BigInteger || value instanceof Long;
	}

	public boolean isDecimal() {
		return value instanceof BigDecimal || value instanceof Double;
	}

	public @NotNull Number getValue() {
		return value;
	}


	@Override
	public int intValue() {
		return value.intValue();
	}

	@Override
	public long longValue() {
		return value.longValue();
	}

	@Override
	public float floatValue() {
		return value.floatValue();
	}

	@Override
	public double doubleValue() {
		return value.doubleValue();
	}

	@Override
	public @NotNull String toString() {
		return value.toString();
	}

	@Override
	public boolean equals(@Nullable Object object) {
		if (this == object) return true;
		if (!(object instanceof JsonNumber)) return false;
		final JsonNumber number = (JsonNumber) object;
		return value.equals(number.value);
	}

	@Override
	public int hashCode() {
		return value.hashCode();
	}
}
