package com.blackboxai.utils.readers;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Provides a deterministic index for scenarios that consume array-based JSON
 * test data.
 *
 * Since Cucumber/TestNG integration in this project currently runs the scenario
 * only once,
 * we use a global counter so that multiple records from the same JSON array can
 * be exercised
 * in sequence.
 */
public final class ScenarioDataIndexProvider {

    private static final AtomicInteger COUNTER = new AtomicInteger(0);

    private ScenarioDataIndexProvider() {
    }

    public static int nextIndex() {
        return COUNTER.getAndIncrement();
    }
}
