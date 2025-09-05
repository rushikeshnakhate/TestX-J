package org.example.config;

import io.cucumber.spring.CucumberContextConfiguration;
import org.example.TestApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

/**
 * Test configuration for Cucumber tests
 */
@CucumberContextConfiguration
@SpringBootTest
@ContextConfiguration(classes = {TestApplication.class})
public class TestConfiguration {
    // Configuration for Cucumber tests
}
