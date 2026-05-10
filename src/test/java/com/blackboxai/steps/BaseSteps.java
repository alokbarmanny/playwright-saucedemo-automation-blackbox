package com.blackboxai.steps;

import com.microsoft.playwright.Page;

/**
 * Base class for all Cucumber step definition classes.
 * Centralizes shared access to the Playwright {@link Page}.
 */
public abstract class BaseSteps {

    protected final Hooks hooks;
    protected final Page page;

    protected BaseSteps(Hooks hooks) {
        this.hooks = hooks;
        this.page = hooks.getPage();
    }
}

