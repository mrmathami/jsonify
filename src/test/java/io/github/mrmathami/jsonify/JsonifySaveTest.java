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
import java.io.StringWriter;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;

public class JsonifySaveTest {
	@Test
	public void saveArrayEmpty() throws IOException {
		try {
			final StringWriter writer = new StringWriter();
			final JsonArray element = new JsonArray();
			Jsonify.save(writer, element);
			Assertions.assertEquals(writer.toString(), "[]");
		} catch (final JsonException e) {
			Assertions.fail(e);
		}
	}

	@Test
	public void saveArrayNull() throws IOException {
		try {
			final StringWriter writer = new StringWriter();
			final JsonArray element = new JsonArray();
			element.add(JsonPrimitive.NULL);
			Jsonify.save(writer, element);
			Assertions.assertEquals(writer.toString(), "[null]");
		} catch (final JsonException e) {
			Assertions.fail(e);
		}
	}

	@Test
	public void saveArrayFalse() throws IOException {
		try {
			final StringWriter writer = new StringWriter();
			final JsonArray element = new JsonArray();
			element.add(JsonPrimitive.FALSE);
			Jsonify.save(writer, element);
			Assertions.assertEquals(writer.toString(), "[false]");
		} catch (final JsonException e) {
			Assertions.fail(e);
		}
	}

	@Test
	public void saveArrayTrue() throws IOException {
		try {
			final StringWriter writer = new StringWriter();
			final JsonArray element = new JsonArray();
			element.add(JsonPrimitive.TRUE);
			Jsonify.save(writer, element);
			Assertions.assertEquals(writer.toString(), "[true]");
		} catch (final JsonException e) {
			Assertions.fail(e);
		}
	}

	@Test
	public void saveArrayString() throws IOException {
		try {
			final StringWriter writer = new StringWriter();
			final JsonArray element = new JsonArray();
			element.add(new JsonString(""));
			Jsonify.save(writer, element);
			Assertions.assertEquals(writer.toString(), "[\"\"]");
		} catch (final JsonException e) {
			Assertions.fail(e);
		}
	}

	@Test
	public void saveArrayNumber() throws IOException {
		try {
			final StringWriter writer = new StringWriter();
			final JsonArray element = new JsonArray();
			element.add(new JsonNumber(0));
			Jsonify.save(writer, element);
			Assertions.assertEquals(writer.toString(), "[0]");
		} catch (final JsonException e) {
			Assertions.fail(e);
		}
	}

	@Test
	public void saveArrayArray() throws IOException {
		try {
			final StringWriter writer = new StringWriter();
			final JsonArray element = new JsonArray();
			element.add(new JsonArray());
			Jsonify.save(writer, element);
			Assertions.assertEquals(writer.toString(), "[[]]");
		} catch (final JsonException e) {
			Assertions.fail(e);
		}
	}

	@Test
	public void saveArrayObject() throws IOException {
		try {
			final StringWriter writer = new StringWriter();
			final JsonArray element = new JsonArray();
			element.add(new JsonObject());
			Jsonify.save(writer, element);
			Assertions.assertEquals(writer.toString(), "[{}]");
		} catch (final JsonException e) {
			Assertions.fail(e);
		}
	}

	@Test
	public void saveArrayDouble() throws IOException {
		try {
			final StringWriter writer = new StringWriter();
			final JsonArray element = new JsonArray();
			element.add(JsonPrimitive.NULL);
			element.add(JsonPrimitive.NULL);
			Jsonify.save(writer, element);
			Assertions.assertEquals(writer.toString(), "[null,null]");
		} catch (final JsonException e) {
			Assertions.fail(e);
		}
	}

	@Test
	public void saveArrayTriple() throws IOException {
		try {
			final StringWriter writer = new StringWriter();
			final JsonArray element = new JsonArray();
			element.add(JsonPrimitive.NULL);
			element.add(JsonPrimitive.NULL);
			element.add(JsonPrimitive.NULL);
			Jsonify.save(writer, element);
			Assertions.assertEquals(writer.toString(), "[null,null,null]");
		} catch (final JsonException e) {
			Assertions.fail(e);
		}
	}

	// ====================

	@Test
	public void saveObjectEmpty() throws IOException {
		try {
			final StringWriter writer = new StringWriter();
			final JsonObject element = new JsonObject();
			Jsonify.save(writer, element);
			Assertions.assertEquals(writer.toString(), "{}");
		} catch (final JsonException e) {
			Assertions.fail(e);
		}
	}

