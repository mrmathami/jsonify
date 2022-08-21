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

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

public final class Jsonify {
	private Jsonify() {
	}

	/**
	 * Load input json to JSON element.
	 */
	public static @NotNull JsonElement load(@NotNull Reader reader) throws IOException, JsonException {
		return JsonLoader.load(reader);
	}

	//========================================

	/**
	 * Save JSON element to output json.
	 */
	public static void save(@NotNull Writer writer, @NotNull JsonElement element) throws IOException, JsonException {
		JsonSaver.save(writer, element);
	}
}
