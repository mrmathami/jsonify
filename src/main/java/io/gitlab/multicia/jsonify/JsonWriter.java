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

package io.gitlab.multicia.jsonify;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.Writer;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.BitSet;
import java.util.IdentityHashMap;
import java.util.Map;

/**
 * JSON writer.
 */
public class JsonWriter implements JsonOutput {
	/**
	 * The output writer.
	 */
	private final @NotNull Writer writer;

	/**
	 * Creates a json writer.
	 */
	public JsonWriter(@NotNull Writer writer) {
		this.writer = writer;
	}

	//========================================

	/**
	 * Save JSON element to output json.
	 */
	public static void write(@NotNull Writer outputWriter, @NotNull JsonElement element) throws IOException {
		try (final JsonWriter writer = new JsonWriter(outputWriter)) {
			writer.value(element);
		}
	}

	//========================================

	/**
	 * Close the JSON writer, also close the underlying writer.
	 */
	@Override
	public void close() throws IOException {
		if (state != STATE_CLOSED) {
			writer.close();
			lastStructures.clear();
			this.state = STATE_CLOSED;
		}
	}

	/**
	 * Check if the JSON writer is done writing the JSON. The writer is considered done writing the JSON when there is
	 * nothing more can be written after that point that makes the output JSON still valid.
	 */
	public boolean isDone() {
		if (state == STATE_CLOSED) {
			throw new IllegalStateException("Already closed!");
		}
		return state == STATE_EXPECT_DOCUMENT_END;
	}

	//========================================

	/**
	 * Last states of the writer's state machine. This bit set will be used as a stack of state. A bit with value 0
	 * indicates the writer is currently inside an object. A bit with value 1 indicates the writer is currently inside
	 * an array.
	 */
	private final @NotNull BitSet lastStructures = new BitSet();

	/**
	 * Last index of last states array. An index smaller than 0 indicates the writer is at the top level, any other
	 * value indicate the writer is inside an object or an array.
	 */
	private int lastStructureIndex = -1;

	/**
	 * This state indicates that the writer is closed.
	 */
	private static final int STATE_CLOSED = -1;

	/**
	 * This state indicates that the writer is at the beginning of an object, and the writer expects the next token is a
	 * Name or an ObjectEnd.
	 */
	private static final int STATE_EXPECT_NAME_NO_SEPARATOR = 0;

	/**
	 * This state indicates that the writer is in the middle of an object, and the writer expects the next token is a
	 * Name or an ObjectEnd, and will automatically write a Comma before writing the name if the next token is a Name.
	 */
	private static final int STATE_EXPECT_NAME_WITH_COMMA = 1;

	/**
	 * This state indicates that the writer is either at the beginning of an array. and the writer expects the next
	 * token is a Value.
	 */
	private static final int STATE_EXPECT_VALUE_NO_SEPARATOR = 2;

	/**
	 * This state indicates that the writer is in the middle of an array, ann the writer expects the next token is a
	 * Value, and will automatically write a Comma before writing the value.
	 */
	private static final int STATE_EXPECT_VALUE_WITH_COMMA = 3;

	/**
	 * This state indicates that the writer is after the name in the middle of an object, ann the writer expects the
	 * next token is a Value, and will automatically write a Colon before writing the value.
	 */
	private static final int STATE_EXPECT_VALUE_WITH_COLON = 4;

	/**
	 * This state indicates that the writer expects nothing else will be written, and waiting for a call to close the
	 * JSON writer.
	 */
	private static final int STATE_EXPECT_DOCUMENT_END = 5;

	/**
	 * Current state of the writer's state machine.
	 */
	private int state = STATE_EXPECT_VALUE_NO_SEPARATOR;

	//========================================

	/**
	 * Begin an array. Throws {@link JsonIOException} if this is unexpected.
	 */
	public void beginArray() throws IOException {
		switch (state) {
			case STATE_CLOSED -> throw new IllegalStateException("Already closed!");
			case STATE_EXPECT_VALUE_WITH_COLON -> writer.write(":[");
			case STATE_EXPECT_VALUE_WITH_COMMA -> writer.write(",[");
			case STATE_EXPECT_VALUE_NO_SEPARATOR -> writer.write('[');
			default -> throw new JsonIOException("Array begin not expected!");
		}
		lastStructures.set(++this.lastStructureIndex);
		this.state = STATE_EXPECT_VALUE_NO_SEPARATOR;
	}