	@Test
	public void saveObjectNull() throws IOException {
		try {
			final StringWriter writer = new StringWriter();
			final JsonObject element = new JsonObject();
			element.put("", JsonPrimitive.NULL);
			Jsonify.save(writer, element);
			Assertions.assertEquals(writer.toString(), "{\"\":null}");
		} catch (final JsonException e) {
			Assertions.fail(e);
		}
	}

	@Test
	public void saveObjectFalse() throws IOException {
		try {
			final StringWriter writer = new StringWriter();
			final JsonObject element = new JsonObject();
			element.put("", JsonPrimitive.FALSE);
			Jsonify.save(writer, element);
			Assertions.assertEquals(writer.toString(), "{\"\":false}");
		} catch (final JsonException e) {
			Assertions.fail(e);
		}
	}

	@Test
	public void saveObjectTrue() throws IOException {
		try {
			final StringWriter writer = new StringWriter();
			final JsonObject element = new JsonObject();
			element.put("", JsonPrimitive.TRUE);
			Jsonify.save(writer, element);
			Assertions.assertEquals(writer.toString(), "{\"\":true}");
		} catch (final JsonException e) {
			Assertions.fail(e);
		}
	}

	@Test
	public void saveObjectString() throws IOException {
		try {
			final StringWriter writer = new StringWriter();
			final JsonObject element = new JsonObject();
			element.put("", new JsonString(""));
			Jsonify.save(writer, element);
			Assertions.assertEquals(writer.toString(), "{\"\":\"\"}");
		} catch (final JsonException e) {
			Assertions.fail(e);
		}
	}

	@Test
	public void saveObjectNumber() throws IOException {
		try {
			final StringWriter writer = new StringWriter();
			final JsonObject element = new JsonObject();
			element.put("", new JsonNumber(0));
			Jsonify.save(writer, element);
			Assertions.assertEquals(writer.toString(), "{\"\":0}");
		} catch (final JsonException e) {
			Assertions.fail(e);
		}
	}

	@Test
	public void saveObjectArray() throws IOException {
		try {
			final StringWriter writer = new StringWriter();
			final JsonObject element = new JsonObject();
			element.put("", new JsonArray());
			Jsonify.save(writer, element);
			Assertions.assertEquals(writer.toString(), "{\"\":[]}");
		} catch (final JsonException e) {
			Assertions.fail(e);
		}
	}

	@Test
	public void saveObjectObject() throws IOException {
		try {
			final StringWriter writer = new StringWriter();
			final JsonObject element = new JsonObject();
			element.put("", new JsonObject());
			Jsonify.save(writer, element);
			Assertions.assertEquals(writer.toString(), "{\"\":{}}");
		} catch (final JsonException e) {
			Assertions.fail(e);
		}
	}

	@Test
	public void saveObjectDouble() throws IOException {
		try {
			final StringWriter writer = new StringWriter();
			final JsonObject element = new JsonObject();
			element.put("", JsonPrimitive.NULL);
			element.put("2", JsonPrimitive.NULL);
			Jsonify.save(writer, element);
			Assertions.assertEquals(writer.toString(), "{\"\":null,\"2\":null}");
		} catch (final JsonException e) {
			Assertions.fail(e);
		}
	}

	@Test
	public void saveObjectTriple() throws IOException {
		try {
			final StringWriter writer = new StringWriter();
			final JsonObject element = new JsonObject();
			element.put("", JsonPrimitive.NULL);
			element.put("2", JsonPrimitive.NULL);
			element.put("3", JsonPrimitive.NULL);
			Jsonify.save(writer, element);
			Assertions.assertEquals(writer.toString(), "{\"\":null,\"2\":null,\"3\":null}");
		} catch (final JsonException e) {
			Assertions.fail(e);
		}
	}

	// ====================

	@Test
	public void saveNull() throws IOException {
		try {
			final StringWriter writer = new StringWriter();
			Jsonify.save(writer, JsonPrimitive.NULL);
			Assertions.assertEquals(writer.toString(), "null");
		} catch (final JsonException e) {
			Assertions.fail(e);
		}
	}

	@Test
	public void saveTrue() throws IOException {
		try {
			final StringWriter writer = new StringWriter();
			Jsonify.save(writer, JsonPrimitive.TRUE);
			Assertions.assertEquals(writer.toString(), "true");
		} catch (final JsonException e) {
			Assertions.fail(e);
		}
	}

	@Test
	public void saveFalse() throws IOException {
		try {
			final StringWriter writer = new StringWriter();
			Jsonify.save(writer, JsonPrimitive.FALSE);
			Assertions.assertEquals(writer.toString(), "false");
		} catch (final JsonException e) {
			Assertions.fail(e);
		}
	}

	// ====================

