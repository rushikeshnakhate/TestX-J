package org.example.handler;

import org.example.message.Message;
import org.example.message.MessageType;
import org.springframework.stereotype.Component;

/**
 * JSON message handler
 */
@Component
public class JsonMessageHandler implements MessageHandler {
    
    @Override
    public boolean canHandle(Message message) {
        return message.getType() == MessageType.JSON;
    }
    
    @Override
    public String process(Message message) {
        return message.getContentAsString();
    }
}
