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

import java.io.Closeable;
import java.io.IOException;
import java.io.Writer;
import java.util.BitSet;
import java.util.PrimitiveIterator;

/**
 * JSON writer.
 */
public class JsonWriter implements Closeable {
	private final @NotNull Writer writer;

	public JsonWriter(@NotNull Writer writer) {
		this.writer = writer;
	}

	//========================================

	/**
	 * Checks to make sure that the stream has not been closed
	 */
	private void ensureOpen() throws IOException {
		if (state == STATE_CLOSED) throw new IOException("Already closed!");
	}

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

	public void beginArray() throws IOException, JsonException {
		switch (state) {
			case STATE_CLOSED:
				throw new IllegalStateException("Already closed!");
			case STATE_EXPECT_VALUE_WITH_COLON:
				writer.write(":[");
				break;
			case STATE_EXPECT_VALUE_WITH_COMMA:
				writer.write(",[");
				break;
			case STATE_EXPECT_VALUE_NO_SEPARATOR:
				writer.write('[');
				break;
			default:
				throw new JsonException("Array begin not expected!");
		}
		lastStructures.set(++this.lastStructureIndex);
		this.state = STATE_EXPECT_VALUE_NO_SEPARATOR;
	}

	public void endArray() throws IOException, JsonException {
		switch (state) {
			case STATE_CLOSED:
				throw new IOException("Already closed!");
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
				throw new JsonException("Array end not expected!");
		}
	}

	public void beginObject() throws IOException, JsonException {
		switch (state) {
			case STATE_CLOSED:
				throw new IllegalStateException("Already closed!");
			case STATE_EXPECT_VALUE_WITH_COLON:
				writer.write(":{");
				break;
			case STATE_EXPECT_VALUE_WITH_COMMA:
				writer.write(",{");
				break;
			case STATE_EXPECT_VALUE_NO_SEPARATOR:
				writer.write('{');
				break;
			default:
				throw new JsonException("Object begin not expected!");
		}
		lastStructures.clear(++this.lastStructureIndex);
		this.state = STATE_EXPECT_NAME_NO_SEPARATOR;
	}

	public void endObject() throws IOException, JsonException {
		switch (state) {
			case STATE_CLOSED:
				throw new IllegalStateException("Already closed!");
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
			default:
				throw new JsonException("Object end not expected!");
		}
	}

	public void name(@NotNull String name) throws IOException, JsonException {
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
				throw new JsonException("Name not expected!");
		}
	}

	public void valueBoolean(boolean value) throws IOException, JsonException {
		writeValueRaw(value ? "true" : "false");
	}

	public void valueBoolean(@NotNull Boolean value) throws IOException, JsonException {
		writeValueRaw(value.toString());
	}

	public void valueNumber(byte value) throws IOException, JsonException {
		writeValueRaw(String.valueOf(value));
	}

	public void valueNumber(short value) throws IOException, JsonException {
		writeValueRaw(String.valueOf(value));
	}

	public void valueNumber(int value) throws IOException, JsonException {
		writeValueRaw(String.valueOf(value));
	}

	public void valueNumber(long value) throws IOException, JsonException {
		writeValueRaw(String.valueOf(value));
	}

	public void valueNumber(float value) throws IOException, JsonException {
		writeValueRaw(String.valueOf(value));
	}

	public void valueNumber(double value) throws IOException, JsonException {
		writeValueRaw(String.valueOf(value));
	}

	public void valueNumber(@NotNull Number value) throws IOException, JsonException {
		writeValueRaw(value.toString());
	}

	public void valueString(char value) throws IOException, JsonException {
		writeValueString(String.valueOf(value));
	}

	public void valueString(@NotNull String value) throws IOException, JsonException {
		writeValueString(value);
	}

	public void valueNull() throws IOException, JsonException {
		writeValueRaw("null");
	}

	//========================================

	private void writeValueRaw(@NotNull String rawValue) throws IOException, JsonException {
		writeValueSeparator();
		writer.write(rawValue);
	}

	private void writeValueString(@NotNull String string) throws IOException, JsonException {
		writeValueSeparator();
		writeStringUnchecked(string);
	}

	private void writeValueSeparator() throws IOException, JsonException {
		switch (state) {
			case STATE_CLOSED:
				throw new IllegalStateException("Already closed!");
			case STATE_EXPECT_VALUE_WITH_COLON:
				this.state = STATE_EXPECT_NAME_WITH_COMMA;
				writer.write(':');
				break;
			case STATE_EXPECT_VALUE_WITH_COMMA:
				writer.write(',');
				break;
			case STATE_EXPECT_VALUE_NO_SEPARATOR:
				if (lastStructureIndex >= 0) {
					this.state = STATE_EXPECT_VALUE_WITH_COMMA;
				} else {
					this.state = STATE_EXPECT_DOCUMENT_END;
				}
				break;
			default:
				throw new JsonException("Value not expected!");
		}
	}

	private void writeStringUnchecked(@NotNull String string) throws IOException {
		writer.write('"');
		final PrimitiveIterator.OfInt iterator = string.codePoints().iterator();
		while (iterator.hasNext()) {
			final int c = iterator.nextInt();
			if (c == '"') {
				writer.write("\\\"");
			} else if (c == '\\') {
				writer.write("\\\\");
			} else if (c >= ' ') {
				writer.write(Character.toString(c));
			} else if (c == '\r') {
				writer.write("\\r");
			} else if (c == '\n') {
				writer.write("\\n");
			} else if (c == '\t') {
				writer.write("\\t");
			} else {
				writer.write("\\u00");
				writer.write(c >= 0x10 ? '1' : '0');
				writer.write(c + (c >= 10 ? 'A' - 10 : '0'));
			}
		}
		writer.write('"');
	}
}
