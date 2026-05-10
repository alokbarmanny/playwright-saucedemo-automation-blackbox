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
        // env/<env>/env.properties located under src/test/resources/env/<env>/env.properties
        // Ensure we resolve it relative to the project root when running via Maven.
        String envPropsPath = "src/test/resources/env/" + envName + "/env.properties";

        baseUrl = propertyReader.getString(envPropsPath, "base.url", null);
        browserName = propertyReader.getString(envPropsPath, "browser.name", "chromium");

        // env/<env>/env.properties overrides application.properties.
        String headlessStr = propertyReader.getString(envPropsPath, "execution.headless", "false");
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

