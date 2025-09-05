package org.example.message;

/**
 * Base message interface
 */
public interface Message {
    MessageType getType();
    byte[] getContent();
    String getContentAsString();
}
