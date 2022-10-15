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
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.BitSet;

/**
 * JSON reader. The input reader should REALLY be a buffered one.
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
		if (undo < 0) {
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
		} else {
			this.undo = -1;
			return undo;
		}

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

	//========================================

	/**
	 * Last states of the reader's state machine. This bit set will be used as a stack of state. A bit with value 0
	 * indicates the reader is currently inside an object. A bit with value 1 indicates the reader is currently inside
	 * an array.
	 */
	private final @NotNull BitSet lastStructures = new BitSet();

	/**
	 * Last index of last states array. An index smaller than 0 indicates the reader is at the top level, any other
	 * value indicate the reader is inside an object or an array.
	 */
	private int lastStructureIndex = -1;

	/**
	 * This state indicates that the reader is in error state, nothing else can be done except throwing.
	 */
	private static final int STATE_ERROR = -2;

	/**
	 * This state indicates that the reader is closed.
	 */
	private static final int STATE_CLOSED = -1;

	/**
	 * This state indicates that the reader expects the next token is a Name.
	 */
	private static final int STATE_EXPECT_NAME = 0;

	/**
	 * This state indicates that the reader expects the next token is a Name or an ObjectEnd.
	 */
	private static final int STATE_EXPECT_NAME_OR_OBJECT_END = 1;

	/**
	 * This state indicates that the reader expects the next token is a Value.
	 */
	private static final int STATE_EXPECT_VALUE = 2;

	/**
	 * This state indicates that the reader expects the next token is a Value or an ArrayEnd.
	 */
	private static final int STATE_EXPECT_VALUE_OR_ARRAY_END = 3;

	/**
	 * This state indicates that the reader is already pass over an ArrayEnd token and now waiting to receive a
	 * endStructure call.
	 */
	private static final int STATE_ARRAY_END = 4;

	/**
	 * This state indicates that the reader is already pass over an ObjectEnd token and now waiting to receive a
	 * endStructure call.
	 */
	private static final int STATE_OBJECT_END = 5;

	/**
	 * This state indicates that the reader expects the next token is an EOF.
	 */
	private static final int STATE_EXPECT_DOCUMENT_END = 6;

	/**
	 * Current state of the reader's state machine.
	 */
	private int state = STATE_EXPECT_VALUE;

	//========================================

	/**
	 * Array begin token.
	 */
	public static final int TOKEN_ARRAY_BEGIN = 0;

	/**
	 * Array end token.
	 */
	public static final int TOKEN_ARRAY_END = 1;

	/**
	 * Object begin token.
	 */
	public static final int TOKEN_OBJECT_BEGIN = 2;

	/**
	 * Object end token.
	 */
	public static final int TOKEN_OBJECT_END = 3;

	/**
	 * A name token.
	 */
	public static final int TOKEN_NAME = 4;

	/**
	 * A string token.
	 */
	public static final int TOKEN_STRING = 5;

	/**
	 * A number token.
	 */
	public static final int TOKEN_NUMBER = 6;

	/**
	 * A boolean true token.
	 */
	public static final int TOKEN_TRUE = 7;

	/**
	 * A boolean false token.
	 */
	public static final int TOKEN_FALSE = 8;

	/**
	 * A null token.
	 */
	public static final int TOKEN_NULL = 9;

	/**
	 * EOF token
	 */
	public static final int TOKEN_EOF = 10;

	//========================================

	/**
	 * Checks to make sure that the stream has not been closed and the reader is not in error state.
	 */
	private void ensureOpenAndValid() throws IOException {
		if (state == STATE_CLOSED) throw new IOException("Already closed!");
		if (state == STATE_ERROR) throw new JsonException("Reader is in error state!");
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

	/**
	 * Parse next token and return token. Throws JsonException if there is an error while parsing input JSON.
	 * <p>
	 * Note: This method will not skip the array end token and the object end token, instead it returns
	 * {@link #TOKEN_ARRAY_END} or {@link #TOKEN_OBJECT_END} repeatedly.
	 */
	public int nextToken() throws IOException {
		ensureOpenAndValid();
		try {
			switch (state) {
				case STATE_EXPECT_NAME:
				case STATE_EXPECT_NAME_OR_OBJECT_END:
					final int name = name();
					// NOTE: the separator ':' are already consumed
					switch (name) {
						case TOKEN_NAME:
							this.state = STATE_EXPECT_VALUE;
							break;
						case TOKEN_OBJECT_END:
							if (state == STATE_EXPECT_NAME_OR_OBJECT_END) {
								this.state = STATE_OBJECT_END;
							} else {
								throw new JsonException("Unexpected closing character!");
							}
							break;
						default:
							throw new AssertionError();
					}
					return name;
				case STATE_EXPECT_VALUE:
				case STATE_EXPECT_VALUE_OR_ARRAY_END:
					final int value = value();
					switch (value) {
						case TOKEN_OBJECT_BEGIN:
							// push Object to the structure stack
							// set next expected token to be a Name
							this.state = STATE_EXPECT_NAME_OR_OBJECT_END;
							lastStructures.clear(++this.lastStructureIndex);
							break;
						case TOKEN_ARRAY_BEGIN:
							// push Array to the structure stack
							// set next expected token to be a Value
							this.state = STATE_EXPECT_VALUE_OR_ARRAY_END;
							lastStructures.set(++this.lastStructureIndex);
							break;
						case TOKEN_ARRAY_END:
							if (state == STATE_EXPECT_VALUE_OR_ARRAY_END && lastStructureIndex >= 0) {
								this.state = STATE_ARRAY_END;
							} else {
								throw new JsonException("Unexpected closing character!");
							}
							break;
						case TOKEN_STRING:
						case TOKEN_NUMBER:
						case TOKEN_TRUE:
						case TOKEN_FALSE:
						case TOKEN_NULL:
							consumeSeparator();
							break;
						case TOKEN_EOF:
							throw new JsonException("Empty JSON document is invalid!");
						default:
							throw new AssertionError();
					}
					return value;
				case STATE_ARRAY_END:
					return TOKEN_ARRAY_END;
				case STATE_OBJECT_END:
					return TOKEN_OBJECT_END;
				case STATE_EXPECT_DOCUMENT_END:
					final int c = readNonWhitespace();
					if (c < 0) return TOKEN_EOF;
					throw new JsonException("Unexpected character at the end of the document!");
				default:
					throw new AssertionError();
			}
		} catch (IOException exception) {
			this.state = STATE_ERROR;
			throw exception;
		}
	}

	/**
	 * Consume structure separator after a value and set state accordingly. This includes comma, array close bracket and
	 * object close bracket.
	 */
	private void consumeSeparator() throws IOException {
		if (lastStructureIndex >= 0) {
			// the reader is inside an object or an array
			final int c = readNonWhitespace();
			if (c == ',') {
				// not end of object/array yet
				this.state = lastStructures.get(lastStructureIndex)
						? STATE_EXPECT_VALUE // array
						: STATE_EXPECT_NAME; // object
			} else if (c == ']') {
				// end of array. Checking the closing character...
				if (lastStructures.get(lastStructureIndex)) {
					this.state = STATE_ARRAY_END;
				} else {
					throw new JsonException("Invalid closing character for object!");
				}
			} else if (c == '}') {
				// end of object. Checking the closing character...
				if (!lastStructures.get(lastStructureIndex)) {
					this.state = STATE_OBJECT_END;
				} else {
					throw new JsonException("Invalid closing character for array!");
				}
			} else {
				throw new JsonException("Unexpected character after a value!");
			}
		} else {
			// the reader is at the top level, expect an EOF
			this.state = STATE_EXPECT_DOCUMENT_END;
		}
	}

	/**
	 * Skip over the end of an Array or an Object.
	 */
	public void endStructure() throws IOException {
		ensureOpenAndValid();
		try {
			if (state == STATE_ARRAY_END || state == STATE_OBJECT_END) {
				// at the end of a structure
				// pop structure stack
				this.lastStructureIndex -= 1;
				consumeSeparator();
			} else if (lastStructureIndex >= 0) {
				// in the middle of a structure, so skip everything until the end
				final int currentStructureIndex = this.lastStructureIndex;
				while (currentStructureIndex <= lastStructureIndex) {
					while (true) {
						final int token = nextToken();
						if (token == TOKEN_ARRAY_END || token == TOKEN_OBJECT_END) break;
					}
					// pop structure stack
					this.lastStructureIndex -= 1;
					consumeSeparator();
				}
			} else {
				throw new IllegalStateException("Not in a structure!");
			}
		} catch (IOException exception) {
			this.state = STATE_ERROR;
			throw exception;
		}
	}

	//========================================

	/**
	 * Latest value for String or Name token.
	 */
	private @Nullable String string;

	/**
	 * Latest value for Number token.
	 */
	private @Nullable JsonNumber number;

	/**
	 * Get last String or Name token value.
	 */
	public @NotNull String getString() {
		if (string != null) return string;
		throw new IllegalStateException("Last token is not a string or a name!");
	}

	/**
	 * Get last Number token value.
	 */
	public @NotNull JsonNumber getNumber() {
		if (number != null) return number;
		throw new IllegalStateException("Last token is not a number!");
	}

	/**
	 * Consume a Name token and the separator token.
	 */
	private int name() throws IOException {
		final int c = readNonWhitespace();
		if (c == '\"') {
			string();
			if (readNonWhitespace() == ':') {
				return TOKEN_NAME;
			}
		} else if (c == '}') {
			return TOKEN_OBJECT_END;
		}
		throw new JsonException("Unexpected character when parsing input JSON!");
	}

	/**
	 * Consume the Value token but DOES NOT consume the separator token.
	 */
	private int value() throws IOException {
		final int c = readNonWhitespace();
		if (c == '\"') {
			string();
			return TOKEN_STRING;
		} else if (c >= '0' && c <= '9' || c == '-') {
			number(c);
			return TOKEN_NUMBER;
		} else if (c == 't') {
			if (read() == 'r' && read() == 'u' && read() == 'e') {
				return TOKEN_TRUE;
			}
		} else if (c == 'f') {
			if (read() == 'a' && read() == 'l' && read() == 's' && read() == 'e') {
				return TOKEN_FALSE;
			}
		} else if (c == 'n') {
			if (read() == 'u' && read() == 'l' && read() == 'l') {
				return TOKEN_NULL;
			}
		} else if (c == '[') {
			return TOKEN_ARRAY_BEGIN;
		} else if (c == ']') {
			return TOKEN_ARRAY_END;
		} else if (c == '{') {
			return TOKEN_OBJECT_BEGIN;
		} else if (c == -1) {
			return TOKEN_EOF;
		}
		throw new JsonException("Unexpected character when parsing input JSON!");
	}

	/**
	 * Consume a Number token and set the number value accordingly.
	 */
	private void number(int startCp) throws IOException {
		final StringBuilder builder = new StringBuilder();
		boolean integer = true;
		// first part: the integer
		int c = startCp;
		if (c == '-') {
			// minus sign
			builder.append('-');
			c = read();
		}
		if (c == '0') {
			// zero suffix
			builder.append('0');
			c = read();
		} else if (c >= '1' && c <= '9') {
			// digits
			do {
				builder.append((char) c);
				c = read();
			} while (c >= '0' && c <= '9');
		} else {
			throw new JsonException("Invalid character in integer part of number!");
		}
		// second part: fraction
		if (c == '.') {
			integer = false;
			// set decimal
			builder.append('.');
			c = read();
			// at least one digit
			if (c >= '0' && c <= '9') {
				// digits
				do {
					builder.append((char) c);
					c = read();
				} while (c >= '0' && c <= '9');
			} else {
				throw new JsonException("Invalid character in fraction part of number!");
			}
		}
		// third part: exponent
		if (c == 'e' || c == 'E') {
			integer = false;
			// set decimal
			builder.append((char) c);
			c = read();
			// sign
			if (c == '+' || c == '-') {
				builder.append((char) c);
				c = read();
			}
			// at least one digit
			if (c >= '0' && c <= '9') {
				// digits
				do {
					builder.append((char) c);
					c = read();
				} while (c >= '0' && c <= '9');
			} else {
				throw new JsonException("Invalid character in exponent part of number!");
			}
		}
		undo(c);
		final String number = builder.toString();
		this.string = null;
		this.number = integer
				? new JsonNumber(new BigInteger(number))
				: new JsonNumber(new BigDecimal(number));
	}

	/**
	 * Consume a String token and set the string value accordingly.
	 */
	private void string() throws IOException {
		// the open quote are already consumed
		final StringBuilder builder = new StringBuilder();
		while (true) {
			final int c = read();
			if (c >= ' ' && c != '\\' && c != '"') {
				// the specification do have an upper limit for codepoint in string
				// currently it is the same as the upper limit of the Unicode table
				// here I deliberately skip the specification codepoint limit for future-proof
				builder.appendCodePoint(c);
			} else if (c == '\\') {
				final int d = read();
				if (d == '"' || d == '\\' || d == '/') {
					builder.append((char) d);
				} else if (d == 't') {
					builder.append('\t');
				} else if (d == 'b') {
					builder.append('\b');
				} else if (d == 'n') {
					builder.append('\n');
				} else if (d == 'r') {
					builder.append('\r');
				} else if (d == 'f') {
					builder.append('\f');
				} else if (d == 'u') {
					int result = 1;
					do {
						int e = read();
						if (e >= '0' && e <= '9') {
							result = (result << 4) + e - '0';
						} else if (e >= 'A' && e <= 'F') {
							result = (result << 4) + e - 'A' + 10;
						} else if (e >= 'a' && e <= 'f') {
							result = (result << 4) + e - 'a' + 10;
						} else {
							throw new JsonException("Invalid escape sequence in string!");
						}
					} while (result < 0x10000);
					builder.appendCodePoint(result - 0x10000);
				} else {
					throw new JsonException("Invalid escape sequence in string!");
				}
			} else if (c == '"') {
				this.string = builder.toString();
				this.number = null;
				return;
			} else {
				throw new JsonException("Invalid character in string!");
			}
		}
	}
}

