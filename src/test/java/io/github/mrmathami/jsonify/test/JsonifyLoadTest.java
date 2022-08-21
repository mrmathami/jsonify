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

import io.github.mrmathami.jsonify.JsonArray;
import io.github.mrmathami.jsonify.JsonElement;
import io.github.mrmathami.jsonify.JsonException;
import io.github.mrmathami.jsonify.JsonNumber;
import io.github.mrmathami.jsonify.JsonObject;
import io.github.mrmathami.jsonify.JsonPrimitive;
import io.github.mrmathami.jsonify.JsonString;
import io.github.mrmathami.jsonify.Jsonify;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
 * Test jsonify load functionality.
 */
public class JsonifyLoadTest {
	private static final String WHITESPACES = " \n\r\t\r\n";

	@Test
	public void loadArrayEmpty() throws IOException {
		try {
			final JsonElement element = Jsonify.load(new StringReader("[]"));
			Assertions.assertEquals(JsonArray.class, element.getClass());
			Assertions.assertEquals(List.of(), element);
		} catch (final JsonException e) {
			Assertions.fail(e);
		}
	}

	@Test
	public void loadArrayNull() throws IOException {
		try {
			final JsonElement element = Jsonify.load(new StringReader("[null]"));
			Assertions.assertEquals(JsonArray.class, element.getClass());
			Assertions.assertEquals(List.of(JsonPrimitive.NULL), element);
		} catch (final JsonException e) {
			Assertions.fail(e);
		}
	}

	@Test
	public void loadArrayFalse() throws IOException {
		try {
			final JsonElement element = Jsonify.load(new StringReader("[false]"));
			Assertions.assertEquals(JsonArray.class, element.getClass());
			Assertions.assertEquals(List.of(JsonPrimitive.FALSE), element);
		} catch (final JsonException e) {
			Assertions.fail(e);
		}
	}

	@Test
	public void loadArrayTrue() throws IOException {
		try {
			final JsonElement element = Jsonify.load(new StringReader("[true]"));
			Assertions.assertEquals(JsonArray.class, element.getClass());
			Assertions.assertEquals(List.of(JsonPrimitive.TRUE), element);
		} catch (final JsonException e) {
			Assertions.fail(e);
		}
	}

	@Test
	public void loadArrayString() throws IOException {
		try {
			final JsonElement element = Jsonify.load(new StringReader("[\"\"]"));
			Assertions.assertEquals(JsonArray.class, element.getClass());
			Assertions.assertEquals(List.of(new JsonString("")), element);
		} catch (final JsonException e) {
			Assertions.fail(e);
		}
	}

	@Test
	public void loadArrayNumber() throws IOException {
		try {
			final JsonElement element = Jsonify.load(new StringReader("[0]"));
			Assertions.assertEquals(JsonArray.class, element.getClass());
			Assertions.assertEquals(List.of(new JsonNumber(0)), element);
		} catch (final JsonException e) {
			Assertions.fail(e);
		}
	}

	@Test
	public void loadArrayArray() throws IOException {
		try {
			final JsonElement element = Jsonify.load(new StringReader("[[]]"));
			Assertions.assertEquals(JsonArray.class, element.getClass());
			Assertions.assertEquals(List.of(List.of()), element);
		} catch (final JsonException e) {
			Assertions.fail(e);
		}
	}

	@Test
	public void loadArrayObject() throws IOException {
		try {
			final JsonElement element = Jsonify.load(new StringReader("[{}]"));
			Assertions.assertEquals(JsonArray.class, element.getClass());
			Assertions.assertEquals(List.of(Map.of()), element);
		} catch (final JsonException e) {
			Assertions.fail(e);
		}
	}

	@Test
	public void loadArrayDouble() throws IOException {
		try {
			final JsonElement element = Jsonify.load(new StringReader("[null,null]"));
			Assertions.assertEquals(JsonArray.class, element.getClass());
			Assertions.assertEquals(List.of(JsonPrimitive.NULL, JsonPrimitive.NULL), element);
		} catch (final JsonException e) {
			Assertions.fail(e);
		}
	}

	@Test
	public void loadArrayTriple() throws IOException {
		try {
			final JsonElement element = Jsonify.load(new StringReader("[null,null,null]"));
			Assertions.assertEquals(JsonArray.class, element.getClass());
			Assertions.assertEquals(List.of(JsonPrimitive.NULL, JsonPrimitive.NULL, JsonPrimitive.NULL), element);
		} catch (final JsonException e) {
			Assertions.fail(e);
		}
	}

