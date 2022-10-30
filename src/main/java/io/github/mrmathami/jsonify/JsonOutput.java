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
import java.math.BigDecimal;
import java.math.BigInteger;

public interface JsonOutput extends Closeable {
	/**
	 * Begin an array. Throws {@link JsonException} if this is unexpected.
	 */
	void beginArray() throws IOException;

	/**
	 * Begin an object. Throws {@link JsonException} if this is unexpected.
	 */
	void beginObject() throws IOException;

	/**
	 * End an array or an object. Throws {@link JsonException} if this is unexpected.
	 */
	void end() throws IOException;

	/**
	 * Write a name. Throws {@link JsonException} if this is unexpected.
	 */
	void name(@NotNull String name) throws IOException;

	/**
	 * Write a {@link JsonElement} value. Throws {@link JsonException} if this is unexpected.
	 */
	void value(@NotNull JsonElement element) throws IOException;

	/**
	 * Write a boolean value. Throws {@link JsonException} if this is unexpected.
	 */
	void valueBoolean(boolean value) throws IOException;

	/**
	 * Write a integer number value. Throws {@link JsonException} if this is unexpected.
	 */
	void valueNumber(long value) throws IOException;

	/**
	 * Write a decimal number value. Throws {@link JsonException} if this is unexpected.
	 */
	void valueNumber(double value) throws IOException;

	/**
	 * Write a big integer number value. Throws {@link JsonException} if this is unexpected.
	 */
	void valueNumber(@NotNull BigInteger value) throws IOException;

	/**
	 * Write a big decimal number value. Throws {@link JsonException} if this is unexpected.
	 */
	void valueNumber(@NotNull BigDecimal value) throws IOException;

	/**
	 * Write a string value. Throws {@link JsonException} if this is unexpected.
	 */
	void valueString(char value) throws IOException;

	/**
	 * Write a string value. Throws {@link JsonException} if this is unexpected.
	 */
	void valueString(@NotNull String value) throws IOException;

	/**
	 * Write a null value. Throws {@link JsonException} if this is unexpected.
	 */
	void valueNull() throws IOException;
}
