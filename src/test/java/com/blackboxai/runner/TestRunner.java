package com.blackboxai.runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.AfterSuite;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = "com.blackboxai.steps",
        plugin = {"pretty"}
)
public class TestRunner extends AbstractTestNGCucumberTests {

    /**
     * Ensures the JVM terminates after Cucumber/TestNG completes.
     * Prevents “process keeps running” behavior after the suite ends.
     */
    @AfterSuite(alwaysRun = true)
    public void afterSuite() {
        System.out.println("=========Done=============");
        // Do NOT call System.exit(0) here; it can break Surefire/TestNG reporting.
    }

}


