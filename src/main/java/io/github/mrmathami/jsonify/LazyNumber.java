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
	private final @NotNull String numberString;
	private @Nullable BigDecimal decimal;

	public LazyNumber(@NotNull String numberString) {
		this.numberString = numberString;
	}

	@Override
	public int intValue() {
		return asBigDecimal().intValue();
	}

	@Override
	public long longValue() {
		return asBigDecimal().longValue();
	}

	@Override
	public float floatValue() {
		return asBigDecimal().floatValue();
	}

	@Override
	public double doubleValue() {
		return asBigDecimal().doubleValue();
	}

	public @NotNull BigInteger asBigInteger() {
		return asBigDecimal().toBigInteger();
	}

	public @NotNull BigDecimal asBigDecimal() {
		return decimal != null ? decimal : (this.decimal = new BigDecimal(numberString));
	}

	@Override
	public @NotNull String toString() {
		return asBigDecimal().toString();
	}

	@Override
	public boolean equals(@Nullable Object object) {
		return this == object
				|| object instanceof LazyNumber lazyNumber && asBigDecimal().equals(lazyNumber.asBigDecimal())
				|| object instanceof Number normalNumber && asBigDecimal().toString().equals(normalNumber.toString());
	}

	@Override
	public int hashCode() {
		return asBigDecimal().hashCode();
	}
}