	@Test
	public void loadArrayWhitespace() throws IOException {
		try {
			final String input = String.join(WHITESPACES, "", "[", "null", ",", "null", ",", "null", "]", "");
			final JsonElement element = Jsonify.load(new StringReader(input));
			Assertions.assertEquals(JsonArray.class, element.getClass());
			Assertions.assertEquals(List.of(JsonPrimitive.NULL, JsonPrimitive.NULL, JsonPrimitive.NULL), element);
		} catch (final JsonException e) {
			Assertions.fail(e);
		}
	}

	// ==========

	@Test
	public void throwArrayNoClose() {
		Assertions.assertThrows(JsonException.class, () -> Jsonify.load(new StringReader("[")));
	}

	@Test
	public void throwArrayNoOpen() {
		Assertions.assertThrows(JsonException.class, () -> Jsonify.load(new StringReader("]")));
	}

	@Test
	public void throwArrayExtraAfter() {
		Assertions.assertThrows(JsonException.class, () -> Jsonify.load(new StringReader("[],")));
	}

	@Test
	public void throwArrayExtraEmptyInside() {
		Assertions.assertThrows(JsonException.class, () -> Jsonify.load(new StringReader("[,]")));
	}

	@Test
	public void throwArrayExtraSingleInside() {
		Assertions.assertThrows(JsonException.class, () -> Jsonify.load(new StringReader("[null,]")));
	}

	// ====================

	@Test
	public void loadObjectEmpty() throws IOException {
		try {
			final JsonElement element = Jsonify.load(new StringReader("{}"));
			Assertions.assertEquals(JsonObject.class, element.getClass());
			Assertions.assertEquals(Map.of(), element);
		} catch (final JsonException e) {
			Assertions.fail(e);
		}
	}

	@Test
	public void loadObjectNull() throws IOException {
		try {
			final JsonElement element = Jsonify.load(new StringReader("{\"\":null}"));
			Assertions.assertEquals(JsonObject.class, element.getClass());
			Assertions.assertEquals(Map.of("", JsonPrimitive.NULL), element);
		} catch (final JsonException e) {
			Assertions.fail(e);
		}
	}

	@Test
	public void loadObjectFalse() throws IOException {
		try {
			final JsonElement element = Jsonify.load(new StringReader("{\"\":false}"));
			Assertions.assertEquals(JsonObject.class, element.getClass());
			Assertions.assertEquals(Map.of("", JsonPrimitive.FALSE), element);
		} catch (final JsonException e) {
			Assertions.fail(e);
		}
	}

	@Test
	public void loadObjectTrue() throws IOException {
		try {
			final JsonElement element = Jsonify.load(new StringReader("{\"\":true}"));
			Assertions.assertEquals(JsonObject.class, element.getClass());
			Assertions.assertEquals(Map.of("", JsonPrimitive.TRUE), element);
		} catch (final JsonException e) {
			Assertions.fail(e);
		}
	}

	@Test
	public void loadObjectString() throws IOException {
		try {
			final JsonElement element = Jsonify.load(new StringReader("{\"\":\"\"}"));
			Assertions.assertEquals(JsonObject.class, element.getClass());
			Assertions.assertEquals(Map.of("", new JsonString("")), element);
		} catch (final JsonException e) {
			Assertions.fail(e);
		}
	}

	@Test
	public void loadObjectNumber() throws IOException {
		try {
			final JsonElement element = Jsonify.load(new StringReader("{\"\":0}"));
			Assertions.assertEquals(JsonObject.class, element.getClass());
			Assertions.assertEquals(Map.of("", new JsonNumber(0)), element);
		} catch (final JsonException e) {
			Assertions.fail(e);
		}
	}

	@Test
	public void loadObjectArray() throws IOException {
		try {
			final JsonElement element = Jsonify.load(new StringReader("{\"\":[]}"));
			Assertions.assertEquals(JsonObject.class, element.getClass());
			Assertions.assertEquals(Map.of("", List.of()), element);
		} catch (final JsonException e) {
			Assertions.fail(e);
		}
	}

	@Test
	public void loadObjectObject() throws IOException {
		try {
			final JsonElement element = Jsonify.load(new StringReader("{\"\":{}}"));
			Assertions.assertEquals(JsonObject.class, element.getClass());
			Assertions.assertEquals(Map.of("", Map.of()), element);
		} catch (final JsonException e) {
			Assertions.fail(e);
		}
	}

	@Test
	public void loadObjectDouble() throws IOException {
		try {
			final JsonElement element = Jsonify.load(new StringReader("{\"\":null,\"2\":null}"));
			Assertions.assertEquals(JsonObject.class, element.getClass());
			Assertions.assertEquals(Map.of("", JsonPrimitive.NULL, "2", JsonPrimitive.NULL), element);
		} catch (final JsonException e) {
			Assertions.fail(e);
		}
	}

