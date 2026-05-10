# TODO

## Completed
- Added `PropertyReader` utility to read environment `.properties` files.
- Added `JsonReader` utility to read JSON files.
- Refactored `Hooks` to use `PropertyReader` for `env/<env>/env.properties`.

## Next
- Add `DatabaseReader` and `ExcelReader` under `src/test/java/com/blackboxai/utils/readers/`.
- (Optional) Add a common reader interface and shared exception handling/logging.
- Add utility methods for JSON path retrieval if needed.

## Completed
- Wired `execution.headless` flag from `env/<env>/env.properties` into Playwright browser launch (removed hardcoded headless=true). 


