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

package io.github.mrmathami.jsonify.test;

import io.github.mrmathami.jsonify.JsonNumber;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.BigInteger;

public class JsonNumberTest {
	@Test
	public void integerZero() {
		final JsonNumber number = new JsonNumber(0);
		Assertions.assertTrue(number.isInteger());
		Assertions.assertFalse(number.isDecimal());
		Assertions.assertEquals(0, number.intValue());
		Assertions.assertEquals(0, number.intValueExact());
		Assertions.assertEquals(0L, number.longValue());
		Assertions.assertEquals(0L, number.longValueExact());
		Assertions.assertEquals(0.0f, number.floatValue());
		Assertions.assertEquals(0.0f, number.floatValueExact());
		Assertions.assertEquals(0.0, number.doubleValue());
		Assertions.assertEquals(0.0, number.doubleValueExact());
		Assertions.assertEquals(BigInteger.valueOf(0), number.toBigInteger());
		Assertions.assertEquals(BigInteger.valueOf(0), number.toBigIntegerExact());
		Assertions.assertEquals(BigDecimal.valueOf(0), number.toBigDecimal());
	}

	@Test
	public void integerMin() {
		final JsonNumber number = new JsonNumber(Integer.MIN_VALUE);
		Assertions.assertTrue(number.isInteger());
		Assertions.assertFalse(number.isDecimal());
		Assertions.assertEquals(Integer.MIN_VALUE, number.intValue());
		Assertions.assertEquals(Integer.MIN_VALUE, number.intValueExact());
		Assertions.assertEquals(Integer.MIN_VALUE, number.longValue());
		Assertions.assertEquals(Integer.MIN_VALUE, number.longValueExact());
		Assertions.assertEquals(Integer.MIN_VALUE, number.floatValue());
		Assertions.assertThrows(ArithmeticException.class, number::floatValueExact);
		Assertions.assertEquals(Integer.MIN_VALUE, number.doubleValue());
		Assertions.assertEquals(Integer.MIN_VALUE, number.doubleValueExact());
		Assertions.assertEquals(BigInteger.valueOf(Integer.MIN_VALUE), number.toBigInteger());
		Assertions.assertEquals(BigInteger.valueOf(Integer.MIN_VALUE), number.toBigIntegerExact());
		Assertions.assertEquals(BigDecimal.valueOf(Integer.MIN_VALUE), number.toBigDecimal());
	}

	@Test
	public void integerMax() {
		final JsonNumber number = new JsonNumber(Integer.MAX_VALUE);
		Assertions.assertTrue(number.isInteger());
		Assertions.assertFalse(number.isDecimal());
		Assertions.assertEquals(Integer.MAX_VALUE, number.intValue());
		Assertions.assertEquals(Integer.MAX_VALUE, number.intValueExact());
		Assertions.assertEquals(Integer.MAX_VALUE, number.longValue());
		Assertions.assertEquals(Integer.MAX_VALUE, number.longValueExact());
		Assertions.assertEquals(Integer.MAX_VALUE, number.floatValue());
		Assertions.assertThrows(ArithmeticException.class, number::floatValueExact);
		Assertions.assertEquals(Integer.MAX_VALUE, number.doubleValue());
		Assertions.assertEquals(Integer.MAX_VALUE, number.doubleValueExact());
		Assertions.assertEquals(BigInteger.valueOf(Integer.MAX_VALUE), number.toBigInteger());
		Assertions.assertEquals(BigInteger.valueOf(Integer.MAX_VALUE), number.toBigIntegerExact());
		Assertions.assertEquals(BigDecimal.valueOf(Integer.MAX_VALUE), number.toBigDecimal());
	}

