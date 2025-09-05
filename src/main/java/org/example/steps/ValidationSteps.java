package org.example.steps;

import io.cucumber.java.en.Then;
import org.example.message.MessageType;
import org.example.solace.SolaceConsumer;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Message validation step definitions
 */
public class ValidationSteps {
    
    @Autowired
    private SolaceConsumer consumer;
    
    @Then("the message should be of type {string}")
    public void the_message_should_be_of_type(String messageType) {
        var messages = consumer.getReceivedMessages();
        assertNotNull(messages);
        assertFalse(messages.isEmpty());
        
        MessageType expectedType = MessageType.valueOf(messageType.toUpperCase());
        boolean found = messages.stream()
                .anyMatch(msg -> msg.message().getType() == expectedType);
        assertTrue(found);
    }
    
    @Then("the message should be from destination {string}")
    public void the_message_should_be_from_destination(String destination) {
        var messages = consumer.getReceivedMessages();
        assertNotNull(messages);
        assertFalse(messages.isEmpty());
        
        boolean found = messages.stream()
                .anyMatch(msg -> destination.equals(msg.destination()));
        assertTrue(found);
    }
    
    @Then("I should have no messages")
    public void i_should_have_no_messages() {
        var messages = consumer.getReceivedMessages();
        assertTrue(messages.isEmpty());
    }
    
    @Then("I clear all messages")
    public void i_clear_all_messages() {
        consumer.clearMessages();
    }
}