	/**
	 * Begin an object. Throws {@link JsonIOException} if this is unexpected.
	 */
	public void beginObject() throws IOException {
		switch (state) {
			case STATE_CLOSED -> throw new IllegalStateException("Already closed!");
			case STATE_EXPECT_VALUE_WITH_COLON -> writer.write(":{");
			case STATE_EXPECT_VALUE_WITH_COMMA -> writer.write(",{");
			case STATE_EXPECT_VALUE_NO_SEPARATOR -> writer.write('{');
			default -> throw new JsonIOException("Object begin not expected!");
		}
		lastStructures.clear(++this.lastStructureIndex);
		this.state = STATE_EXPECT_NAME_NO_SEPARATOR;
	}

	/**
	 * End an array or an object. Throws {@link JsonIOException} if this is unexpected.
	 */
	public void end() throws IOException {
		switch (state) {
			case STATE_CLOSED:
				throw new IOException("Already closed!");
			case STATE_EXPECT_NAME_NO_SEPARATOR:
			case STATE_EXPECT_NAME_WITH_COMMA:
				if (!lastStructures.get(lastStructureIndex)) {
					this.lastStructureIndex -= 1;
					writer.append('}');
					this.state = lastStructureIndex >= 0
							? lastStructures.get(lastStructureIndex)
							? STATE_EXPECT_VALUE_WITH_COMMA
							: STATE_EXPECT_NAME_WITH_COMMA
							: STATE_EXPECT_DOCUMENT_END;
					return;
				}
			case STATE_EXPECT_VALUE_NO_SEPARATOR:
			case STATE_EXPECT_VALUE_WITH_COMMA:
				if (lastStructures.get(lastStructureIndex)) {
					this.lastStructureIndex -= 1;
					writer.append(']');
					this.state = lastStructureIndex >= 0
							? lastStructures.get(lastStructureIndex)
							? STATE_EXPECT_VALUE_WITH_COMMA
							: STATE_EXPECT_NAME_WITH_COMMA
							: STATE_EXPECT_DOCUMENT_END;
					return;
				}
			default:
				throw new JsonIOException("Array end not expected!");
		}
	}

	/**
	 * Write a name. Throws {@link JsonIOException} if this is unexpected.
	 */
	public void name(@NotNull String name) throws IOException {
		switch (state) {
			case STATE_CLOSED:
				throw new IllegalStateException("Already closed!");
			case STATE_EXPECT_NAME_WITH_COMMA:
				writer.append(',');
			case STATE_EXPECT_NAME_NO_SEPARATOR:
				writeStringUnchecked(name);
				this.state = STATE_EXPECT_VALUE_WITH_COLON;
				return;
			default:
				throw new JsonIOException("Name not expected!");
		}
	}

	/**
	 * Write a boolean value. Throws {@link JsonIOException} if this is unexpected.
	 */
	public void valueBoolean(boolean value) throws IOException {
		writeValueRaw(value ? "true" : "false");
	}

	/**
	 * Write a integer number value. Throws {@link JsonIOException} if this is unexpected.
	 */
	public void valueNumber(long value) throws IOException {
		writeValueRaw(Long.toString(value));
	}

	/**
	 * Write a decimal number value. Throws {@link JsonIOException} if this is unexpected.
	 */
	public void valueNumber(double value) throws IOException {
		if (Double.isFinite(value)) {
			writeValueRaw(Double.toString(value));
		} else {
			throw new NumberFormatException("JSON number cannot be NaN or Infinity.");
		}
	}

	/**
	 * Write a big integer number value. Throws {@link JsonIOException} if this is unexpected.
	 */
	public void valueNumber(@NotNull BigInteger value) throws IOException {
		writeValueRaw(value.toString());
	}

	/**
	 * Write a big decimal number value. Throws {@link JsonIOException} if this is unexpected.
	 */
	public void valueNumber(@NotNull BigDecimal value) throws IOException {
		writeValueRaw(value.toString());
	}

	/**
	 * Write a string value. Throws {@link JsonIOException} if this is unexpected.
	 */
	public void valueString(char value) throws IOException {
		writeValueString(String.valueOf(value));
	}

	/**
	 * Write a string value. Throws {@link JsonIOException} if this is unexpected.
	 */
	public void valueString(@NotNull String value) throws IOException {
		writeValueString(value);
	}

	/**
	 * Write a null value. Throws {@link JsonIOException} if this is unexpected.
	 */
	public void valueNull() throws IOException {
		writeValueRaw("null");
	}

	//========================================

	/**
	 * The active element stack, use to detect recursive write of the same element.
	 */
	private final @NotNull Map<@NotNull JsonElement, @NotNull JsonElement> recursionStack = new IdentityHashMap<>();

