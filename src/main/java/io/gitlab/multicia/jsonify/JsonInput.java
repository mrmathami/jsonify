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

import java.io.Closeable;
import java.io.IOException;

public interface JsonInput extends Closeable {
	/**
	 * Parse and return the next token. This method will not skip over the end of an array or an object, instead it
	 * returns {@link JsonTokens#ARRAY_END} or {@link JsonTokens#OBJECT_END} repeatedly until {@link #endStructure()} is
	 * called. Similarly, this method also return {@link JsonTokens#EOF} repeatedly whem.
	 *
	 * @throws JsonIOException Throws if there is any error while parsing input JSON.
	 * @throws IOException     Throws if there is any error while reading input JSON.
	 */
	@NotNull JsonToken nextToken() throws IOException;

	/**
	 * Skip over the end of an array or an object. This method will also skip over any unread tokens before
	 * {@link JsonTokens#ARRAY_END} or {@link JsonTokens#OBJECT_END}.
	 *
	 * @throws IllegalStateException Throws if the parser is not currently inside an array or an object.
	 * @throws JsonIOException Throws if there is any error while parsing input JSON.
	 * @throws IOException Throws if there is any error while reading input JSON.
	 */
	void endStructure() throws IOException;

	/**
	 * Parse a structure based on the previous token and return the corresponding {@link JsonElement}, ot throws
	 * {@link IllegalStateException} if the previous token is not the beginning of a structure. The default behaviour
	 * is:
	 * <ul>
	 *     <li>If the previous token is {@link JsonTokens#ARRAY_BEGIN}, this method returns a {@link JsonArray} and
	 *         skip over {@link JsonTokens#ARRAY_END} automatically.</li>
	 *     <li>If the previous token is {@link JsonTokens#OBJECT_BEGIN}, this method returns a {@link JsonObject} and
	 *         skip over {@link JsonTokens#OBJECT_END} automatically.</li>
	 *     <li>If the previous token is anything else, this methods throws {@link IllegalStateException}.</li>
	 * </ul>
	 *
	 * @throws IllegalStateException Throws if the previous token is not the beginning of a structure..
	 * @throws JsonIOException Throws if there is any error while parsing input JSON.
	 * @throws IOException Throws if there is any error while reading input JSON.
	 */
	@NotNull JsonElement parseStructure() throws IOException;
}
