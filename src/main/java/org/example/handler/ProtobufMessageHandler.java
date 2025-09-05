package org.example.handler;

import org.example.message.Message;
import org.example.message.MessageType;
import org.springframework.stereotype.Component;

/**
 * Protocol Buffer message handler
 */
@Component
public class ProtobufMessageHandler implements MessageHandler {
    
    @Override
    public boolean canHandle(Message message) {
        return message.getType() == MessageType.PROTOBUF;
    }
    
    @Override
    public String process(Message message) {
        return message.getContentAsString();
    }
}