	@Test
	public void longZero() {
		final JsonNumber number = new JsonNumber(0L);
		Assertions.assertTrue(number.isInteger());
		Assertions.assertFalse(number.isDecimal());
		Assertions.assertEquals(0, number.intValue());
		Assertions.assertEquals(0, number.intValueExact());
		Assertions.assertEquals(0L, number.longValue());
		Assertions.assertEquals(0L, number.longValueExact());
		Assertions.assertEquals(0.0f, number.floatValue());
		Assertions.assertEquals(0.0f, number.floatValueExact());
		Assertions.assertEquals(0.0, number.doubleValue());
		Assertions.assertEquals(0.0, number.doubleValueExact());
		Assertions.assertEquals(BigInteger.valueOf(0), number.toBigInteger());
		Assertions.assertEquals(BigInteger.valueOf(0), number.toBigIntegerExact());
		Assertions.assertEquals(BigDecimal.valueOf(0), number.toBigDecimal());
	}

	@Test
	public void longMin() {
		final JsonNumber number = new JsonNumber(Long.MIN_VALUE);
		Assertions.assertTrue(number.isInteger());
		Assertions.assertFalse(number.isDecimal());
		Assertions.assertEquals((int) Long.MIN_VALUE, number.intValue());
		Assertions.assertThrows(ArithmeticException.class, number::intValueExact);
		Assertions.assertEquals(Long.MIN_VALUE, number.longValue());
		Assertions.assertEquals(Long.MIN_VALUE, number.longValueExact());
		Assertions.assertEquals(Long.MIN_VALUE, number.floatValue());
		Assertions.assertThrows(ArithmeticException.class, number::floatValueExact);
		Assertions.assertEquals(Long.MIN_VALUE, number.doubleValue());
		Assertions.assertThrows(ArithmeticException.class, number::doubleValueExact);
		Assertions.assertEquals(BigInteger.valueOf(Long.MIN_VALUE), number.toBigInteger());
		Assertions.assertEquals(BigInteger.valueOf(Long.MIN_VALUE), number.toBigIntegerExact());
		Assertions.assertEquals(BigDecimal.valueOf(Long.MIN_VALUE), number.toBigDecimal());
	}

	@Test
	public void longMax() {
		final JsonNumber number = new JsonNumber(Long.MAX_VALUE);
		Assertions.assertTrue(number.isInteger());
		Assertions.assertFalse(number.isDecimal());
		Assertions.assertEquals((int) Long.MAX_VALUE, number.intValue());
		Assertions.assertThrows(ArithmeticException.class, number::intValueExact);
		Assertions.assertEquals(Long.MAX_VALUE, number.longValue());
		Assertions.assertEquals(Long.MAX_VALUE, number.longValueExact());
		Assertions.assertEquals(Long.MAX_VALUE, number.floatValue());
		Assertions.assertThrows(ArithmeticException.class, number::floatValueExact);
		Assertions.assertEquals(Long.MAX_VALUE, number.doubleValue());
		Assertions.assertThrows(ArithmeticException.class, number::doubleValueExact);
		Assertions.assertEquals(BigInteger.valueOf(Long.MAX_VALUE), number.toBigInteger());
		Assertions.assertEquals(BigInteger.valueOf(Long.MAX_VALUE), number.toBigIntegerExact());
		Assertions.assertEquals(BigDecimal.valueOf(Long.MAX_VALUE), number.toBigDecimal());
	}

	@Test
	public void floatZero() {
		final JsonNumber number = new JsonNumber(0.0f);
		Assertions.assertFalse(number.isInteger());
		Assertions.assertTrue(number.isDecimal());
		Assertions.assertEquals(0, number.intValue());
		Assertions.assertEquals(0, number.intValueExact());
		Assertions.assertEquals(0L, number.longValue());
		Assertions.assertEquals(0L, number.longValueExact());
		Assertions.assertEquals(0.0f, number.floatValue());
		Assertions.assertEquals(0.0f, number.floatValueExact());
		Assertions.assertEquals(0.0, number.doubleValue());
		Assertions.assertEquals(0.0, number.doubleValueExact());
		Assertions.assertEquals(BigInteger.valueOf(0), number.toBigInteger());
		Assertions.assertEquals(BigInteger.valueOf(0), number.toBigIntegerExact());
		Assertions.assertEquals(BigDecimal.valueOf(0.0), number.toBigDecimal());
	}

