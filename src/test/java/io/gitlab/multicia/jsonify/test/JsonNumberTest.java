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

package io.gitlab.multicia.jsonify.test;

import io.gitlab.multicia.jsonify.JsonNumber;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.ThreadLocalRandom;

public class JsonNumberTest {
	@Test
	public void integerZero() {
		final JsonNumber number = new JsonNumber(0);
		Assertions.assertTrue(number.isInteger());
		Assertions.assertFalse(number.isDecimal());
		Assertions.assertEquals(number.intValue(), 0);
		Assertions.assertEquals(number.longValue(), 0L);
		Assertions.assertEquals(number.floatValue(), 0.0f);
		Assertions.assertEquals(number.doubleValue(), 0.0);
		Assertions.assertEquals(number.getValue(), 0L);
	}

	@Test
	public void integerMin() {
		final JsonNumber number = new JsonNumber(Integer.MIN_VALUE);
		Assertions.assertTrue(number.isInteger());
		Assertions.assertFalse(number.isDecimal());
		Assertions.assertEquals(number.intValue(), Integer.MIN_VALUE);
		Assertions.assertEquals(number.longValue(), Integer.MIN_VALUE);
		Assertions.assertEquals(number.floatValue(), Integer.MIN_VALUE);
		Assertions.assertEquals(number.doubleValue(), Integer.MIN_VALUE);
		Assertions.assertEquals(number.getValue(), (long) Integer.MIN_VALUE);
	}

	@Test
	public void integerMax() {
		final JsonNumber number = new JsonNumber(Integer.MAX_VALUE);
		Assertions.assertTrue(number.isInteger());
		Assertions.assertFalse(number.isDecimal());
		Assertions.assertEquals(number.intValue(), Integer.MAX_VALUE);
		Assertions.assertEquals(number.longValue(), Integer.MAX_VALUE);
		Assertions.assertEquals(number.floatValue(), Integer.MAX_VALUE);
		Assertions.assertEquals(number.doubleValue(), Integer.MAX_VALUE);
		Assertions.assertEquals(number.getValue(), (long) Integer.MAX_VALUE);
	}


	@Test
	public void longZero() {
		final JsonNumber number = new JsonNumber(0L);
		Assertions.assertTrue(number.isInteger());
		Assertions.assertFalse(number.isDecimal());
		Assertions.assertEquals(number.intValue(), 0);
		Assertions.assertEquals(number.longValue(), 0L);
		Assertions.assertEquals(number.floatValue(), 0.0f);
		Assertions.assertEquals(number.doubleValue(), 0.0);
		Assertions.assertEquals(number.getValue(), 0L);
	}

	@Test
	public void longMin() {
		final JsonNumber number = new JsonNumber(Long.MIN_VALUE);
		Assertions.assertTrue(number.isInteger());
		Assertions.assertFalse(number.isDecimal());
		Assertions.assertEquals(number.intValue(), (int) Long.MIN_VALUE);
		Assertions.assertEquals(number.longValue(), Long.MIN_VALUE);
		Assertions.assertEquals(number.floatValue(), Long.MIN_VALUE);
		Assertions.assertEquals(number.doubleValue(), Long.MIN_VALUE);
		Assertions.assertEquals(number.getValue(), Long.MIN_VALUE);
	}

	@Test
	public void longMax() {
		final JsonNumber number = new JsonNumber(Long.MAX_VALUE);
		Assertions.assertTrue(number.isInteger());
		Assertions.assertFalse(number.isDecimal());
		Assertions.assertEquals(number.intValue(), (int) Long.MAX_VALUE);
		Assertions.assertEquals(number.longValue(), Long.MAX_VALUE);
		Assertions.assertEquals(number.floatValue(), Long.MAX_VALUE);
		Assertions.assertEquals(number.doubleValue(), Long.MAX_VALUE);
		Assertions.assertEquals(number.getValue(), Long.MAX_VALUE);
	}


