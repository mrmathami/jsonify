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

public final class JsonPrimitive implements JsonElement {
	public static final @NotNull JsonPrimitive TRUE = new JsonPrimitive("true");
	public static final @NotNull JsonPrimitive FALSE = new JsonPrimitive("false");
	public static final @NotNull JsonPrimitive NULL = new JsonPrimitive("null");

	private final @NotNull String string;

	private JsonPrimitive(@NotNull String string) {
		this.string = string;
	}

	public static JsonPrimitive of(@Nullable Object object) {
		if (object == null) return NULL;
		if (object == Boolean.TRUE) return TRUE;
		if (object == Boolean.FALSE) return FALSE;
		if (object instanceof JsonPrimitive) return (JsonPrimitive) object;
		throw new IllegalArgumentException();
	}

	public static JsonPrimitive of(boolean bool) {
		return bool ? TRUE : FALSE;
	}

	@Override
	public @NotNull String toString() {
		return string;
	}
}
