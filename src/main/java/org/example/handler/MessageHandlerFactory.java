package org.example.handler;

import org.example.message.Message;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Message handler factory using strategy pattern
 */
@Component
public class MessageHandlerFactory {
    
    private final List<MessageHandler> handlers;
    
    public MessageHandlerFactory(List<MessageHandler> handlers) {
        this.handlers = handlers;
    }
    
    public MessageHandler getHandler(Message message) {
        return handlers.stream()
                .filter(handler -> handler.canHandle(message))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No handler found for message type: " + message.getType()));
    }
}