	/**
	 * Write a {@link JsonElement} value. Throws {@link JsonIOException} if this is unexpected.
	 */
	@Override
	public void value(@NotNull JsonElement element) throws IOException {
		if (element instanceof JsonNumber) {
			valueNumber((JsonNumber) element);
		} else if (element instanceof JsonString) {
			valueString(element.toString());
		} else if (element instanceof JsonKeyword) {
			if (element == JsonKeyword.TRUE) {
				valueBoolean(true);
			} else if (element == JsonKeyword.FALSE) {
				valueBoolean(false);
			} else if (element == JsonKeyword.NULL) {
				valueNull();
			} else {
				throw new AssertionError(); // safeguard
			}
		} else if (element instanceof JsonArray) {
			valueArray((JsonArray) element);
		} else if (element instanceof JsonObject) {
			valueObject((JsonObject) element);
		} else {
			throw new JsonIOException("Unknown element!");
		}
	}

	/**
	 * Write a {@link JsonNumber} value. Throws {@link JsonIOException} if this is unexpected.
	 */
	private void valueNumber(@NotNull JsonNumber element) throws IOException {
		final Number number = element.getValue();
		if (number instanceof Long) {
			valueNumber((Long) number);
		} else if (number instanceof Double) {
			valueNumber((Double) number);
		} else if (number instanceof BigInteger) {
			valueNumber((BigInteger) number);
		} else if (number instanceof BigDecimal) {
			valueNumber((BigDecimal) number);
		} else {
			throw new AssertionError();
		}
	}

	/**
	 * Write a {@link JsonArray}. Throws {@link JsonIOException} if this is unexpected.
	 */
	private void valueArray(@NotNull JsonArray array) throws IOException {
		// check recursion
		if (recursionStack.put(array, array) != null) throw new JsonIOException("Recursive structure detected!");
		// write array
		beginArray();
		for (final JsonElement arrayElement : array) {
			value(arrayElement);
		}
		end();
		// remove from recursion stack
		if (recursionStack.remove(array) != array) throw new AssertionError(); // safeguard
	}

	/**
	 * Write a {@link JsonObject}. Throws {@link JsonIOException} if this is unexpected.
	 */
	private void valueObject(@NotNull JsonObject object) throws IOException {
		// check recursion
		if (recursionStack.put(object, object) != null) throw new JsonIOException("Recursive structure detected!");
		// write object
		beginObject();
		for (final Map.Entry<String, JsonElement> entry : object.entrySet()) {
			name(entry.getKey());
			value(entry.getValue());
		}
		end();
		// remove from recursion stack
		if (recursionStack.remove(object) != object) throw new AssertionError(); // safeguard
	}

	//========================================

	/**
	 * Write value in raw mode instead of string mode. Raw mode does not escape the string.
	 */
	private void writeValueRaw(@NotNull String rawValue) throws IOException {
		writeValueSeparator();
		writer.write(rawValue);
	}

	/**
	 * Write value in string mode instead of raw mode. String mode does escape the string and put them in quote.
	 */
	private void writeValueString(@NotNull String string) throws IOException {
		writeValueSeparator();
		writeStringUnchecked(string);
	}

	/**
	 * Write the separator (COLON for the name value separator, COMMA for the value separator or for name-value pair
	 * separator)
	 */
	private void writeValueSeparator() throws IOException {
		switch (state) {
			case STATE_CLOSED -> throw new IllegalStateException("Already closed!");
			case STATE_EXPECT_VALUE_WITH_COLON -> {
				this.state = STATE_EXPECT_NAME_WITH_COMMA;
				writer.write(':');
			}
			case STATE_EXPECT_VALUE_WITH_COMMA -> writer.write(',');
			case STATE_EXPECT_VALUE_NO_SEPARATOR -> {
				if (lastStructureIndex >= 0) {
					this.state = STATE_EXPECT_VALUE_WITH_COMMA;
				} else {
					this.state = STATE_EXPECT_DOCUMENT_END;
				}
			}
			default -> throw new JsonIOException("Value not expected!");
		}
	}

	/**
	 * Escape string and write out the escaped string.
	 */
	private void writeStringUnchecked(@NotNull String string) throws IOException {
		writer.write('"');
		final int length = string.length();
		int count = 0;
		for (int index = 0; index < length; index++) {
			final int c = string.charAt(index);
			if (c >= ' ' && c != '"' && c != '\\') {
				count += 1;
			} else {
				if (count > 0) {
					writer.write(string, index - count, count);
					count = 0;
				}
				switch (c) {
					case '"' -> writer.write("\\\"");
					case '\\' -> writer.write("\\\\");
					case '\r' -> writer.write("\\r");
					case '\n' -> writer.write("\\n");
					case '\t' -> writer.write("\\t");
					case '\b' -> writer.write("\\b");
					case '\f' -> writer.write("\\f");
					default -> {
						writer.write("\\u00");
						writer.write(c >= 0x10 ? '1' : '0');
						writer.write(c + (c >= 10 ? 'A' - 10 : '0'));
					}
				}
			}
		}
		if (count > 0) writer.write(string, length - count, count);
		writer.write('"');
	}
}
