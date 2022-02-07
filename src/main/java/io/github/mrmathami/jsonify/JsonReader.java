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

import java.io.Closeable;
import java.io.IOException;
import java.io.Reader;
import java.util.BitSet;

/**
 * Json reader.
 */
public class JsonReader implements Closeable {
	/**
	 * The input reader.
	 */
	private final @NotNull Reader reader;

	/**
	 * Creates a json reader.
	 */
	public JsonReader(@NotNull Reader reader) {
		this.reader = reader;
	}

	//========================================

	/**
	 * The codepoint that has been undone reading.
	 */
	private int undo = -1;

	/**
	 * Undo reading a codepoint.
	 */
	private void undo(int undo) {
		// assert undo < 0;
		this.undo = undo;
	}

	/**
	 * Get next codepoint.
	 */
	private int read() throws IOException {
		final int undo = this.undo;
		if (undo >= 0) {
			this.undo = -1;
			return undo;
		}

		int u0 = reader.read();
		if (u0 < 0 || !Character.isHighSurrogate((char) u0)) {
			// eof or normal character
			return u0;
		}
		int u1 = reader.read();
		if (u1 >= 0 && Character.isLowSurrogate((char) u1)) {
			// extended character
			return Character.toCodePoint((char) u0, (char) u1);
		}
		// invalid/incomplete pair
		throw new IOException("Invalid input surrogate pair.");
	}

	/**
	 * Get next character, skip all allowed whitespaces.
	 */
	private int readNonWhitespace() throws IOException {
		while (true) {
			final int character = read();
			switch (character) {
				case '\t':
				case '\n':
				case '\r':
				case ' ':
					continue;
				default:
					return character;
			}
		}
	}

	/**
	 * Checks to make sure that the stream has not been closed
	 */
	private void ensureOpen() throws IOException {
		if (state == STATE_CLOSED) throw new IOException("Already closed!");
	}

	/**
	 * Close the JSON reader, also close the underlying reader.
	 */
	@Override
	public void close() throws IOException {
		if (state != STATE_CLOSED) {
			reader.close();
			lastStructures.clear();
			this.state = STATE_CLOSED;
		}
	}

	//========================================

	/**
	 * Last states of the parser's state machine. This bit set will be used as a stack of state. A bit with value 0
	 * indicates the parser is currently inside an object. A bit with value 1 indicates the parser is currently inside
	 * an array.
	 */
	private final @NotNull BitSet lastStructures = new BitSet();

	/**
	 * Last index of last states array. An index smaller than 0 indicates the parser is at the top level, any other
	 * value indicate the parser is inside an object or an array.
	 */
	private int lastStructureIndex = -1;

	/**
	 * This state indicates that the parser is closed.
	 */
	private static final int STATE_CLOSED = -1;

	/**
	 * This state indicates that the parser expect the next token is a Name.
	 */
	private static final int STATE_EXPECT_NAME = 0;

	/**
	 * This state indicates that the parser expect the next token is a Value.
	 */
	private static final int STATE_EXPECT_VALUE = 1;

	/**
	 * This state indicates that the parser expect the next token is an ArrayEnd or an ObjectEnd.
	 */
	private static final int STATE_ARRAY_OBJECT_END = 2;

	/**
	 * This state indicates that the parser expect the next token is an EOF.
	 */
	private static final int STATE_EXPECT_DOCUMENT_END = 3;

	/**
	 * Current state of the parser's state machine.
	 */
	private int state = STATE_EXPECT_VALUE;