	@Test
	public void loadObjectTriple() throws IOException {
		try {
			final JsonElement element = Jsonify.load(new StringReader("{\"\":null,\"2\":null,\"3\":null}"));
			Assertions.assertEquals(JsonObject.class, element.getClass());
			Assertions.assertEquals(Map.of("", JsonPrimitive.NULL, "2", JsonPrimitive.NULL,
					"3", JsonPrimitive.NULL), element);
		} catch (final JsonException e) {
			Assertions.fail(e);
		}
	}

	@Test
	public void loadObjectWhitespace() throws IOException {
		try {
			final String input = String.join(WHITESPACES,
					"", "{", "\"\"", ":", "null", ",", "\"2\"", ":", "null", ",", "\"3\"", ":", "null", "}", "");
			final JsonElement element = Jsonify.load(new StringReader(input));
			Assertions.assertEquals(JsonObject.class, element.getClass());
			Assertions.assertEquals(Map.of("", JsonPrimitive.NULL, "2", JsonPrimitive.NULL,
					"3", JsonPrimitive.NULL), element);
		} catch (final JsonException e) {
			Assertions.fail(e);
		}
	}

	// ====================

	@Test
	public void throwObjectNoClose() {
		Assertions.assertThrows(JsonException.class, () -> Jsonify.load(new StringReader("{")));
	}

	@Test
	public void throwObjectNoOpen() {
		Assertions.assertThrows(JsonException.class, () -> Jsonify.load(new StringReader("}")));
	}

	@Test
	public void throwObjectExtraAfter() {
		Assertions.assertThrows(JsonException.class, () -> Jsonify.load(new StringReader("{},")));
	}

	@Test
	public void throwObjectExtraInsideEmpty() {
		Assertions.assertThrows(JsonException.class, () -> Jsonify.load(new StringReader("{,}")));
	}

	@Test
	public void throwObjectExtraInsideSingle() {
		Assertions.assertThrows(JsonException.class, () -> Jsonify.load(new StringReader("{\"\":null,}")));
	}

	@Test
	public void throwObjectIncompleteNoColon() {
		Assertions.assertThrows(JsonException.class, () -> Jsonify.load(new StringReader("{\"\"")));
	}

	@Test
	public void throwObjectIncompleteNoValue() {
		Assertions.assertThrows(JsonException.class, () -> Jsonify.load(new StringReader("{\"\":")));
	}

	@Test
	public void throwObjectIncompleteNoComma() {
		Assertions.assertThrows(JsonException.class, () -> Jsonify.load(new StringReader("{\"\":null")));
	}

	@Test
	public void throwObjectIncompleteNoClose() {
		Assertions.assertThrows(JsonException.class, () -> Jsonify.load(new StringReader("{\"\":null,")));
	}

	// ====================

	@Test
	public void loadNull() throws IOException {
		try {
			final JsonElement element = Jsonify.load(new StringReader("null"));
			Assertions.assertEquals(JsonPrimitive.NULL, element);
		} catch (final JsonException e) {
			Assertions.fail(e);
		}
	}

	@Test
	public void loadTrue() throws IOException {
		try {
			final JsonElement element = Jsonify.load(new StringReader("true"));
			Assertions.assertEquals(JsonPrimitive.TRUE, element);
		} catch (final JsonException e) {
			Assertions.fail(e);
		}
	}

	@Test
	public void loadFalse() throws IOException {
		try {
			final JsonElement element = Jsonify.load(new StringReader("false"));
			Assertions.assertEquals(JsonPrimitive.FALSE, element);
		} catch (final JsonException e) {
			Assertions.fail(e);
		}
	}

	// ====================

	@Test
	public void throwNullExtra() {
		Assertions.assertThrows(JsonException.class, () -> Jsonify.load(new StringReader("null,")));
	}

	@Test
	public void throwTrueExtra() {
		Assertions.assertThrows(JsonException.class, () -> Jsonify.load(new StringReader("true,")));
	}

	@Test
	public void throwFalseExtra() {
		Assertions.assertThrows(JsonException.class, () -> Jsonify.load(new StringReader("false,")));
	}

	// ====================

	@Test
	public void loadStringEmpty() throws IOException {
		try {
			final JsonElement element = Jsonify.load(new StringReader("\"\""));
			Assertions.assertEquals(new JsonString(""), element);
		} catch (final JsonException e) {
			Assertions.fail(e);
		}
	}

