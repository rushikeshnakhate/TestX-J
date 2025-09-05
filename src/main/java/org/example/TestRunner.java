package org.example;

import io.cucumber.core.cli.Main;

/**
 * Main test runner for executing Cucumber tests
 */
public class TestRunner {
    
    public static void main(String[] args) {
        String[] cucumberArgs = {
            "--glue", "org.example.steps,org.example.config",
            "--plugin", "pretty,html:target/cucumber-reports/html,json:target/cucumber-reports/cucumber.json",
            "src/main/resources/features"
        };
        
        System.exit(Main.run(cucumberArgs));
    }
}