	@Test
	public void floatZero() {
		final JsonNumber number = new JsonNumber(0.0f);
		Assertions.assertFalse(number.isInteger());
		Assertions.assertTrue(number.isDecimal());
		Assertions.assertEquals(number.intValue(), 0);
		Assertions.assertEquals(number.longValue(), 0L);
		Assertions.assertEquals(number.floatValue(), 0.0f);
		Assertions.assertEquals(number.doubleValue(), 0.0);
		Assertions.assertEquals(number.getValue(), 0.0);
	}

	@Test
	public void floatMin() {
		final JsonNumber number = new JsonNumber(Float.MIN_VALUE);
		Assertions.assertFalse(number.isInteger());
		Assertions.assertTrue(number.isDecimal());
		Assertions.assertEquals(number.intValue(), 0);
		Assertions.assertEquals(number.longValue(), 0L);
		Assertions.assertEquals(number.floatValue(), Float.MIN_VALUE);
		Assertions.assertEquals(number.doubleValue(), Float.MIN_VALUE);
		Assertions.assertEquals(number.getValue(), (double) Float.MIN_VALUE);
	}

	@Test
	public void floatMinNormal() {
		final JsonNumber number = new JsonNumber(Float.MIN_NORMAL);
		Assertions.assertFalse(number.isInteger());
		Assertions.assertTrue(number.isDecimal());
		Assertions.assertEquals(number.intValue(), 0);
		Assertions.assertEquals(number.longValue(), 0L);
		Assertions.assertEquals(number.floatValue(), Float.MIN_NORMAL);
		Assertions.assertEquals(number.doubleValue(), Float.MIN_NORMAL);
		Assertions.assertEquals(number.getValue(), (double) Float.MIN_NORMAL);
	}

	@Test
	public void floatMax() {
		final JsonNumber number = new JsonNumber(Float.MAX_VALUE);
		Assertions.assertFalse(number.isInteger());
		Assertions.assertTrue(number.isDecimal());
		Assertions.assertEquals(number.intValue(), Integer.MAX_VALUE);
		Assertions.assertEquals(number.longValue(), Long.MAX_VALUE);
		Assertions.assertEquals(number.floatValue(), Float.MAX_VALUE);
		Assertions.assertEquals(number.doubleValue(), Float.MAX_VALUE);
		Assertions.assertEquals(number.getValue(), (double) Float.MAX_VALUE);
	}

	@Test
	public void floatSpecial() {
		Assertions.assertThrows(NumberFormatException.class, () -> new JsonNumber(Float.NaN));
		Assertions.assertThrows(NumberFormatException.class, () -> new JsonNumber(Float.POSITIVE_INFINITY));
		Assertions.assertThrows(NumberFormatException.class, () -> new JsonNumber(Float.NEGATIVE_INFINITY));
	}


	@Test
	public void doubleZero() {
		final JsonNumber number = new JsonNumber(0.0);
		Assertions.assertFalse(number.isInteger());
		Assertions.assertTrue(number.isDecimal());
		Assertions.assertEquals(number.intValue(), 0);
		Assertions.assertEquals(number.longValue(), 0L);
		Assertions.assertEquals(number.floatValue(), 0.0f);
		Assertions.assertEquals(number.doubleValue(), 0.0);
		Assertions.assertEquals(number.getValue(), 0.0);
	}

	@Test
	public void doubleMin() {
		final JsonNumber number = new JsonNumber(Double.MIN_VALUE);
		Assertions.assertFalse(number.isInteger());
		Assertions.assertTrue(number.isDecimal());
		Assertions.assertEquals(number.intValue(), 0);
		Assertions.assertEquals(number.longValue(), 0L);
		Assertions.assertEquals(number.floatValue(), 0.0f);
		Assertions.assertEquals(number.doubleValue(), Double.MIN_VALUE);
		Assertions.assertEquals(number.getValue(), Double.MIN_VALUE);
	}

