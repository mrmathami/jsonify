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

import java.util.LinkedHashMap;

public final class JsonObject extends LinkedHashMap<@NotNull String, @NotNull JsonElement> implements JsonElement {
	public JsonObject() {
		super(16, 0.5f);
	}

	public @Nullable JsonElement putValue(@NotNull String key, @Nullable String value) {
		return put(key, value != null ? new JsonString(value) : JsonKeyword.NULL);
	}

	public @Nullable JsonElement putValue(@NotNull String key, boolean value) {
		return put(key, JsonKeyword.of(value));
	}

	public @Nullable JsonElement putValue(@NotNull String key, long value) {
		return put(key, new JsonNumber(value));
	}

	public @Nullable JsonElement putValue(@NotNull String key, double value) {
		return put(key, new JsonNumber(value));
	}
}
