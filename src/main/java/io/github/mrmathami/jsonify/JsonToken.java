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
	EOF
}
