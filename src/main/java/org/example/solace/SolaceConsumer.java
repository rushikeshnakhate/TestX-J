package org.example.solace;

import com.solacesystems.jcsmp.*;
import org.example.message.Message;
import org.example.message.TextMessage;
import org.example.message.BinaryMessage;
import org.springframework.stereotype.Component;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.List;

/**
 * Solace message consumer
 */
@Component
public class SolaceConsumer {
    
    private final SolaceConnection connection;
    private XMLMessageConsumer consumer;
    private final List<ReceivedMessage> receivedMessages = new CopyOnWriteArrayList<>();
    
    public SolaceConsumer(SolaceConnection connection) {
        this.connection = connection;
    }
    
    public void startConsumingFromTopic(String topicName) throws JCSMPException {
        if (!connection.isConnected()) {
            throw new JCSMPException("Not connected to Solace");
        }
        
        Topic topic = JCSMPFactory.onlyInstance().createTopic(topicName);
        consumer = connection.getSession().getMessageConsumer(new MessageListener());
        consumer.addSubscription(topic);
        consumer.start();
    }
    
    public void startConsumingFromQueue(String queueName) throws JCSMPException {
        if (!connection.isConnected()) {
            throw new JCSMPException("Not connected to Solace");
        }
        
        Queue queue = JCSMPFactory.onlyInstance().createQueue(queueName);
        consumer = connection.getSession().getMessageConsumer(new MessageListener());
        consumer.addSubscription(queue);
        consumer.start();
    }
    
    public void stopConsuming() throws JCSMPException {
        if (consumer != null) {
            consumer.stop();
        }
    }
    
    public List<ReceivedMessage> getReceivedMessages() {
        return List.copyOf(receivedMessages);
    }
    
    public void clearMessages() {
        receivedMessages.clear();
    }
    
    private class MessageListener implements XMLMessageListener {
        @Override
        public void onReceive(BytesXMLMessage message) {
            Message msg = createMessage(message);
            receivedMessages.add(new ReceivedMessage(msg, message.getDestination().getName()));
        }
        
        @Override
        public void onException(JCSMPException e) {
            // Handle exception
        }
        
        private Message createMessage(BytesXMLMessage message) {
            if (message instanceof TextMessage) {
                return new TextMessage(((TextMessage) message).getText());
            } else {
                return new BinaryMessage(message.getData());
            }
        }
    }
    
    public record ReceivedMessage(Message message, String destination) {}
}