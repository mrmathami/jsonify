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
 * Lazy parsed number.
 */
public final class LazyNumber extends Number {
	private final @NotNull String number;
	private @Nullable BigDecimal decimal;

	public LazyNumber(@NotNull String number) {
		this.number = number;
	}

	@Override
	public int intValue() {
		if (decimal == null) parse();
		return decimal.intValue();
	}

	@Override
	public long longValue() {
		if (decimal == null) parse();
		return decimal.longValue();
	}

	@Override
	public float floatValue() {
		if (decimal == null) parse();
		return decimal.floatValue();
	}

	@Override
	public double doubleValue() {
		if (decimal == null) parse();
		return decimal.doubleValue();
	}

	public @NotNull BigInteger asBigInteger() {
		if (decimal == null) parse();
		return decimal.toBigInteger();
	}

	public @NotNull BigDecimal asBigDecimal() {
		if (decimal == null) parse();
		return decimal;
	}

	private void parse() {
		this.decimal = new BigDecimal(number);
	}

	@Override
	public @NotNull String toString() {
		return number;
	}

	@Override
	public boolean equals(@Nullable Object object) {
		return this == object
				|| object instanceof LazyNumber lazyNumber && number.equals(lazyNumber.number)
				|| object instanceof Number normalNumber && number.equals(normalNumber.toString());
	}

	@Override
	public int hashCode() {
		return number.hashCode();
	}
}
