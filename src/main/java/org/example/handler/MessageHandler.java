package org.example.handler;

import org.example.message.Message;

/**
 * Message handler interface
 */
public interface MessageHandler {
    boolean canHandle(Message message);
    String process(Message message);
}