	@Test
	public void saveStringEmpty() throws IOException {
		try {
			final StringWriter writer = new StringWriter();
			Jsonify.save(writer, new JsonString(""));
			Assertions.assertEquals(writer.toString(), "\"\"");
		} catch (final JsonException e) {
			Assertions.fail(e);
		}
	}

	@Test
	public void saveStringNormal() throws IOException {
		try {
			final StringWriter writer = new StringWriter();
			Jsonify.save(writer, new JsonString(
					"ABCDEFGHIJKLMNOPQRSTUVWXYZ<>:{}abcdefghijklmnopqrstuvwxyz,.;'[]/`123456789-=~!@#$%^&*_+()" +
							"\r\b\n\t\f\\\u041A\u0BEA\u1050\u13BA\u17A2\u1F72\u235A\u2742\u2F12\u3050\u32FA\uA050\uA05A\uA442\u4E2D"));
			Assertions.assertEquals(writer.toString(),
					"\"ABCDEFGHIJKLMNOPQRSTUVWXYZ<>:{}abcdefghijklmnopqrstuvwxyz,.;'[]/`123456789-=~!@#$%^&*_+()" +
							"\\r\\b\\n\\t\\f\\\\\u041A\u0BEA\u1050\u13BA\u17A2\u1F72\u235A\u2742\u2F12\u3050\u32FA\uA050\uA05A\uA442\u4E2D\"");
		} catch (final JsonException e) {
			Assertions.fail(e);
		}
	}

	// ====================

	@Test
	public void saveNumberIntegerSmall() throws IOException {
		try {
			final StringWriter writer = new StringWriter();
			Jsonify.save(writer, new JsonNumber(Integer.MAX_VALUE));
			Assertions.assertEquals(writer.toString(), String.valueOf(Integer.MAX_VALUE));
		} catch (final JsonException e) {
			Assertions.fail(e);
		}
	}

	@Test
	public void saveNumberIntegerMedium() throws IOException {
		try {
			final StringWriter writer = new StringWriter();
			Jsonify.save(writer, new JsonNumber(Long.MAX_VALUE));
			Assertions.assertEquals(writer.toString(), String.valueOf(Long.MAX_VALUE));
		} catch (final JsonException e) {
			Assertions.fail(e);
		}
	}

	@Test
	public void saveNumberIntegerBig() throws IOException {
		try {
			final StringWriter writer = new StringWriter();
			Jsonify.save(writer, new JsonNumber("123456789012345678901234567890"));
			Assertions.assertEquals(writer.toString(), "123456789012345678901234567890");
		} catch (final JsonException e) {
			Assertions.fail(e);
		}
	}

	@Test
	public void saveNumberDecimalFractionSmall() throws IOException {
		try {
			final StringWriter writer = new StringWriter();
			Jsonify.save(writer, new JsonNumber("0.1"));
			Assertions.assertEquals(writer.toString(), "0.1");
		} catch (final JsonException e) {
			Assertions.fail(e);
		}
	}

	@Test
	public void saveNumberDecimalFractionBig() throws IOException {
		try {
			final StringWriter writer = new StringWriter();
			Jsonify.save(writer, new JsonNumber("0.12345678901234567890"));
			Assertions.assertEquals(writer.toString(), "0.12345678901234567890");
		} catch (final JsonException e) {
			Assertions.fail(e);
		}
	}

	@Test
	public void saveNumberDecimalFractionTrailingZero() throws IOException {
		try {
			final StringWriter writer = new StringWriter();
			Jsonify.save(writer, new JsonNumber("0.1000"));
			Assertions.assertEquals(writer.toString(), "0.1000");
		} catch (final JsonException e) {
			Assertions.fail(e);
		}
	}

	@Test
	public void saveNumberDecimalFractionExponent() throws IOException {
		try {
			final StringWriter writer = new StringWriter();
			Jsonify.save(writer, new JsonNumber("1.0e9"));
			Assertions.assertEquals(writer.toString(), "1.0E+9");
		} catch (final JsonException e) {
			Assertions.fail(e);
		}
	}

	@Test
	public void saveNumberDecimalFractionTrailingZeroExponent() throws IOException {
		try {
			final StringWriter writer = new StringWriter();
			Jsonify.save(writer, new JsonNumber("1.0000e9"));
			Assertions.assertEquals(writer.toString(), "1.0000E+9");
		} catch (final JsonException e) {
			Assertions.fail(e);
		}
	}

	@Test
	public void saveNumberDecimalExponent() throws IOException {
		try {
			final StringWriter writer = new StringWriter();
			Jsonify.save(writer, new JsonNumber("1e9"));
			Assertions.assertEquals(writer.toString(), "1E+9");
		} catch (final JsonException e) {
			Assertions.fail(e);
		}
	}

}
