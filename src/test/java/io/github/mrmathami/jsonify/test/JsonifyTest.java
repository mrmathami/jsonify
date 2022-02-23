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

package io.github.mrmathami.jsonify.test;

import io.github.mrmathami.jsonify.JsonArray;
import io.github.mrmathami.jsonify.JsonElement;
import io.github.mrmathami.jsonify.JsonException;
import io.github.mrmathami.jsonify.Jsonify;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;

public class JsonifyTest {
	@Test
	public void bigInput() throws IOException {
		try (final InputStream inputStream = JsonifyLoadTest.class.getResourceAsStream("large-file.json")) {
			if (inputStream == null) Assertions.fail("Failed loading test resource!");
			try (final Reader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8)) {
				final JsonElement element = Jsonify.load(reader);
				Assertions.assertEquals(element.getClass(), JsonArray.class);
				Assertions.assertEquals(11351, ((JsonArray) element).size());
				final StringWriter writer = new StringWriter();
				Jsonify.save(writer, element);
				final StringReader anotherReader = new StringReader(writer.toString());
				final JsonElement anotherElement = Jsonify.load(anotherReader);
				Assertions.assertEquals(element, anotherElement);
			} catch (final JsonException e) {
				Assertions.fail(e);
			}
		}
	}
}
