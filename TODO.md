# TODO

- [x] Inspect current Playwright/Cucumber lifecycle implementation (Hooks + step logic).
- [ ] Update lifecycle so browser/page is fully quit after each JSON dataset record iteration.
- [ ] Ensure browser is re-launched for next dataset record (fresh browser per record).
- [x] Identify root cause: JSON iterations are inside LoginPageStepDefs, so Cucumber Hooks @After runs only once per scenario, not per record.
- [ ] Refactor: move JSON-iteration responsibility from LoginPageStepDefs to Cucumber record-level execution (scenario outline style / programmatic datapoints).
- [ ] Update feature file to run two records as two scenarios.
- [ ] Run Maven test and verify browser quits/relaunches per record.