	/**
	 * Parse next token. Throws JsonParsingException if there is an error while parsing input JSON.
	 */
	public @NotNull JsonToken nextToken() throws IOException, JsonParsingException {
		ensureOpen();
		return switch (state) {
			case STATE_EXPECT_NAME -> {
				final JsonToken name = name();
				// NOTE: the separator ':' are already consumed
				switch (name) {
					case OBJECT_END -> this.state = STATE_ARRAY_OBJECT_END;
					case NAME -> this.state = STATE_EXPECT_VALUE;
				}
				yield name;
			}
			case STATE_EXPECT_VALUE -> {
				final JsonToken value = value();
				switch (value) {
					case OBJECT_BEGIN -> {
						// push Object to the structure stack
						// set next expected token to be a Name
						this.state = STATE_EXPECT_NAME;
						lastStructures.clear(++lastStructureIndex);
					}
					case ARRAY_BEGIN -> {
						// push Array to the structure stack
						// set next expected token to be a Value
						this.state = STATE_EXPECT_VALUE;
						lastStructures.set(++lastStructureIndex);
					}
					case ARRAY_END -> this.state = STATE_ARRAY_OBJECT_END;
					case STRING, NUMBER, TRUE, FALSE, NULL -> consumeSeparator();
					case EOF -> throw new JsonParsingException("Empty JSON document is invalid!");
					default -> throw new AssertionError();
				}
				yield value;
			}
			case STATE_ARRAY_OBJECT_END -> lastStructures.get(lastStructureIndex)
					? JsonToken.ARRAY_END // array
					: JsonToken.OBJECT_END; // object

			case STATE_EXPECT_DOCUMENT_END -> {
				final int c = readNonWhitespace();
				if (c < 0) yield JsonToken.EOF;
				undo(c);
				throw new JsonParsingException("Unexpected character at the end of the document!");
			}
			default -> throw new AssertionError();
		};
	}

	/**
	 * Consume structure separator after a value and set state accordingly. This includes comma, array close bracket and
	 * object close bracket.
	 */
	private void consumeSeparator() throws IOException, JsonParsingException {
		if (lastStructureIndex >= 0) {
			// the parser is inside an object or an array
			final int c = readNonWhitespace();
			if (c == ',') {
				// not end of object/array yet
				this.state = lastStructures.get(lastStructureIndex)
						? STATE_EXPECT_VALUE // array
						: STATE_EXPECT_NAME; // object
			} else if (c == ']' || c == '}') {
				// end of object/array. Checking the closing character for sure
				if (lastStructures.get(lastStructureIndex) == (c == ']')) {
					this.state = STATE_ARRAY_OBJECT_END;
				} else {
					throw new JsonParsingException("Invalid closing character for current structure!");
				}
			} else {
				throw new JsonParsingException("Unexpected character after a value!");
			}
		} else {
			// the parser is at the top level, expect an EOF
			this.state = STATE_EXPECT_DOCUMENT_END;
		}
	}

	/**
	 * Skip over the end of an Array or an Object.
	 */
	public void endStructure() throws IOException, JsonParsingException {
		ensureOpen();
		if (state == STATE_ARRAY_OBJECT_END) {
			// at the end of a structure
			// pop structure stack
			this.lastStructureIndex -= 1;
			consumeSeparator();
		} else if (lastStructureIndex >= 0) {
			// in the middle of a structure, so skip everything until the end
			final int currentStructureIndex = this.lastStructureIndex;
			while (currentStructureIndex <= lastStructureIndex) {
				while (true) {
					final JsonToken token = nextToken();
					if (token == JsonToken.OBJECT_END || token == JsonToken.ARRAY_END) break;
				}
				// pop structure stack
				this.lastStructureIndex -= 1;
				consumeSeparator();
			}
		} else {
			throw new IllegalStateException("Not in a structure!");
		}
	}

	//========================================

	/**
	 * Latest value for String, Name or Number token.
	 */
	private @Nullable String value;

	/**
	 * True if the latest value is a Number.
	 */
	private boolean number;

	/**
	 * Get last String or Name token value.
	 */
	public @NotNull String getString() {
		if (value != null && !number) return value;
		throw new IllegalStateException("Last token is not a string or a name!");
	}

	/**
	 * Get last Number token value.
	 */
	public @NotNull Number getNumber() {
		if (value != null && number) return new LazyNumber(value);
		throw new IllegalStateException("Last token is not a number!");
	}

	/**
	 * Consume a Name token and the separator token.
	 */
	private @NotNull JsonToken name() throws IOException, JsonParsingException {
		final int c = readNonWhitespace();
		if (c == '\"') {
			this.value = string();
			this.number = false;
			if (readNonWhitespace() == ':') {
				return JsonToken.NAME;
			}
		} else if (c == '}') {
			return JsonToken.OBJECT_END;
		}
		throw new JsonParsingException("Unexpected character when parsing input JSON!");
	}

