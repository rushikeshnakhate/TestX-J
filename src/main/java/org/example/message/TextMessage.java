package org.example.message;

/**
 * Text message implementation
 */
public record TextMessage(String content) implements Message {
    
    @Override
    public MessageType getType() {
        return MessageType.TEXT;
    }
    
    @Override
    public byte[] getContent() {
        return content.getBytes();
    }
    
    @Override
    public String getContentAsString() {
        return content;
    }
}
