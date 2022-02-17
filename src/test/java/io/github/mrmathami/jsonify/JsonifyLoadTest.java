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

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
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
			Assertions.assertEquals(element.getClass(), JsonArray.class);
			Assertions.assertEquals(element, List.of());
		} catch (final JsonException e) {
			Assertions.fail(e);
		}
	}

	@Test
	public void loadArrayNull() throws IOException {
		try {
			final JsonElement element = Jsonify.load(new StringReader("[null]"));
			Assertions.assertEquals(element.getClass(), JsonArray.class);
			Assertions.assertEquals(element, List.of(JsonPrimitive.NULL));
		} catch (final JsonException e) {
			Assertions.fail(e);
		}
	}

	@Test
	public void loadArrayFalse() throws IOException {
		try {
			final JsonElement element = Jsonify.load(new StringReader("[false]"));
			Assertions.assertEquals(element.getClass(), JsonArray.class);
			Assertions.assertEquals(element, List.of(JsonPrimitive.FALSE));
		} catch (final JsonException e) {
			Assertions.fail(e);
		}
	}

	@Test
	public void loadArrayTrue() throws IOException {
		try {
			final JsonElement element = Jsonify.load(new StringReader("[true]"));
			Assertions.assertEquals(element.getClass(), JsonArray.class);
			Assertions.assertEquals(element, List.of(JsonPrimitive.TRUE));
		} catch (final JsonException e) {
			Assertions.fail(e);
		}
	}

	@Test
	public void loadArrayString() throws IOException {
		try {
			final JsonElement element = Jsonify.load(new StringReader("[\"\"]"));
			Assertions.assertEquals(element.getClass(), JsonArray.class);
			Assertions.assertEquals(element, List.of(JsonPrimitive.of("")));
		} catch (final JsonException e) {
			Assertions.fail(e);
		}
	}

	@Test
	public void loadArrayNumber() throws IOException {
		try {
			final JsonElement element = Jsonify.load(new StringReader("[0]"));
			Assertions.assertEquals(element.getClass(), JsonArray.class);
			Assertions.assertEquals(element, List.of(JsonPrimitive.of(0)));
		} catch (final JsonException e) {
			Assertions.fail(e);
		}
	}

	@Test
	public void loadArrayArray() throws IOException {
		try {
			final JsonElement element = Jsonify.load(new StringReader("[[]]"));
			Assertions.assertEquals(element.getClass(), JsonArray.class);
			Assertions.assertEquals(element, List.of(List.of()));
		} catch (final JsonException e) {
			Assertions.fail(e);
		}
	}

	@Test
	public void loadArrayObject() throws IOException {
		try {
			final JsonElement element = Jsonify.load(new StringReader("[{}]"));
			Assertions.assertEquals(element.getClass(), JsonArray.class);
			Assertions.assertEquals(element, List.of(Map.of()));
		} catch (final JsonException e) {
			Assertions.fail(e);
		}
	}

	@Test
	public void loadArrayDouble() throws IOException {
		try {
			final JsonElement element = Jsonify.load(new StringReader("[null,null]"));
			Assertions.assertEquals(element.getClass(), JsonArray.class);
			Assertions.assertEquals(element, List.of(JsonPrimitive.NULL, JsonPrimitive.NULL));
		} catch (final JsonException e) {
			Assertions.fail(e);
		}
	}

	@Test
	public void loadArrayTriple() throws IOException {
		try {
			final JsonElement element = Jsonify.load(new StringReader("[null,null,null]"));
			Assertions.assertEquals(element.getClass(), JsonArray.class);
			Assertions.assertEquals(element, List.of(JsonPrimitive.NULL, JsonPrimitive.NULL, JsonPrimitive.NULL));
		} catch (final JsonException e) {
			Assertions.fail(e);
		}
	}

	@Test
	public void loadArrayWhitespace() throws IOException {
		try {
			final String input = String.join(WHITESPACES, "", "[", "null", ",", "null", ",", "null", "]", "");
			final JsonElement element = Jsonify.load(new StringReader(input));
			Assertions.assertEquals(element.getClass(), JsonArray.class);
			Assertions.assertEquals(element, List.of(JsonPrimitive.NULL, JsonPrimitive.NULL, JsonPrimitive.NULL));
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
			Assertions.assertEquals(element.getClass(), JsonObject.class);
			Assertions.assertEquals(element, Map.of());
		} catch (final JsonException e) {
			Assertions.fail(e);
		}
	}

	@Test
	public void loadObjectNull() throws IOException {
		try {
			final JsonElement element = Jsonify.load(new StringReader("{\"\":null}"));
			Assertions.assertEquals(element.getClass(), JsonObject.class);
			Assertions.assertEquals(element, Map.of("", JsonPrimitive.NULL));
		} catch (final JsonException e) {
			Assertions.fail(e);
		}
	}

	@Test
	public void loadObjectFalse() throws IOException {
		try {
			final JsonElement element = Jsonify.load(new StringReader("{\"\":false}"));
			Assertions.assertEquals(element.getClass(), JsonObject.class);
			Assertions.assertEquals(element, Map.of("", JsonPrimitive.FALSE));
		} catch (final JsonException e) {
			Assertions.fail(e);
		}
	}

	@Test
	public void loadObjectTrue() throws IOException {
		try {
			final JsonElement element = Jsonify.load(new StringReader("{\"\":true}"));
			Assertions.assertEquals(element.getClass(), JsonObject.class);
			Assertions.assertEquals(element, Map.of("", JsonPrimitive.TRUE));
		} catch (final JsonException e) {
			Assertions.fail(e);
		}
	}

	@Test
	public void loadObjectString() throws IOException {
		try {
			final JsonElement element = Jsonify.load(new StringReader("{\"\":\"\"}"));
			Assertions.assertEquals(element.getClass(), JsonObject.class);
			Assertions.assertEquals(element, Map.of("", JsonPrimitive.of("")));
		} catch (final JsonException e) {
			Assertions.fail(e);
		}
	}

	@Test
	public void loadObjectNumber() throws IOException {
		try {
			final JsonElement element = Jsonify.load(new StringReader("{\"\":0}"));
			Assertions.assertEquals(element.getClass(), JsonObject.class);
			Assertions.assertEquals(element, Map.of("", JsonPrimitive.of(0)));
		} catch (final JsonException e) {
			Assertions.fail(e);
		}
	}

	@Test
	public void loadObjectArray() throws IOException {
		try {
			final JsonElement element = Jsonify.load(new StringReader("{\"\":[]}"));
			Assertions.assertEquals(element.getClass(), JsonObject.class);
			Assertions.assertEquals(element, Map.of("", List.of()));
		} catch (final JsonException e) {
			Assertions.fail(e);
		}
	}

	@Test
	public void loadObjectObject() throws IOException {
		try {
			final JsonElement element = Jsonify.load(new StringReader("{\"\":{}}"));
			Assertions.assertEquals(element.getClass(), JsonObject.class);
			Assertions.assertEquals(element, Map.of("", Map.of()));
		} catch (final JsonException e) {
			Assertions.fail(e);
		}
	}

	@Test
	public void loadObjectDouble() throws IOException {
		try {
			final JsonElement element = Jsonify.load(new StringReader("{\"\":null,\"2\":null}"));
			Assertions.assertEquals(element.getClass(), JsonObject.class);
			Assertions.assertEquals(element, Map.of("", JsonPrimitive.NULL, "2", JsonPrimitive.NULL));
		} catch (final JsonException e) {
			Assertions.fail(e);
		}
	}

	@Test
	public void loadObjectTriple() throws IOException {
		try {
			final JsonElement element = Jsonify.load(new StringReader("{\"\":null,\"2\":null,\"3\":null}"));
			Assertions.assertEquals(element.getClass(), JsonObject.class);
			Assertions.assertEquals(element, Map.of("", JsonPrimitive.NULL, "2", JsonPrimitive.NULL,
					"3", JsonPrimitive.NULL));
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
			Assertions.assertEquals(element.getClass(), JsonObject.class);
			Assertions.assertEquals(element, Map.of("", JsonPrimitive.NULL, "2", JsonPrimitive.NULL,
					"3", JsonPrimitive.NULL));
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

	// ====================

	@Test
	public void loadNull() throws IOException {
		try {
			final JsonElement element = Jsonify.load(new StringReader("null"));
			Assertions.assertEquals(element, JsonPrimitive.NULL);
		} catch (final JsonException e) {
			Assertions.fail(e);
		}
	}

	@Test
	public void loadTrue() throws IOException {
		try {
			final JsonElement element = Jsonify.load(new StringReader("true"));
			Assertions.assertEquals(element, JsonPrimitive.TRUE);
		} catch (final JsonException e) {
			Assertions.fail(e);
		}
	}

	@Test
	public void loadFalse() throws IOException {
		try {
			final JsonElement element = Jsonify.load(new StringReader("false"));
			Assertions.assertEquals(element, JsonPrimitive.FALSE);
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
			Assertions.assertEquals(element, JsonPrimitive.of(""));
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
			Assertions.assertEquals(element, JsonPrimitive.of(
					"ABCDEFGHIJKLMNOPQRSTUVWXYZ<>:{}abcdefghijklmnopqrstuvwxyz,.;'[]/`123456789-=~!@#$%^&*_+()" +
							"\r\b\n\t\f\\\u041A\u0BEA\u1050\u13BA\u17A2\u1F72\u235A\u2742\u2F12\u3050\u32FA\uA050\uA05A\uA442\u4e2d"));
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
			Assertions.assertEquals(element, JsonPrimitive.of(0));
		} catch (final JsonException e) {
			Assertions.fail(e);
		}
	}

	@Test
	public void loadNumberIntegerBig() throws IOException {
		try {
			final JsonElement element = Jsonify.load(new StringReader("12345678901234567890"));
			Assertions.assertEquals(element, JsonPrimitive.of(new BigInteger("12345678901234567890")));
		} catch (final JsonException e) {
			Assertions.fail(e);
		}
	}

	@Test
	public void loadNumberDecimalFractionSmall() throws IOException {
		try {
			final JsonElement element = Jsonify.load(new StringReader("0.1"));
			Assertions.assertEquals(element, JsonPrimitive.of(0.1));
		} catch (final JsonException e) {
			Assertions.fail(e);
		}
	}

	@Test
	public void loadNumberDecimalFractionBig() throws IOException {
		try {
			final JsonElement element = Jsonify.load(new StringReader("0.12345678901234567890"));
			Assertions.assertEquals(element, JsonPrimitive.of(new BigDecimal("0.12345678901234567890")));
		} catch (final JsonException e) {
			Assertions.fail(e);
		}
	}

	@Test
	public void loadNumberDecimalFractionTrailingZero() throws IOException {
		try {
			final JsonElement element = Jsonify.load(new StringReader("0.1000"));
			Assertions.assertEquals(element, JsonPrimitive.of(new BigDecimal("0.1000")));
		} catch (final JsonException e) {
			Assertions.fail(e);
		}
	}

	@Test
	public void loadNumberDecimalFractionExponent() throws IOException {
		try {
			final JsonElement element = Jsonify.load(new StringReader("1.0e9"));
			Assertions.assertEquals(element, JsonPrimitive.of(new BigDecimal("1.0E+9")));
		} catch (final JsonException e) {
			Assertions.fail(e);
		}
	}

	@Test
	public void loadNumberDecimalFractionTrailingZeroExponent() throws IOException {
		try {
			final JsonElement element = Jsonify.load(new StringReader("1.0000e9"));
			Assertions.assertEquals(element, JsonPrimitive.of(new BigDecimal("1.0000E+9")));
		} catch (final JsonException e) {
			Assertions.fail(e);
		}
	}

	@Test
	public void loadNumberDecimalExponent() throws IOException {
		try {
			final JsonElement element = Jsonify.load(new StringReader("1e9"));
			Assertions.assertEquals(element, JsonPrimitive.of(new BigDecimal("1E+9")));
		} catch (final JsonException e) {
			Assertions.fail(e);
		}
	}

	// ====================

	@Test
	public void throwNumberExtra() {
		Assertions.assertThrows(JsonException.class, () -> Jsonify.load(new StringReader("0,")));
	}

	// ====================

	@Test
	public void loadBigInput() throws IOException {
		try (InputStream inputStream = JsonifyLoadTest.class.getResourceAsStream("testdata/large-file.json")) {
			if (inputStream == null) Assertions.fail("Failed loading test resource!");
			try (final Reader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8)) {
				final JsonElement element = Jsonify.load(reader);
				Assertions.assertInstanceOf(JsonArray.class, element);
				Assertions.assertEquals(((JsonArray) element).size(), 11351);
			} catch (final JsonException e) {
				Assertions.fail(e);
			}
		}
	}
}
