package org.example.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.example.message.*;
import org.example.solace.SolaceProducer;
import org.example.solace.SolaceConsumer;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Message handling step definitions
 */
public class MessageSteps {
    
    @Autowired
    private SolaceProducer producer;
    
    @Autowired
    private SolaceConsumer consumer;
    
    private Message lastMessage;
    private List<SolaceConsumer.ReceivedMessage> receivedMessages;
    
    @Given("I have a text message {string}")
    public void i_have_a_text_message(String content) {
        lastMessage = new TextMessage(content);
    }
    
    @Given("I have a JSON message:")
    public void i_have_a_json_message(String jsonContent) {
        lastMessage = new JsonMessage(jsonContent);
    }
    
    @When("I publish the message to topic {string}")
    public void i_publish_the_message_to_topic(String topic) throws Exception {
        producer.publishToTopic(topic, lastMessage);
    }
    
    @When("I publish the message to queue {string}")
    public void i_publish_the_message_to_queue(String queue) throws Exception {
        producer.publishToQueue(queue, lastMessage);
    }
    
    @When("I start consuming from topic {string}")
    public void i_start_consuming_from_topic(String topic) throws Exception {
        consumer.startConsumingFromTopic(topic);
    }
    
    @When("I start consuming from queue {string}")
    public void i_start_consuming_from_queue(String queue) throws Exception {
        consumer.startConsumingFromQueue(queue);
    }
    
    @When("I wait for messages for {int} seconds")
    public void i_wait_for_messages_for_seconds(int seconds) throws InterruptedException {
        TimeUnit.SECONDS.sleep(seconds);
        receivedMessages = consumer.getReceivedMessages();
    }
    
    @Then("I should receive {int} message\\(s\\)")
    public void i_should_receive_messages(int count) {
        assertNotNull(receivedMessages);
        assertEquals(count, receivedMessages.size());
    }
    
    @Then("the received message should contain {string}")
    public void the_received_message_should_contain(String content) {
        assertNotNull(receivedMessages);
        assertFalse(receivedMessages.isEmpty());
        
        boolean found = receivedMessages.stream()
                .anyMatch(msg -> msg.message().getContentAsString().contains(content));
        assertTrue(found);
    }
}
