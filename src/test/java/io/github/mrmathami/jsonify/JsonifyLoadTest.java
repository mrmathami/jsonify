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
import java.io.StringReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
 * Test jsonify load functionality.
 */
public class JsonifyLoadTest {
	@Test
	public void loadArrayEmpty() throws IOException, JsonException {
		final JsonElement element = Jsonify.load(new StringReader("[]"));
		Assertions.assertEquals(element.getClass(), JsonArray.class);
		Assertions.assertEquals(element, List.of());
	}

	@Test
	public void loadArrayNull() throws IOException, JsonException {
		final JsonElement element = Jsonify.load(new StringReader("[null]"));
		Assertions.assertEquals(element.getClass(), JsonArray.class);
		Assertions.assertEquals(element, List.of(JsonPrimitive.NULL));
	}

	@Test
	public void loadArrayFalse() throws IOException, JsonException {
		final JsonElement element = Jsonify.load(new StringReader("[false]"));
		Assertions.assertEquals(element.getClass(), JsonArray.class);
		Assertions.assertEquals(element, List.of(JsonPrimitive.FALSE));
	}

	@Test
	public void loadArrayTrue() throws IOException, JsonException {
		final JsonElement element = Jsonify.load(new StringReader("[true]"));
		Assertions.assertEquals(element.getClass(), JsonArray.class);
		Assertions.assertEquals(element, List.of(JsonPrimitive.TRUE));
	}

	@Test
	public void loadArrayString() throws IOException, JsonException {
		final JsonElement element = Jsonify.load(new StringReader("[\"\"]"));
		Assertions.assertEquals(element.getClass(), JsonArray.class);
		Assertions.assertEquals(element, List.of(JsonPrimitive.of("")));
	}

	@Test
	public void loadArrayNumber() throws IOException, JsonException {
		final JsonElement element = Jsonify.load(new StringReader("[0]"));
		Assertions.assertEquals(element.getClass(), JsonArray.class);
		Assertions.assertEquals(element, List.of(JsonPrimitive.of(0)));
	}

	@Test
	public void loadArrayArray() throws IOException, JsonException {
		final JsonElement element = Jsonify.load(new StringReader("[[]]"));
		Assertions.assertEquals(element.getClass(), JsonArray.class);
		Assertions.assertEquals(element, List.of(List.of()));
	}

	@Test
	public void loadArrayObject() throws IOException, JsonException {
		final JsonElement element = Jsonify.load(new StringReader("[{}]"));
		Assertions.assertEquals(element.getClass(), JsonArray.class);
		Assertions.assertEquals(element, List.of(Map.of()));
	}

	@Test
	public void loadArrayDouble() throws IOException, JsonException {
		final JsonElement element = Jsonify.load(new StringReader("[null,null]"));
		Assertions.assertEquals(element.getClass(), JsonArray.class);
		Assertions.assertEquals(element, List.of(JsonPrimitive.NULL, JsonPrimitive.NULL));
	}

	@Test
	public void loadArrayTriple() throws IOException, JsonException {
		final JsonElement element = Jsonify.load(new StringReader("[null,null,null]"));
		Assertions.assertEquals(element.getClass(), JsonArray.class);
		Assertions.assertEquals(element, List.of(JsonPrimitive.NULL, JsonPrimitive.NULL, JsonPrimitive.NULL));
	}

	// ==========

	@Test
	public void loadObjectEmpty() throws IOException, JsonException {
		final JsonElement element = Jsonify.load(new StringReader("{}"));
		Assertions.assertEquals(element.getClass(), JsonObject.class);
		Assertions.assertEquals(element, Map.of());
	}

	@Test
	public void loadObjectNull() throws IOException, JsonException {
		final JsonElement element = Jsonify.load(new StringReader("{\"\":null}"));
		Assertions.assertEquals(element.getClass(), JsonObject.class);
		Assertions.assertEquals(element, Map.of("", JsonPrimitive.NULL));
	}

	@Test
	public void loadObjectFalse() throws IOException, JsonException {
		final JsonElement element = Jsonify.load(new StringReader("{\"\":false}"));
		Assertions.assertEquals(element.getClass(), JsonObject.class);
		Assertions.assertEquals(element, Map.of("", JsonPrimitive.FALSE));
	}

	@Test
	public void loadObjectTrue() throws IOException, JsonException {
		final JsonElement element = Jsonify.load(new StringReader("{\"\":true}"));
		Assertions.assertEquals(element.getClass(), JsonObject.class);
		Assertions.assertEquals(element, Map.of("", JsonPrimitive.TRUE));
	}

	@Test
	public void loadObjectString() throws IOException, JsonException {
		final JsonElement element = Jsonify.load(new StringReader("{\"\":\"\"}"));
		Assertions.assertEquals(element.getClass(), JsonObject.class);
		Assertions.assertEquals(element, Map.of("", JsonPrimitive.of("")));
	}

	@Test
	public void loadObjectNumber() throws IOException, JsonException {
		final JsonElement element = Jsonify.load(new StringReader("{\"\":0}"));
		Assertions.assertEquals(element.getClass(), JsonObject.class);
		Assertions.assertEquals(element, Map.of("", JsonPrimitive.of(0)));
	}