	@Test
	public void loadStringNormal() throws IOException {
		try {
			final JsonElement element = Jsonify.load(new StringReader(
					"\"ABCDEFGHIJKLMNOPQRSTUVWXYZ<>:{}abcdefghijklmnopqrstuvwxyz,.;'[]\\/`123456789-=~!@#$%^&*_+()" +
							"\\r\\b\\n\\t\\f\\\\\u041A\u0BEA\u1050\u13BA\u17A2\u1F72\u235A\u2742\u2F12\u3050\u32FA\uA050\uA05A\uA442\\u4e2d\""));
			Assertions.assertEquals(new JsonString(
					"ABCDEFGHIJKLMNOPQRSTUVWXYZ<>:{}abcdefghijklmnopqrstuvwxyz,.;'[]/`123456789-=~!@#$%^&*_+()" +
							"\r\b\n\t\f\\\u041A\u0BEA\u1050\u13BA\u17A2\u1F72\u235A\u2742\u2F12\u3050\u32FA\uA050\uA05A\uA442\u4e2d"), element);
		} catch (final JsonException e) {
			Assertions.fail(e);
		}
	}

	// ====================

	@Test
	public void throwStringExtra() {
		Assertions.assertThrows(JsonException.class, () -> Jsonify.load(new StringReader("\"\",")));
	}

	// ====================

	@Test
	public void loadNumberIntegerSmall() throws IOException {
		try {
			final JsonElement element = Jsonify.load(new StringReader("0"));
			Assertions.assertEquals(new JsonNumber(0), element);
		} catch (final JsonException e) {
			Assertions.fail(e);
		}
	}

	@Test
	public void loadNumberIntegerBig() throws IOException {
		try {
			final JsonElement element = Jsonify.load(new StringReader("123456789012345678901234567890"));
			Assertions.assertEquals(new JsonNumber(new BigInteger("123456789012345678901234567890")), element);
		} catch (final JsonException e) {
			Assertions.fail(e);
		}
	}

	@Test
	public void loadNumberIntegerNegative() throws IOException {
		try {
			final JsonElement element = Jsonify.load(new StringReader("-123456789012345678901234567890"));
			Assertions.assertEquals(new JsonNumber(new BigInteger("-123456789012345678901234567890")), element);
		} catch (final JsonException e) {
			Assertions.fail(e);
		}
	}

	@Test
	public void loadNumberDecimalFractionSmall() throws IOException {
		try {
			final JsonElement element = Jsonify.load(new StringReader("0.12345678"));
			Assertions.assertEquals(new JsonNumber(0.12345678), element);
		} catch (final JsonException e) {
			Assertions.fail(e);
		}
	}

	@Test
	public void loadNumberDecimalFractionMedium() throws IOException {
		try {
			final JsonElement element = Jsonify.load(new StringReader("0.12345678912345678"));
			Assertions.assertEquals(new JsonNumber(0.12345678912345678), element);
		} catch (final JsonException e) {
			Assertions.fail(e);
		}
	}

	@Test
	public void loadNumberDecimalFractionBig() throws IOException {
		try {
			final JsonElement element = Jsonify.load(new StringReader("0.123456789012345678901234567890"));
			Assertions.assertEquals(new JsonNumber(new BigDecimal("0.123456789012345678901234567890")), element);
		} catch (final JsonException e) {
			Assertions.fail(e);
		}
	}

	@Test
	public void loadNumberDecimalFractionNegative() throws IOException {
		try {
			final JsonElement element = Jsonify.load(new StringReader("-0.123456789012345678901234567890"));
			Assertions.assertEquals(new JsonNumber(new BigDecimal("-0.123456789012345678901234567890")), element);
		} catch (final JsonException e) {
			Assertions.fail(e);
		}
	}

	@Test
	public void loadNumberDecimalFractionTrailingZero() throws IOException {
		try {
			final JsonElement element = Jsonify.load(new StringReader("0.1000"));
			Assertions.assertEquals(new JsonNumber(new BigDecimal("0.1000")), element);
		} catch (final JsonException e) {
			Assertions.fail(e);
		}
	}

	@Test
	public void loadNumberDecimalFractionExponent() throws IOException {
		try {
			final JsonElement element = Jsonify.load(new StringReader("1.0e9"));
			Assertions.assertEquals(new JsonNumber(new BigDecimal("1.0E+9")), element);
		} catch (final JsonException e) {
			Assertions.fail(e);
		}
	}

	@Test
	public void loadNumberDecimalFractionTrailingZeroExponent() throws IOException {
		try {
			final JsonElement element = Jsonify.load(new StringReader("1.0000e9"));
			Assertions.assertEquals(new JsonNumber(new BigDecimal("1.0000E+9")), element);
		} catch (final JsonException e) {
			Assertions.fail(e);
		}
	}

	@Test
	public void loadNumberDecimalExponent() throws IOException {
		try {
			final JsonElement element = Jsonify.load(new StringReader("1e9"));
			Assertions.assertEquals(new JsonNumber(new BigDecimal("1E+9")), element);
		} catch (final JsonException e) {
			Assertions.fail(e);
		}
	}

	// ====================

	@Test
	public void throwNumberExtra() {
		Assertions.assertThrows(JsonException.class, () -> Jsonify.load(new StringReader("0,")));
	}
}
