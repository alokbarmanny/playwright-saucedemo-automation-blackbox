package com.blackboxai.playwright;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public final class PlaywrightContext implements AutoCloseable {
    private final Playwright playwright;
    private final Browser browser;
    private final Page page;

    public PlaywrightContext(Playwright playwright, Browser browser, Page page) {
        this.playwright = playwright;
        this.browser = browser;
        this.page = page;
    }

    public Page page() {
        return page;
    }

    @Override
    public void close() {
        if (page != null) {
            page.close();
        }
        if (browser != null) {
            browser.close();
        }
        if (playwright != null) {
            playwright.close();
        }
    }
}