	@Test
	public void doubleMinNormal() {
		final JsonNumber number = new JsonNumber(Double.MIN_NORMAL);
		Assertions.assertFalse(number.isInteger());
		Assertions.assertTrue(number.isDecimal());
		Assertions.assertEquals(number.intValue(), 0);
		Assertions.assertEquals(number.longValue(), 0L);
		Assertions.assertEquals(number.floatValue(), 0.0f);
		Assertions.assertEquals(number.doubleValue(), Double.MIN_NORMAL);
		Assertions.assertEquals(number.getValue(), Double.MIN_NORMAL);
	}

	@Test
	public void doubleMax() {
		final JsonNumber number = new JsonNumber(Double.MAX_VALUE);
		Assertions.assertFalse(number.isInteger());
		Assertions.assertTrue(number.isDecimal());
		Assertions.assertEquals(number.intValue(), Integer.MAX_VALUE);
		Assertions.assertEquals(number.longValue(), Long.MAX_VALUE);
		Assertions.assertEquals(number.floatValue(), Float.POSITIVE_INFINITY);
		Assertions.assertEquals(number.doubleValue(), Double.MAX_VALUE);
		Assertions.assertEquals(number.getValue(), Double.MAX_VALUE);
	}

	@Test
	public void doubleSpecial() {
		Assertions.assertThrows(NumberFormatException.class, () -> new JsonNumber(Double.NaN));
		Assertions.assertThrows(NumberFormatException.class, () -> new JsonNumber(Double.POSITIVE_INFINITY));
		Assertions.assertThrows(NumberFormatException.class, () -> new JsonNumber(Double.NEGATIVE_INFINITY));
	}


	@Test
	public void bigIntegerZero() {
		final JsonNumber number = new JsonNumber(BigInteger.ZERO);
		Assertions.assertTrue(number.isInteger());
		Assertions.assertFalse(number.isDecimal());
		Assertions.assertEquals(number.intValue(), 0);
		Assertions.assertEquals(number.longValue(), 0L);
		Assertions.assertEquals(number.floatValue(), 0.0f);
		Assertions.assertEquals(number.doubleValue(), 0.0);
		Assertions.assertEquals(number.getValue(), 0L);
	}

	@Test
	public void bigIntegerNegativeSmall() {
		final JsonNumber number = new JsonNumber(BigInteger.valueOf(Long.MIN_VALUE));
		Assertions.assertTrue(number.isInteger());
		Assertions.assertFalse(number.isDecimal());
		Assertions.assertEquals(number.intValue(), (int) Long.MIN_VALUE);
		Assertions.assertEquals(number.longValue(), Long.MIN_VALUE);
		Assertions.assertEquals(number.floatValue(), Long.MIN_VALUE);
		Assertions.assertEquals(number.doubleValue(), Long.MIN_VALUE);
		Assertions.assertEquals(number.getValue(), Long.MIN_VALUE);
	}

	@Test
	public void bigIntegerNegativeLarge() {
		final BigInteger integer = BigInteger.probablePrime(80, ThreadLocalRandom.current()).negate();
		final JsonNumber number = new JsonNumber(integer);
		Assertions.assertTrue(number.isInteger());
		Assertions.assertFalse(number.isDecimal());
		Assertions.assertEquals(number.intValue(), integer.intValue());
		Assertions.assertEquals(number.longValue(), integer.longValue());
		Assertions.assertEquals(number.floatValue(), integer.floatValue());
		Assertions.assertEquals(number.doubleValue(), integer.doubleValue());
		Assertions.assertEquals(number.getValue(), integer);
	}

	@Test
	public void bigIntegerPositiveSmall() {
		final JsonNumber number = new JsonNumber(BigInteger.valueOf(Long.MAX_VALUE));
		Assertions.assertTrue(number.isInteger());
		Assertions.assertFalse(number.isDecimal());
		Assertions.assertEquals(number.intValue(), (int) Long.MAX_VALUE);
		Assertions.assertEquals(number.longValue(), Long.MAX_VALUE);
		Assertions.assertEquals(number.floatValue(), Long.MAX_VALUE);
		Assertions.assertEquals(number.doubleValue(), Long.MAX_VALUE);
		Assertions.assertEquals(number.getValue(), Long.MAX_VALUE);
	}

