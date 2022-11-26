# jsonify

JSON reader/writer library. Currently, it is in production-ready stage. The API is stable, any change will come with proper deprecation and deprecation for removal notice at least 1 year before the removal of any API.

## Goal

- Be able to read/write JSON.
- Be 100% compliant to the standard.
- Be simple to maintain/develop.
- Be simple to use.
- Be really fast.

## Non-goal

- Be a serializer/deserializer library. If you need one, take a look at [Jackson](https://github.com/FasterXML/jackson), [Gson](https://github.com/google/gson) or something else.
- Throwing with useful location information on parsing error. If you need that, consider using a JSON validator. If reading non-standard JSON is a necessary feature, consider using other libraries.

## Usage note

### High level usage

An input JSON can be loaded and parsed by using `JsonElement element = JsonReader.read(reader);`. Similarly, the `JsonElement` can be saved back to JSON using `JsonWriter.write(writer, element)`.

There are a few type of `JsonElement`:

- `JsonArray` for array.
- `JsonObject` for object.
- `JsonString` for string.
- `JsonNumber` for number.
- `JsonKeyword` for boolean and null.

`JsonArray` and `JsonObject` are mutable; `JsonString`, `JsonNumber` and `JsonKeyword` are immutable.

Note that `JsonWriter::write` do check for circular references and will throw `JsonException` in that case.

### Low level usage

If you want more control on the reader/writer, you can use an instance of `JsonReader`/`JsonWriter` directly to load/save JSON from/to your own data structure. Moreover, when using an instance of `JsonReader` instead of `JsonReader::read`, you have ability to skip over the remaining content of an array or an object you are reading on, thus save times and memory.

## Todo

- [x] Read JSON input at low level.
- [x] Write JSON output at low level.
- [x] Parse JSON input to a high level data structure.
- [x] Write that data structure back to JSON output.
- [ ] Create benchmark to compare with other libraries.

## License

LGPL v3 or any later version

## Found a bug?

Feel free to open an issue.
