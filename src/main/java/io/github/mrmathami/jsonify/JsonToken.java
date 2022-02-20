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

import java.util.List;

/**
 * Tokens.
 */
public enum JsonToken {
	/**
	 * Array begin token.
	 */
	ARRAY_BEGIN,

	/**
	 * Array end token.
	 */
	ARRAY_END,

	/**
	 * Object begin token.
	 */
	OBJECT_BEGIN,

	/**
	 * Object end token.
	 */
	OBJECT_END,

	/**
	 * A name token.
	 */
	NAME,

	/**
	 * A string token.
	 */
	STRING,

	/**
	 * A number token.
	 */
	NUMBER,

	/**
	 * A boolean true token.
	 */
	TRUE,

	/**
	 * A boolean false token.
	 */
	FALSE,

	/**
	 * A null token.
	 */
	NULL,

	/**
	 * EOF token
	 */
	EOF;

	public static final @NotNull List<JsonToken> values = List.of(values());

	//========================================

	/**
	 * The ordinal of {@code JsonToken.ARRAY_BEGIN} in {@code JsonToken.values}.
	 */
	public static final int TOKEN_ARRAY_BEGIN = 0;

	/**
	 * The ordinal of {@code JsonToken.ARRAY_END} in {@code JsonToken.values}.
	 */
	public static final int TOKEN_ARRAY_END = 1;

	/**
	 * The ordinal of {@code JsonToken.OBJECT_BEGIN} in {@code JsonToken.values}.
	 */
	public static final int TOKEN_OBJECT_BEGIN = 2;

	/**
	 * The ordinal of {@code JsonToken.OBJECT_END} in {@code JsonToken.values}.
	 */
	public static final int TOKEN_OBJECT_END = 3;

	/**
	 * The ordinal of {@code JsonToken.NAME} in {@code JsonToken.values}.
	 */
	public static final int TOKEN_NAME = 4;

	/**
	 * The ordinal of {@code JsonToken.STRING} in {@code JsonToken.values}.
	 */
	public static final int TOKEN_STRING = 5;

	/**
	 * The ordinal of {@code JsonToken.NUMBER} in {@code JsonToken.values}.
	 */
	public static final int TOKEN_NUMBER = 6;

	/**
	 * The ordinal of {@code JsonToken.TRUE} in {@code JsonToken.values}.
	 */
	public static final int TOKEN_TRUE = 7;

	/**
	 * The ordinal of {@code JsonToken.FALSE} in {@code JsonToken.values}.
	 */
	public static final int TOKEN_FALSE = 8;

	/**
	 * The ordinal of {@code JsonToken.NULL} in {@code JsonToken.values}.
	 */
	public static final int TOKEN_NULL = 9;

	/**
	 * The ordinal of {@code JsonToken.EOF} in {@code JsonToken.values}.
	 */
	public static final int TOKEN_EOF = 10;
}