	/**
	 * Consume the Value token but DOES NOT consume the separator token.
	 */
	private @NotNull JsonToken value() throws IOException, JsonParsingException {
		this.value = null;
		final int c = readNonWhitespace();
		return switch (c) {
			case -1 -> JsonToken.EOF;
			case '[' -> JsonToken.ARRAY_BEGIN;
			case ']' -> JsonToken.ARRAY_END;
			case '{' -> JsonToken.OBJECT_BEGIN;
			case '\"' -> {
				this.value = string();
				this.number = false;
				yield JsonToken.STRING;
			}
			case '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '-' -> {
				this.value = number(c);
				this.number = true;
				yield JsonToken.NUMBER;
			}
			case 't' -> {
				if (read() == 'r' && read() == 'u' && read() == 'e') {
					yield JsonToken.TRUE;
				}
				throw new JsonParsingException("Unexpected character when parsing input JSON!");
			}
			case 'f' -> {
				if (read() == 'a' && read() == 'l' && read() == 's' && read() == 'e') {
					yield JsonToken.FALSE;
				}
				throw new JsonParsingException("Unexpected character when parsing input JSON!");
			}
			case 'n' -> {
				if (read() == 'u' && read() == 'l' && read() == 'l') {
					yield JsonToken.NULL;
				}
				throw new JsonParsingException("Unexpected character when parsing input JSON!");
			}
			default -> throw new JsonParsingException("Unexpected character when parsing input JSON!");
		};
	}

	/**
	 * Consume a Number token and set the number value accordingly.
	 */
	private @NotNull String number(int startCp) throws IOException, JsonParsingException {
		final StringBuilder builder = new StringBuilder();
		// first part: the integer
		int c = startCp;
		if (c == '-') {
			// minus sign
			builder.appendCodePoint('-');
			c = read();
		}
		if (c == '0') {
			// zero suffix
			builder.appendCodePoint('0');
			c = read();
		} else if (c >= '1' && c <= '9') {
			// digits
			do {
				builder.appendCodePoint(c);
				c = read();
			} while (c >= '0' && c <= '9');
		} else {
			throw new JsonParsingException("Invalid character in integer part of number!");
		}
		// second part: fraction
		if (c == '.') {
			// set decimal
			builder.appendCodePoint('.');
			c = read();
			// at least one digit
			if (c >= '0' && c <= '9') {
				// digits
				do {
					builder.appendCodePoint(c);
					c = read();
				} while (c >= '0' && c <= '9');
			} else {
				throw new JsonParsingException("Invalid character in fraction part of number!");
			}
		}
		// third part: exponent
		if (c == 'e' || c == 'E') {
			// set decimal
			builder.appendCodePoint(c);
			c = read();
			// sign
			if (c == '+' || c == '-') {
				builder.appendCodePoint(c);
				c = read();
			}
			// at least one digit
			if (c >= '0' && c <= '9') {
				// digits
				do {
					builder.appendCodePoint(c);
					c = read();
				} while (c >= '0' && c <= '9');
			} else {
				throw new JsonParsingException("Invalid character in exponent part of number!");
			}
		}
		undo(c);
		return builder.toString();
	}

	/**
	 * Consume a String token and set the string value accordingly.
	 */
	private @NotNull String string() throws IOException, JsonParsingException {
		// the open quote are already consumed
		final StringBuilder builder = new StringBuilder();
		while (true) {
			final int c = read();
			if (c >= ' ' && c != '\\' && c != '"') {
				// the specification do have a upper limit for codepoint in string
				// currently it is the same as the upper limit of the Unicode table
				// here I deliberately skip it for future-proof
				builder.appendCodePoint(c);
			} else if (c == '\\') {
				final int d = read();
				builder.appendCodePoint(switch (d) {
					case 'u' -> {
						int result = 1;
						do {
							int e = read();
							result = (result << 4) + switch (e) {
								case '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' -> e - '0';
								case 'A', 'B', 'C', 'D', 'E', 'F' -> e - 'A' + 10;
								case 'a', 'b', 'c', 'd', 'e', 'f' -> e - 'a' + 10;
								default -> throw new JsonParsingException("Invalid escape sequence in string!");
							};
						} while (result < 0x10000);
						yield result - 0x10000;
					}
					case 't' -> '\t';
					case 'b' -> '\b';
					case 'n' -> '\n';
					case 'r' -> '\r';
					case 'f' -> '\f';
					case '"', '\\', '/' -> d;
					default -> throw new JsonParsingException("Invalid escape sequence in string!");
				});
			} else if (c == '"') {
				return builder.toString();
			} else {
				throw new JsonParsingException("Invalid character in string!");
			}
		}
	}
}

