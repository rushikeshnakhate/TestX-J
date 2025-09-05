package org.example;

import io.cucumber.junit.platform.engine.Constants;
import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

/**
 * JUnit-based Cucumber test runner with Spring Boot integration
 * This replaces the standalone TestRunner.java
 */
@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameter(key = Constants.GLUE_PROPERTY_NAME, value = "org.example.steps,org.example.config")
@ConfigurationParameter(key = Constants.PLUGIN_PROPERTY_NAME,
        value = "pretty,html:target/cucumber-reports/html,json:target/cucumber-reports/cucumber.json")
public class CucumberTestRunner {
    // No main method needed - JUnit will run this
}