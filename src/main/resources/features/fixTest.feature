Feature: QuickFIX Message Processing
  As a test engineer
  I want to test QuickFIX message operations
  So that I can verify FIX messaging works properly

  Background:
    Given I have a Solace connection
    And I connect to Solace

  Scenario: Send and receive FIX Logon message
    Given I have a FIX Logon message with sender "SENDER" and target "TARGET"
    And I start consuming FIX messages from topic "fix/logon"
    When I publish the FIX message to topic "fix/logon"
    And I wait for FIX messages for 5 seconds
    Then I should receive 1 FIX message(s)
    And the received FIX message should have message type "A"
    And the received FIX message should have sender "SENDER"
    And the received FIX message should have target "TARGET"
    And the received FIX message should be valid

  Scenario: Send and receive FIX Logout message
    Given I have a FIX Logout message with sender "CLIENT1" and target "SERVER1"
    And I start consuming FIX messages from queue "fix/logout"
    When I publish the FIX message to queue "fix/logout"
    And I wait for FIX messages for 5 seconds
    Then I should receive 1 FIX message(s)
    And the received FIX message should have message type "5"
    And the received FIX message should have sender "CLIENT1"
    And the received FIX message should have target "SERVER1"
    And the received FIX message should contain "Normal logout"

  Scenario: Send and receive FIX Heartbeat message
    Given I have a FIX Heartbeat message with sender "HB_SENDER" and target "HB_TARGET"
    And I start consuming FIX messages from topic "fix/heartbeat"
    When I publish the FIX message to topic "fix/heartbeat"
    And I wait for FIX messages for 3 seconds
    Then I should receive 1 FIX message(s)
    And the received FIX message should have message type "0"
    And the received FIX message should be valid

  Scenario: Send and receive FIX Test Request
    Given I have a FIX Test Request with sender "TEST_SENDER" and target "TEST_TARGET" and test ID "TEST123"
    And I start consuming FIX messages from topic "fix/test"
    When I publish the FIX message to topic "fix/test"
    And I wait for FIX messages for 5 seconds
    Then I should receive 1 FIX message(s)
    And the received FIX message should have message type "1"
    And the received FIX message should contain "TEST123"

  Scenario: Send and receive FIX New Order Single
    Given I have a FIX New Order Single with:
      | senderCompID | ORDER_SENDER |
      | targetCompID | ORDER_TARGET |
      | clOrdID      | ORD123       |
      | symbol       | AAPL         |
      | side         | 1            |
      | quantity     | 100          |
      | price        | 150.50       |
    And I start consuming FIX messages from queue "fix/orders"
    When I publish the FIX message to queue "fix/orders"
    And I wait for FIX messages for 5 seconds
    Then I should receive 1 FIX message(s)
    And the received FIX message should have message type "D"
    And the received FIX message should contain "AAPL"
    And the received FIX message should contain "ORD123"

  Scenario: Send and receive FIX Execution Report
    Given I have a FIX Execution Report with:
      | senderCompID | EXEC_SENDER |
      | targetCompID | EXEC_TARGET |
      | orderID      | ORDER456    |
      | execID       | EXEC789     |
      | execType     | F           |
      | ordStatus    | 2           |
      | symbol       | MSFT        |
      | side         | 2           |
      | quantity     | 200         |
      | price        | 300.75      |
    And I start consuming FIX messages from topic "fix/executions"
    When I publish the FIX message to topic "fix/executions"
    And I wait for FIX messages for 5 seconds
    Then I should receive 1 FIX message(s)
    And the received FIX message should have message type "8"
    And the received FIX message should contain "MSFT"
    And the received FIX message should contain "ORDER456"

  Scenario: Send raw FIX message string
    Given I have a FIX message from raw string:
      """
      8=FIX.4.4|9=196|35=D|49=SENDER|56=TARGET|34=1|52=20231005-10:30:00.000|11=ORDER123|55=GOOGL|54=1|38=50|44=2500.00|40=2|59=0|60=20231005-10:30:00.000|10=123|
      """
    And I start consuming FIX messages from topic "fix/raw"
    When I publish the FIX message to topic "fix/raw"
    And I wait for FIX messages for 5 seconds
    Then I should receive 1 FIX message(s)
    And the received FIX message should contain "GOOGL"
    And the received FIX message should be valid

  Scenario: Multiple FIX message types
    Given I have a FIX Logon message with sender "MULTI_SENDER" and target "MULTI_TARGET"
    And I start consuming FIX messages from topic "fix/multi"
    When I publish the FIX message to topic "fix/multi"
    Given I have a FIX Heartbeat message with sender "MULTI_SENDER" and target "MULTI_TARGET"
    When I publish the FIX message to topic "fix/multi"
    And I wait for FIX messages for 5 seconds
    Then I should receive 2 FIX message(s)
    And I clear all messages