	@Test
	public void floatMin() {
		final JsonNumber number = new JsonNumber(Float.MIN_VALUE);
		Assertions.assertFalse(number.isInteger());
		Assertions.assertTrue(number.isDecimal());
		Assertions.assertEquals(0, number.intValue());
		Assertions.assertThrows(ArithmeticException.class, number::intValueExact);
		Assertions.assertEquals(0L, number.longValue());
		Assertions.assertThrows(ArithmeticException.class, number::longValueExact);
		Assertions.assertEquals(Float.MIN_VALUE, number.floatValue());
		Assertions.assertEquals(Float.MIN_VALUE, number.floatValueExact());
		Assertions.assertEquals(Float.MIN_VALUE, number.doubleValue());
		Assertions.assertEquals(Float.MIN_VALUE, number.doubleValueExact());
		Assertions.assertEquals(BigInteger.valueOf(0L), number.toBigInteger());
		Assertions.assertThrows(ArithmeticException.class, number::toBigIntegerExact);
		Assertions.assertEquals(new BigDecimal(Float.toString(Float.MIN_VALUE)), number.toBigDecimal());
	}

	@Test
	public void floatMinNormal() {
		final JsonNumber number = new JsonNumber(Float.MIN_NORMAL);
		Assertions.assertFalse(number.isInteger());
		Assertions.assertTrue(number.isDecimal());
		Assertions.assertEquals(0, number.intValue());
		Assertions.assertThrows(ArithmeticException.class, number::intValueExact);
		Assertions.assertEquals(0L, number.longValue());
		Assertions.assertThrows(ArithmeticException.class, number::longValueExact);
		Assertions.assertEquals(Float.MIN_NORMAL, number.floatValue());
		Assertions.assertEquals(Float.MIN_NORMAL, number.floatValueExact());
		Assertions.assertEquals(Float.MIN_NORMAL, number.doubleValue());
		Assertions.assertEquals(Float.MIN_NORMAL, number.doubleValueExact());
		Assertions.assertEquals(BigInteger.valueOf(0L), number.toBigInteger());
		Assertions.assertThrows(ArithmeticException.class, number::toBigIntegerExact);
		Assertions.assertEquals(new BigDecimal(Float.toString(Float.MIN_NORMAL)), number.toBigDecimal());
	}

	@Test
	public void floatMax() {
		final JsonNumber number = new JsonNumber(Float.MAX_VALUE);
		Assertions.assertFalse(number.isInteger());
		Assertions.assertTrue(number.isDecimal());
		Assertions.assertEquals(Integer.MAX_VALUE, number.intValue());
		Assertions.assertThrows(ArithmeticException.class, number::intValueExact);
		Assertions.assertEquals(Long.MAX_VALUE, number.longValue());
		Assertions.assertThrows(ArithmeticException.class, number::longValueExact);
		Assertions.assertEquals(Float.MAX_VALUE, number.floatValue());
		Assertions.assertEquals(Float.MAX_VALUE, number.floatValueExact());
		Assertions.assertEquals(Float.MAX_VALUE, number.doubleValue());
		Assertions.assertEquals(Float.MAX_VALUE, number.doubleValueExact());
		final BigDecimal bigDecimal = new BigDecimal(Float.toString(Float.MAX_VALUE));
		Assertions.assertEquals(bigDecimal.toBigInteger(), number.toBigInteger());
		Assertions.assertEquals(bigDecimal.toBigIntegerExact(), number.toBigIntegerExact());
		Assertions.assertEquals(bigDecimal, number.toBigDecimal());
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
		Assertions.assertEquals(0, number.intValue());
		Assertions.assertEquals(0, number.intValueExact());
		Assertions.assertEquals(0L, number.longValue());
		Assertions.assertEquals(0L, number.longValueExact());
		Assertions.assertEquals(0.0f, number.floatValue());
		Assertions.assertEquals(0.0f, number.floatValueExact());
		Assertions.assertEquals(0.0, number.doubleValue());
		Assertions.assertEquals(0.0, number.doubleValueExact());
		Assertions.assertEquals(BigInteger.valueOf(0), number.toBigInteger());
		Assertions.assertEquals(BigInteger.valueOf(0), number.toBigIntegerExact());
		Assertions.assertEquals(BigDecimal.valueOf(0.0), number.toBigDecimal());
	}

