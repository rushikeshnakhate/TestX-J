package org.example.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.example.fix.FixMessageBuilder;
import org.example.message.FixMessage;
import org.example.solace.SolaceProducer;
import org.example.solace.SolaceConsumer;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

/**
 * FIX message step definitions for Cucumber tests
 */
public class FixMessageSteps {

    @Autowired
    private FixMessageBuilder fixMessageBuilder;

    @Autowired
    private SolaceProducer producer;

    @Autowired
    private SolaceConsumer consumer;

    private FixMessage lastFixMessage;
    private List<SolaceConsumer.ReceivedMessage> receivedMessages;

    @Given("I have a FIX Logon message with sender {string} and target {string}")
    public void i_have_a_fix_logon_message(String senderCompID, String targetCompID) {
        lastFixMessage = fixMessageBuilder.createLogon(senderCompID, targetCompID, 30);
    }

    @Given("I have a FIX Logout message with sender {string} and target {string}")
    public void i_have_a_fix_logout_message(String senderCompID, String targetCompID) {
        lastFixMessage = fixMessageBuilder.createLogout(senderCompID, targetCompID, "Normal logout");
    }

    @Given("I have a FIX Heartbeat message with sender {string} and target {string}")
    public void i_have_a_fix_heartbeat_message(String senderCompID, String targetCompID) {
        lastFixMessage = fixMessageBuilder.createHeartbeat(senderCompID, targetCompID);
    }

    @Given("I have a FIX Test Request with sender {string} and target {string} and test ID {string}")
    public void i_have_a_fix_test_request(String senderCompID, String targetCompID, String testReqID) {
        lastFixMessage = fixMessageBuilder.createTestRequest(senderCompID, targetCompID, testReqID);
    }

    @Given("I have a FIX New Order Single with:")
    public void i_have_a_fix_new_order_single(io.cucumber.datatable.DataTable dataTable) {
        var data = dataTable.asMap();
        String senderCompID = data.get("senderCompID");
        String targetCompID = data.get("targetCompID");
        String clOrdID = data.get("clOrdID");
        String symbol = data.get("symbol");
        char side = data.get("side").charAt(0);
        double quantity = Double.parseDouble(data.get("quantity"));
        double price = Double.parseDouble(data.get("price"));

        lastFixMessage = fixMessageBuilder.createNewOrderSingle(
                senderCompID, targetCompID, clOrdID, symbol, side, quantity, price
        );
    }

    @Given("I have a FIX Execution Report with:")
    public void i_have_a_fix_execution_report(io.cucumber.datatable.DataTable dataTable) {
        var data = dataTable.asMap();
        String senderCompID = data.get("senderCompID");
        String targetCompID = data.get("targetCompID");
        String orderID = data.get("orderID");
        String execID = data.get("execID");
        char execType = data.get("execType").charAt(0);
        char ordStatus = data.get("ordStatus").charAt(0);
        String symbol = data.get("symbol");
        char side = data.get("side").charAt(0);
        double quantity = Double.parseDouble(data.get("quantity"));
        double price = Double.parseDouble(data.get("price"));

        lastFixMessage = fixMessageBuilder.createExecutionReport(
                senderCompID, targetCompID, orderID, execID, execType,
                ordStatus, symbol, side, quantity, price
        );
    }

    @Given("I have a FIX message from raw string:")
    public void i_have_a_fix_message_from_raw_string(String fixString) {
        lastFixMessage = fixMessageBuilder.createFromString(fixString);
    }

    @When("I publish the FIX message to topic {string}")
    public void i_publish_the_fix_message_to_topic(String topic) throws Exception {
        assertNotNull(lastFixMessage, "No FIX message created");
        producer.publishToTopic(topic, lastFixMessage);
    }

    @When("I publish the FIX message to queue {string}")
    public void i_publish_the_fix_message_to_queue(String queue) throws Exception {
        assertNotNull(lastFixMessage, "No FIX message created");
        producer.publishToQueue(queue, lastFixMessage);
    }

    @When("I start consuming FIX messages from topic {string}")
    public void i_start_consuming_fix_messages_from_topic(String topic) throws Exception {
        consumer.startConsumingFromTopic(topic);
    }

    @When("I start consuming FIX messages from queue {string}")
    public void i_start_consuming_fix_messages_from_queue(String queue) throws Exception {
        consumer.startConsumingFromQueue(queue);
    }

    @When("I wait for FIX messages for {int} seconds")
    public void i_wait_for_fix_messages_for_seconds(int seconds) throws InterruptedException {
        TimeUnit.SECONDS.sleep(seconds);
        receivedMessages = consumer.getReceivedMessages();
    }

    @Then("I should receive {int} FIX message\\(s\\)")
    public void i_should_receive_fix_messages(int count) {
        assertNotNull(receivedMessages, "No messages received");
        assertEquals(count, receivedMessages.size(), "Expected " + count + " messages but received " + receivedMessages.size());
    }

    @Then("the received FIX message should have message type {string}")
    public void the_received_fix_message_should_have_message_type(String expectedMsgType) {
        assertNotNull(receivedMessages, "No messages received");
        assertFalse(receivedMessages.isEmpty(), "No messages in the received list");

        boolean found = receivedMessages.stream()
                .filter(msg -> msg.message() instanceof FixMessage)
                .map(msg -> (FixMessage) msg.message())
                .anyMatch(fixMsg -> expectedMsgType.equals(fixMsg.getMsgType()));

        assertTrue(found, "No FIX message found with message type: " + expectedMsgType);
    }

    @Then("the received FIX message should have sender {string}")
    public void the_received_fix_message_should_have_sender(String expectedSender) {
        assertNotNull(receivedMessages, "No messages received");
        assertFalse(receivedMessages.isEmpty(), "No messages in the received list");

        boolean found = receivedMessages.stream()
                .filter(msg -> msg.message() instanceof FixMessage)
                .map(msg -> (FixMessage) msg.message())
                .anyMatch(fixMsg -> expectedSender.equals(fixMsg.getSenderCompID()));

        assertTrue(found, "No FIX message found with sender: " + expectedSender);
    }

    @Then("the received FIX message should have target {string}")
    public void the_received_fix_message_should_have_target(String expectedTarget) {
        assertNotNull(receivedMessages, "No messages received");
        assertFalse(receivedMessages.isEmpty(), "No messages in the received list");

        boolean found = receivedMessages.stream()
                .filter(msg -> msg.message() instanceof FixMessage)
                .map(msg -> (FixMessage) msg.message())
                .anyMatch(fixMsg -> expectedTarget.equals(fixMsg.getTargetCompID()));

        assertTrue(found, "No FIX message found with target: " + expectedTarget);
    }

    @Then("the received FIX message should be valid")
    public void the_received_fix_message_should_be_valid() {
        assertNotNull(receivedMessages, "No messages received");
        assertFalse(receivedMessages.isEmpty(), "No messages in the received list");

        boolean found = receivedMessages.stream()
                .filter(msg -> msg.message() instanceof FixMessage)
                .map(msg -> (FixMessage) msg.message())
                .anyMatch(FixMessage::isValid);

        assertTrue(found, "No valid FIX message found");
    }

    @Then("the received FIX message should contain {string}")
    public void the_received_fix_message_should_contain(String expectedContent) {
        assertNotNull(receivedMessages, "No messages received");
        assertFalse(receivedMessages.isEmpty(), "No messages in the received list");

        boolean found = receivedMessages.stream()
                .anyMatch(msg -> msg.message().getContentAsString().contains(expectedContent));

        assertTrue(found, "No message found containing: " + expectedContent);
    }
}