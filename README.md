
# jsonify
JSON reader/writer library. Currently, it is in ALPHA stage.

## Goal
- Be able to read/write JSON.
- Be 100% compliant to the standard.
- Be simple to maintain/develop.
- Be simple to use.
- Be really fast.

## Non-goal
- Be a serializer/deserializer library. If you need one, look for [Jackson](https://github.com/FasterXML/jackson), [Gson](https://github.com/google/gson) or something else.
- Throwing with useful location information on parsing error. Consider using a JSON validator, or if reading non-standard JSON input is a necessary feature, consider using other libraries.

## Todo
- [x] Read JSON input at low level.
- [ ] Write JSON output at low level.
- [x] Parse JSON input to a high level data structure.
- [ ] Write that data structure back to JSON output.
- [ ] Create benchmark to compare with other libraries.

## License
LGPL v3 or any later version

## Found a bug?
Feel free to open an issue.
