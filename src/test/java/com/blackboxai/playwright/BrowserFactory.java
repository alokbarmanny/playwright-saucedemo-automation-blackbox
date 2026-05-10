package com.blackboxai.playwright;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public final class BrowserFactory {
    private BrowserFactory() {}

    public static PlaywrightContext create(String browserName, boolean headless) {
        Playwright playwright = Playwright.create();

        BrowserType browserType = playwright.chromium();
        if ("firefox".equalsIgnoreCase(browserName)) {
            browserType = playwright.firefox();
        } else if ("webkit".equalsIgnoreCase(browserName)) {
            browserType = playwright.webkit();
        }

        Browser browser = browserType.launch(new BrowserType.LaunchOptions().setHeadless(headless));
        Page page = browser.newPage();

        return new PlaywrightContext(playwright, browser, page);
    }
}


