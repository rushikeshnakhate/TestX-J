package org.example.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.example.solace.SolaceConnection;
import org.example.solace.SolaceProducer;
import org.example.solace.SolaceConsumer;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Connection management step definitions
 */
public class ConnectionSteps {
    
    @Autowired
    private SolaceConnection connection;
    
    @Autowired
    private SolaceProducer producer;
    
    @Autowired
    private SolaceConsumer consumer;
    
    @Given("I have a Solace connection")
    public void i_have_a_solace_connection() {
        assertNotNull(connection);
    }
    
    @When("I connect to Solace")
    public void i_connect_to_solace() throws Exception {
        connection.connect();
    }
    
    @When("I disconnect from Solace")
    public void i_disconnect_from_solace() {
        connection.disconnect();
    }
    
    @Then("I should be connected")
    public void i_should_be_connected() {
        assertTrue(connection.isConnected());
    }
    
    @Then("I should be disconnected")
    public void i_should_be_disconnected() {
        assertFalse(connection.isConnected());
    }
}
