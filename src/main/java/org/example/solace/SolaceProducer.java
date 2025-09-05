package org.example.solace;

import com.solacesystems.jcsmp.*;
import org.example.message.Message;
import org.springframework.stereotype.Component;

/**
 * Solace message producer
 */
@Component
public class SolaceProducer {
    
    private final SolaceConnection connection;
    private XMLMessageProducer producer;
    
    public SolaceProducer(SolaceConnection connection) {
        this.connection = connection;
    }
    
    public void publishToTopic(String topicName, Message message) throws JCSMPException {
        if (!connection.isConnected()) {
            throw new JCSMPException("Not connected to Solace");
        }
        
        Topic topic = JCSMPFactory.onlyInstance().createTopic(topicName);
        BytesXMLMessage solaceMessage = createSolaceMessage(message);
        
        if (producer == null) {
            producer = connection.getSession().getMessageProducer(new JCSMPStreamingPublishEventHandler() {
                @Override
                public void responseReceived(String messageID) {
                    // Handle response
                }
                
                @Override
                public void handleError(String messageID, JCSMPException e, long timestamp) {
                    // Handle error
                }
            });
        }
        
        producer.send(solaceMessage, topic);
    }
    
    public void publishToQueue(String queueName, Message message) throws JCSMPException {
        if (!connection.isConnected()) {
            throw new JCSMPException("Not connected to Solace");
        }
        
        Queue queue = JCSMPFactory.onlyInstance().createQueue(queueName);
        BytesXMLMessage solaceMessage = createSolaceMessage(message);
        
        if (producer == null) {
            producer = connection.getSession().getMessageProducer(new JCSMPStreamingPublishEventHandler() {
                @Override
                public void responseReceived(String messageID) {
                    // Handle response
                }
                
                @Override
                public void handleError(String messageID, JCSMPException e, long timestamp) {
                    // Handle error
                }
            });
        }
        
        producer.send(solaceMessage, queue);
    }
    
    private BytesXMLMessage createSolaceMessage(Message message) {
        if (message.getType().name().equals("TEXT") || message.getType().name().equals("JSON")) {
            com.solacesystems.jcsmp.TextMessage textMessage = JCSMPFactory.onlyInstance().createMessage(com.solacesystems.jcsmp.TextMessage.class);
            textMessage.setText(message.getContentAsString());
            return textMessage;
        } else {
            BytesMessage bytesMessage = JCSMPFactory.onlyInstance().createMessage(BytesMessage.class);
            bytesMessage.setData(message.getContent());
            return bytesMessage;
        }
    }
}