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
 * A JSON number. This implementation will reserve the exact value that appeared in the JSON: big integers are returned
 * as {@link BigInteger}, and high precision decimals are returned as {@link BigDecimal}. This does not violate the JSON
 * specification, as by RFC7159, the JSON specification "allows implementations to set limits on the range and precision
 * of numbers accepted.". However, the specification also suggested that "good interoperability can be achieved by
 * implementations that expect no more precision or range than these (IEEE 754-2008 binary64, usually called double
 * precision) provide, in the sense that implementations will approximate JSON numbers within the expected precision.".
 * In other words, don't expect other libraries to respect the exact values of higher-than-double-precision decimals
 * that this library produce. Also, please note that JSON specification doesn't allow NaN and Infinity.
 */
public final class JsonNumber extends Number implements JsonElement, JsonToken {
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
		this.value = tryDecimalToDouble(value);
	}


	private static @NotNull Number tryIntegerToLong(@NotNull BigInteger integer) {
		try {
			return integer.longValueExact();
		} catch (final ArithmeticException ignored) {
		}
		return integer;
	}

	private static @NotNull Number tryDecimalToDouble(@NotNull BigDecimal decimal) {
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
	 * Check if the inner value is an integer (a {@link Long} or a {@link BigInteger}).
	 */
	public boolean isInteger() {
		return value instanceof Long || value instanceof BigInteger;
	}

	/**
	 * Check if the inner value is a decimal (a {@link Double} or a {@link BigDecimal}).
	 */
	public boolean isDecimal() {
		return value instanceof Double || value instanceof BigDecimal;
	}

	/**
	 * Check if the inner value is a big number (a {@link BigInteger} or a {@link BigDecimal}).
	 */
	public boolean isBig() {
		return value instanceof BigInteger || value instanceof BigDecimal;
	}

	/**
	 * Return a {@link Number}. Note that the return value can only be a {@link Long}, a {@link Double}, a
	 * {@link BigInteger} or a {@link BigDecimal}.
	 */
	public @NotNull Number getValue() {
		return value;
	}

	/**
	 * Return a {@link Long} or {@code null} if the inner value is not a {@link Long}. Note that if the inner value is a
	 * {@link BigInteger}, this method still returns {@code null}.
	 */
	public @Nullable Long getAsLong() {
		return value instanceof Long ? (Long) value : null;
	}

	/**
	 * Return a {@link Double} or {@code null} if the inner value is not a {@link Double}. Note that if the inner value
	 * is a {@link BigDecimal}, this method still returns {@code null}.
	 */
	public @Nullable Double getAsDouble() {
		return value instanceof Double ? (Double) value : null;
	}

	/**
	 * Return a {@link BigInteger} or {@code null} if the inner value is a decimal (The {@link #isDecimal()} check
	 * returns {@code true}). If the inner value is a {@link Long}, this method create a {@link BigInteger} from it.
	 */
	public @Nullable BigInteger getAsBigInteger() {
		return value instanceof BigInteger
				? (BigInteger) value
				: value instanceof Long
				? BigInteger.valueOf((long) value)
				: null;
	}

	/**
	 * Return a {@link BigDecimal} or {@code null} if the inner value is an integer (The {@link #isInteger()} check
	 * returns {@code true}). If the inner value is a {@link Double}, this method create a
	 * {@link BigDecimal} from it.
	 */
	public @Nullable BigDecimal getAsBigDecimal() {
		return value instanceof BigDecimal
				? (BigDecimal) value
				: value instanceof Double
				? BigDecimal.valueOf((double) value)
				: null;
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
		return this == object || object instanceof JsonNumber number && value.equals(number.value);
	}

	@Override
	public int hashCode() {
		return value.hashCode();
	}
}