	@Test
	public void doubleMin() {
		final JsonNumber number = new JsonNumber(Double.MIN_VALUE);
		Assertions.assertFalse(number.isInteger());
		Assertions.assertTrue(number.isDecimal());
		Assertions.assertEquals(0, number.intValue());
		Assertions.assertThrows(ArithmeticException.class, number::intValueExact);
		Assertions.assertEquals(0L, number.longValue());
		Assertions.assertThrows(ArithmeticException.class, number::longValueExact);
		Assertions.assertEquals(0.0f, number.floatValue());
		Assertions.assertThrows(ArithmeticException.class, number::floatValueExact);
		Assertions.assertEquals(Double.MIN_VALUE, number.doubleValue());
		Assertions.assertEquals(Double.MIN_VALUE, number.doubleValueExact());
		Assertions.assertEquals(BigInteger.valueOf(0L), number.toBigInteger());
		Assertions.assertThrows(ArithmeticException.class, number::toBigIntegerExact);
		Assertions.assertEquals(new BigDecimal(Double.toString(Double.MIN_VALUE)), number.toBigDecimal());
	}

	@Test
	public void doubleMinNormal() {
		final JsonNumber number = new JsonNumber(Double.MIN_NORMAL);
		Assertions.assertFalse(number.isInteger());
		Assertions.assertTrue(number.isDecimal());
		Assertions.assertEquals(0, number.intValue());
		Assertions.assertThrows(ArithmeticException.class, number::intValueExact);
		Assertions.assertEquals(0L, number.longValue());
		Assertions.assertThrows(ArithmeticException.class, number::longValueExact);
		Assertions.assertEquals(0.0f, number.floatValue());
		Assertions.assertThrows(ArithmeticException.class, number::floatValueExact);
		Assertions.assertEquals(Double.MIN_NORMAL, number.doubleValue());
		Assertions.assertEquals(Double.MIN_NORMAL, number.doubleValueExact());
		Assertions.assertEquals(BigInteger.valueOf(0L), number.toBigInteger());
		Assertions.assertThrows(ArithmeticException.class, number::toBigIntegerExact);
		Assertions.assertEquals(new BigDecimal(Double.toString(Double.MIN_NORMAL)), number.toBigDecimal());
	}

	@Test
	public void doubleMax() {
		final JsonNumber number = new JsonNumber(Double.MAX_VALUE);
		Assertions.assertFalse(number.isInteger());
		Assertions.assertTrue(number.isDecimal());
		Assertions.assertEquals(Integer.MAX_VALUE, number.intValue());
		Assertions.assertThrows(ArithmeticException.class, number::intValueExact);
		Assertions.assertEquals(Long.MAX_VALUE, number.longValue());
		Assertions.assertThrows(ArithmeticException.class, number::longValueExact);
		Assertions.assertEquals(Float.POSITIVE_INFINITY, number.floatValue());
		Assertions.assertThrows(ArithmeticException.class, number::floatValueExact);
		Assertions.assertEquals(Double.MAX_VALUE, number.doubleValue());
		Assertions.assertEquals(Double.MAX_VALUE, number.doubleValueExact());
		final BigDecimal bigDecimal = new BigDecimal(Double.toString(Double.MAX_VALUE));
		Assertions.assertEquals(bigDecimal.toBigInteger(), number.toBigInteger());
		Assertions.assertEquals(bigDecimal.toBigIntegerExact(), number.toBigIntegerExact());
		Assertions.assertEquals(bigDecimal, number.toBigDecimal());
	}

	@Test
	public void doubleSpecial() {
		Assertions.assertThrows(NumberFormatException.class, () -> new JsonNumber(Double.NaN));
		Assertions.assertThrows(NumberFormatException.class, () -> new JsonNumber(Double.POSITIVE_INFINITY));
		Assertions.assertThrows(NumberFormatException.class, () -> new JsonNumber(Double.NEGATIVE_INFINITY));
	}
}
