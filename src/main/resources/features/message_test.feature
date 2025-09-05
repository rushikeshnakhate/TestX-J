Feature: Message Publishing and Consuming
  As a test engineer
  I want to test message operations
  So that I can verify messaging works properly

  Background:
    Given I have a Solace connection
    And I connect to Solace

  Scenario: Publish and consume text message
    Given I have a text message "Hello World"
    And I start consuming from topic "test/topic"
    When I publish the message to topic "test/topic"
    And I wait for messages for 5 seconds
    Then I should receive 1 message(s)
    And the received message should contain "Hello World"
    And the message should be of type "TEXT"

  Scenario: Publish and consume JSON message
    Given I have a JSON message:
      """
      {"id": 123, "name": "Test User"}
      """
    And I start consuming from queue "test/queue"
    When I publish the message to queue "test/queue"
    And I wait for messages for 5 seconds
    Then I should receive 1 message(s)
    And the received message should contain "Test User"
    And the message should be of type "JSON"
