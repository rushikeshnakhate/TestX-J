Feature: Solace Connection Management
  As a test engineer
  I want to test Solace connection functionality
  So that I can verify connections work properly

  Scenario: Connect to Solace
    Given I have a Solace connection
    When I connect to Solace
    Then I should be connected

  Scenario: Disconnect from Solace
    Given I have a Solace connection
    And I connect to Solace
    When I disconnect from Solace
    Then I should be disconnected
