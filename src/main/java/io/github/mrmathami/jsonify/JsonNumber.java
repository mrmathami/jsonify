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
	private final @NotNull Number value;
	private @Nullable Number bigValue;

	public JsonNumber(int value) {
		this.value = value;
		this.bigValue = null;
	}

	public JsonNumber(long value) {
		this.value = value;
		this.bigValue = null;
	}

	/**
	 * Throws {@link NumberFormatException} when value is NaN, +Infinity or -Infinity.
	 */
	public JsonNumber(float value) {
		this.value = value;
		this.bigValue = null;
		if (!Float.isFinite(value)) throw new NumberFormatException();
	}

	/**
	 * Throws {@link NumberFormatException} when value is NaN, +Infinity or -Infinity.
	 */
	public JsonNumber(double value) {
		this.value = value;
		this.bigValue = null;
		if (!Double.isFinite(value)) throw new NumberFormatException();
	}

	public JsonNumber(@NotNull BigDecimal value) {
		final Number number = tryConvertDecimalToInteger(value);
		this.value = number;
		this.bigValue = number;
	}

	public JsonNumber(@NotNull BigInteger value) {
		this.value = value;
		this.bigValue = value;
	}


	private static @NotNull Number tryConvertDecimalToInteger(@NotNull BigDecimal decimal) {
		try {
			final BigInteger integer = decimal.toBigIntegerExact();
			if (decimal.equals(new BigDecimal(integer))) return integer;
		} catch (final ArithmeticException ignored) {
		}
		return decimal;
	}

	private @NotNull Number bigValue() {
		if (bigValue != null) return bigValue;
		if (value instanceof Integer || value instanceof Long) {
			return this.bigValue = BigInteger.valueOf(value.longValue());
		} else if (value instanceof Float || value instanceof Double) {
			return this.bigValue = new BigDecimal(value.toString());
		} else {
			throw new AssertionError();
		}
	}


	public boolean isInteger() {
		return bigValue instanceof BigInteger || value instanceof Integer || value instanceof Long;
	}

	public boolean isDecimal() {
		return bigValue instanceof BigDecimal || value instanceof Float || value instanceof Double;
	}

	public @NotNull Number toNumber() {
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

	/**
	 * WARNING: This is an expensive operator!
	 */
	public @NotNull BigInteger toBigInteger() {
		final Number bigValue = bigValue();
		if (bigValue instanceof BigInteger) {
			return (BigInteger) bigValue;
		} else if (bigValue instanceof BigDecimal) {
			return ((BigDecimal) bigValue).toBigInteger();
		} else {
			throw new AssertionError();
		}
	}

	/**
	 * WARNING: This is an expensive operator!
	 */
	public @NotNull BigDecimal toBigDecimal() {
		final Number bigValue = bigValue();
		if (bigValue instanceof BigInteger) {
			return new BigDecimal((BigInteger) bigValue);
		} else if (bigValue instanceof BigDecimal) {
			return (BigDecimal) bigValue;
		} else {
			throw new AssertionError();
		}
	}


	/**
	 * Throws {@link ArithmeticException} if the value of this will not exactly fit in an int. WARNING: This is an
	 * expensive operator!
	 */
	public int intValueExact() {
		if (value instanceof Integer) return value.intValue();
		final Number bigValue = bigValue();
		if (bigValue instanceof BigInteger) {
			return ((BigInteger) bigValue).intValueExact();
		} else if (bigValue instanceof BigDecimal) {
			return ((BigDecimal) bigValue).intValueExact();
		} else {
			throw new AssertionError();
		}
	}

	/**
	 * Throws {@link ArithmeticException} if the value of this will not exactly fit in a long. WARNING: This is an
	 * expensive operator!
	 */
	public long longValueExact() {
		if (value instanceof Integer || value instanceof Long) return value.longValue();
		final Number bigValue = bigValue();
		if (bigValue instanceof BigInteger) {
			return ((BigInteger) bigValue).longValueExact();
		} else if (bigValue instanceof BigDecimal) {
			return ((BigDecimal) bigValue).longValueExact();
		} else {
			throw new AssertionError();
		}
	}

	/**
	 * Throws {@link ArithmeticException} if the value of this will not exactly fit in a float. WARNING: This is an
	 * expensive operator!
	 */
	public float floatValueExact() {
		if (value instanceof Float) return value.floatValue();
		final Number bigValue = bigValue();
		final float floatValue = bigValue.floatValue();
		try {
			if (bigValue instanceof BigDecimal) {
				if (BigDecimal.valueOf(floatValue).equals(bigValue)) {
					return floatValue;
				}
			} else if (bigValue instanceof BigInteger) {
				if (new BigDecimal(Float.toString(floatValue)).toBigIntegerExact().equals(bigValue)) {
					return floatValue;
				}
			} else {
				throw new AssertionError();
			}
		} catch (final NumberFormatException ignored) {
			// NumberFormatException is thrown when floatValue is NaN, +Infinity or -Infinity
		}
		throw new ArithmeticException();
	}

	/**
	 * Throws {@link ArithmeticException} if the value of this will not exactly fit in a double. WARNING: This is an
	 * expensive operator!
	 */
	public double doubleValueExact() {
		if (value instanceof Integer || value instanceof Float || value instanceof Double) return value.doubleValue();
		final Number bigValue = bigValue();
		final double doubleValue = bigValue.doubleValue();
		try {
			if (bigValue instanceof BigDecimal) {
				if (BigDecimal.valueOf(doubleValue).equals(bigValue)) {
					return doubleValue;
				}
			} else if (bigValue instanceof BigInteger) {
				if (new BigDecimal(Double.toString(doubleValue)).toBigIntegerExact().equals(bigValue)) {
					return doubleValue;
				}
			} else {
				throw new AssertionError();
			}
		} catch (final NumberFormatException ignored) {
			// NumberFormatException is thrown when doubleValue is NaN, +Infinity or -Infinity
		}
		throw new ArithmeticException();
	}

	/**
	 * Throws {@link ArithmeticException} if the value of this has a nonzero fractional part. WARNING: This is an
	 * expensive operator!
	 */
	public @NotNull BigInteger toBigIntegerExact() {
		final Number bigValue = bigValue();
		if (bigValue instanceof BigInteger) {
			return (BigInteger) bigValue;
		} else if (bigValue instanceof BigDecimal) {
			return ((BigDecimal) bigValue).toBigIntegerExact();
		} else {
			throw new AssertionError();
		}
	}

	/**
	 * Never throws anything, fully equals to {@link #toBigDecimal()}. WARNING: This is an  expensive operator!
	 */
	public @NotNull BigDecimal toBigDecimalExact() {
		return toBigDecimal();
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
		return value.equals(number.value) || bigValue().equals(number.bigValue());
	}

	@Override
	public int hashCode() {
		return Float.floatToIntBits(value.floatValue());
	}
}