	@Test
	public void loadObjectArray() throws IOException, JsonException {
		final JsonElement element = Jsonify.load(new StringReader("{\"\":[]}"));
		Assertions.assertEquals(element.getClass(), JsonObject.class);
		Assertions.assertEquals(element, Map.of("", List.of()));
	}

	@Test
	public void loadObjectObject() throws IOException, JsonException {
		final JsonElement element = Jsonify.load(new StringReader("{\"\":{}}"));
		Assertions.assertEquals(element.getClass(), JsonObject.class);
		Assertions.assertEquals(element, Map.of("", Map.of()));
	}

	@Test
	public void loadObjectDouble() throws IOException, JsonException {
		final JsonElement element = Jsonify.load(new StringReader("{\"\":null,\"2\":null}"));
		Assertions.assertEquals(element.getClass(), JsonObject.class);
		Assertions.assertEquals(element, Map.of("", JsonPrimitive.NULL, "2", JsonPrimitive.NULL));
	}

	@Test
	public void loadObjectTriple() throws IOException, JsonException {
		final JsonElement element = Jsonify.load(new StringReader("{\"\":null,\"2\":null,\"3\":null}"));
		Assertions.assertEquals(element.getClass(), JsonObject.class);
		Assertions.assertEquals(element, Map.of("", JsonPrimitive.NULL, "2", JsonPrimitive.NULL,
				"3", JsonPrimitive.NULL));
	}

	// ==========

	@Test
	public void loadNull() throws IOException, JsonException {
		final JsonElement element = Jsonify.load(new StringReader("null"));
		Assertions.assertEquals(element, JsonPrimitive.NULL);
	}

	@Test
	public void loadTrue() throws IOException, JsonException {
		final JsonElement element = Jsonify.load(new StringReader("true"));
		Assertions.assertEquals(element, JsonPrimitive.TRUE);
	}

	@Test
	public void loadFalse() throws IOException, JsonException {
		final JsonElement element = Jsonify.load(new StringReader("false"));
		Assertions.assertEquals(element, JsonPrimitive.FALSE);
	}

	// ==========

	@Test
	public void loadStringEmpty() throws IOException, JsonException {
		final JsonElement element = Jsonify.load(new StringReader("\"\""));
		Assertions.assertEquals(element, JsonPrimitive.of(""));
	}

	@Test
	public void loadStringNormal() throws IOException, JsonException {
		final JsonElement element = Jsonify.load(new StringReader(
				"\"ABCDEFGHIJKLMNOPQRSTUVWXYZ<>:{}abcdefghijklmnopqrstuvwxyz,.;'[]\\/`123456789-=~!@#$%^&*_+()" +
						"\\r\\b\\n\\t\\f\\\\К௪ၐᎺអὲ⍚❂⼒ぐ㋺ꁐꁚꑂ\\u4e2d\""));
		Assertions.assertEquals(element, JsonPrimitive.of(
				"ABCDEFGHIJKLMNOPQRSTUVWXYZ<>:{}abcdefghijklmnopqrstuvwxyz,.;'[]/`123456789-=~!@#$%^&*_+()" +
						"\r\b\n\t\f\\К௪ၐᎺអὲ⍚❂⼒ぐ㋺ꁐꁚꑂ\u4e2d"));
	}

	// ==========

	@Test
	public void loadNumberIntegerSmall() throws IOException, JsonException {
		final JsonElement element = Jsonify.load(new StringReader("0"));
		Assertions.assertEquals(element, JsonPrimitive.of(0));
	}

	@Test
	public void loadNumberIntegerBig() throws IOException, JsonException {
		final JsonElement element = Jsonify.load(new StringReader("12345678901234567890"));
		Assertions.assertEquals(element, JsonPrimitive.of(new BigInteger("12345678901234567890")));
	}

	@Test
	public void loadNumberDecimalFractionSmall() throws IOException, JsonException {
		final JsonElement element = Jsonify.load(new StringReader("0.1"));
		Assertions.assertEquals(element, JsonPrimitive.of(0.1));
	}

	@Test
	public void loadNumberDecimalFractionBig() throws IOException, JsonException {
		final JsonElement element = Jsonify.load(new StringReader("0.12345678901234567890"));
		Assertions.assertEquals(element, JsonPrimitive.of(new BigDecimal("0.12345678901234567890")));
	}

	@Test
	public void loadNumberDecimalFractionTrailingZero() throws IOException, JsonException {
		final JsonElement element = Jsonify.load(new StringReader("0.1000"));
		Assertions.assertEquals(element, JsonPrimitive.of(new BigDecimal("0.1000")));
	}

	@Test
	public void loadNumberDecimalFractionExponent() throws IOException, JsonException {
		final JsonElement element = Jsonify.load(new StringReader("1.0e9"));
		Assertions.assertEquals(element, JsonPrimitive.of(new BigDecimal("1.0E+9")));
	}

	@Test
	public void loadNumberDecimalFractionTrailingZeroExponent() throws IOException, JsonException {
		final JsonElement element = Jsonify.load(new StringReader("1.0000e9"));
		Assertions.assertEquals(element, JsonPrimitive.of(new BigDecimal("1.0000E+9")));
	}

	@Test
	public void loadNumberDecimalExponent() throws IOException, JsonException {
		final JsonElement element = Jsonify.load(new StringReader("1e9"));
		Assertions.assertEquals(element, JsonPrimitive.of(new BigDecimal("1E+9")));
	}
}
