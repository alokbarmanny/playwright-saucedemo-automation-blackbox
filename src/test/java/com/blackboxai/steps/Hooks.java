package com.blackboxai.steps;

import com.blackboxai.playwright.BrowserFactory;
import com.blackboxai.playwright.PlaywrightContext;
import com.blackboxai.utils.readers.PropertyReader;
import com.microsoft.playwright.Page;
import io.cucumber.java.After;
import io.cucumber.java.Before;

public class Hooks {
    private static final ThreadLocal<PlaywrightContext> CONTEXT = new ThreadLocal<>();
    private static final ThreadLocal<Page> PAGE = new ThreadLocal<>();

    public Hooks() {
    }

    private String baseUrl;
    private String envName;
    private String browserName;

    @Before
    public void setUp() {
        envName = System.getProperty("env", "dev");

        PropertyReader propertyReader = new PropertyReader();
        // env/<env>/env.properties located under
        // src/test/resources/env/<env>/env.properties
        // We keep passing the filesystem-style path; PropertyReader will fall back to
        // classpath.
        String envPropsPath = "src/test/resources/env/" + envName + "/env.properties";

        // If the selected env file is missing, fall back to application.properties
        // defaults.
        String baseUrlFromEnv = propertyReader.getString(envPropsPath, "base.url", null);
        baseUrl = baseUrlFromEnv != null ? baseUrlFromEnv
                : propertyReader.getString("application.properties", "base.url", "https://www.saucedemo.com/");

        String browserFromEnv = propertyReader.getString(envPropsPath, "browser.name", null);
        browserName = browserFromEnv != null ? browserFromEnv
                : propertyReader.getString("application.properties", "browser.name", "chromium");

        String headlessStr = propertyReader.getString(envPropsPath, "execution.headless", null);
        if (headlessStr == null) {
            headlessStr = propertyReader.getString("application.properties", "execution.headless", "false");
        }

        boolean headless = Boolean.parseBoolean(headlessStr);

        PlaywrightContext ctx = BrowserFactory.create(browserName, headless);

        CONTEXT.set(ctx);

        Page p = ctx.page();
        PAGE.set(p);

        p.navigate(baseUrl);
    }

    public Page getPage() {
        return PAGE.get();
    }

    @After
    public void tearDown() {
        PlaywrightContext ctx = CONTEXT.get();
        if (ctx != null) {
            ctx.close();
        }
        CONTEXT.remove();
        PAGE.remove();
    }

}
