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
import org.jetbrains.annotations.Nullable;

public final class JsonString implements JsonElement, JsonToken {
	private final @NotNull String value;

	public JsonString(@NotNull String value) {
		this.value = value;
	}

	public JsonString(char value) {
		this.value = String.valueOf(value);
	}

	@Override
	public boolean equals(@Nullable Object object) {
		return this == object || object instanceof JsonString && value.equals(object.toString());
	}

	@Override
	public int hashCode() {
		return value.hashCode();
	}

	@Override
	public @NotNull String toString() {
		return value;
	}

	public @Nullable Character toCharacter() {
		return value.length() == 1 ? value.charAt(0) : null;
	}
}