	@Test
	public void bigIntegerPositiveLarge() {
		final BigInteger integer = BigInteger.probablePrime(80, ThreadLocalRandom.current());
		final JsonNumber number = new JsonNumber(integer);
		Assertions.assertTrue(number.isInteger());
		Assertions.assertFalse(number.isDecimal());
		Assertions.assertEquals(number.intValue(), integer.intValue());
		Assertions.assertEquals(number.longValue(), integer.longValue());
		Assertions.assertEquals(number.floatValue(), integer.floatValue());
		Assertions.assertEquals(number.doubleValue(), integer.doubleValue());
		Assertions.assertEquals(number.getValue(), integer);
	}


	@Test
	public void bigDecimalZero() {
		final JsonNumber number = new JsonNumber(BigDecimal.ZERO);
		Assertions.assertFalse(number.isInteger());
		Assertions.assertTrue(number.isDecimal());
		Assertions.assertEquals(number.intValue(), 0);
		Assertions.assertEquals(number.longValue(), 0L);
		Assertions.assertEquals(number.floatValue(), 0.0f);
		Assertions.assertEquals(number.doubleValue(), 0.0);
		Assertions.assertEquals(number.getValue(), 0.0);
	}

	@Test
	public void bigDecimalSmallDoubleMin() {
		final JsonNumber number = new JsonNumber(BigDecimal.valueOf(Double.MIN_VALUE));
		Assertions.assertFalse(number.isInteger());
		Assertions.assertTrue(number.isDecimal());
		Assertions.assertEquals(number.intValue(), 0);
		Assertions.assertEquals(number.longValue(), 0L);
		Assertions.assertEquals(number.floatValue(), 0.0f);
		Assertions.assertEquals(number.doubleValue(), Double.MIN_VALUE);
		Assertions.assertEquals(number.getValue(), Double.MIN_VALUE);
	}

	@Test
	public void bigDecimalSmallDoubleMinNormal() {
		final JsonNumber number = new JsonNumber(BigDecimal.valueOf(Double.MIN_NORMAL));
		Assertions.assertFalse(number.isInteger());
		Assertions.assertTrue(number.isDecimal());
		Assertions.assertEquals(number.intValue(), 0);
		Assertions.assertEquals(number.longValue(), 0L);
		Assertions.assertEquals(number.floatValue(), 0.0f);
		Assertions.assertEquals(number.doubleValue(), Double.MIN_NORMAL);
		Assertions.assertEquals(number.getValue(), Double.MIN_NORMAL);
	}

	@Test
	public void bigDecimalSmallDoubleMax() {
		final JsonNumber number = new JsonNumber(BigDecimal.valueOf(Double.MAX_VALUE));
		Assertions.assertFalse(number.isInteger());
		Assertions.assertTrue(number.isDecimal());
		Assertions.assertEquals(number.intValue(), Integer.MAX_VALUE);
		Assertions.assertEquals(number.longValue(), Long.MAX_VALUE);
		Assertions.assertEquals(number.floatValue(), Float.POSITIVE_INFINITY);
		Assertions.assertEquals(number.doubleValue(), Double.MAX_VALUE);
		Assertions.assertEquals(number.getValue(), Double.MAX_VALUE);
	}

	@Test
	public void bigDecimalLarge() {
		final BigDecimal decimal = new BigDecimal(BigInteger.probablePrime(80, ThreadLocalRandom.current()), 64);
		final JsonNumber number = new JsonNumber(decimal);
		Assertions.assertFalse(number.isInteger());
		Assertions.assertTrue(number.isDecimal());
		Assertions.assertEquals(number.intValue(), decimal.intValue());
		Assertions.assertEquals(number.longValue(), decimal.longValue());
		Assertions.assertEquals(number.floatValue(), decimal.floatValue());
		Assertions.assertEquals(number.doubleValue(), decimal.doubleValue());
		Assertions.assertEquals(number.getValue(), decimal);
	}
}
