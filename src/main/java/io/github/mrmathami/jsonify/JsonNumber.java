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


	/**
	 * Create a {@link JsonNumber} with a {@code long} value.
	 */
	public JsonNumber(long value) {
		this.value = value;
	}

	/**
	 * Create a {@link JsonNumber} with a {@code double} value. Throws {@link NumberFormatException} when input value is
	 * NaN, +Infinity or -Infinity.
	 */
	public JsonNumber(double value) {
		if (Double.isFinite(value)) {
			this.value = value;
		} else {
			throw new NumberFormatException("JSON number cannot be NaN or Infinity.");
		}
	}

	/**
	 * Create a {@link JsonNumber} with a {@link BigInteger} value. The value will be saved as a {@link Long} if it
	 * fits.
	 */
	public JsonNumber(@NotNull BigInteger value) {
		this.value = tryIntegerToLong(value);
	}

	/**
	 * Create a {@link JsonNumber} with a {@link BigDecimal} value. The value will be saved as a {@link Double} if it
	 * fits.
	 */
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
		// double is precision(15..17) scale[-1022;1023]
		final int precision = decimal.precision();
		if (precision > 17) return decimal;
		final int scale = decimal.scale();
		if (scale < -1022 || scale > 1023) return decimal;
		final String formatString = "%." + precision + 'e';
		final double doubleValue = decimal.doubleValue();
		if (!Double.isFinite(doubleValue)) return decimal;
		final String doubleString = String.format(formatString, doubleValue);
		final String decimalString = String.format(formatString, decimal);
		if (doubleString.equals(decimalString)) return doubleValue;
		return decimal;
	}


	/**
	 * Check if this is an integer number (a {@link Long} or a {@link BigInteger}).
	 */
	public boolean isInteger() {
		return value instanceof Long || value instanceof BigInteger;
	}

	/**
	 * Check if this is a decimal number (a {@link Double} or a {@link BigDecimal}).
	 */
	public boolean isDecimal() {
		return value instanceof Double || value instanceof BigDecimal;
	}

	/**
	 * Check if this is a big number (a {@link BigInteger} or a {@link BigDecimal}).
	 */
	public boolean isBig() {
		return value instanceof BigInteger || value instanceof BigDecimal;
	}

	/**
	 * Return a {@link Number}. The number returned can only be a {@link Long}, a {@link Double}, a {@link BigInteger}
	 * or a {@link BigDecimal}.
	 */
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
