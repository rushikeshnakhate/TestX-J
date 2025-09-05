package org.example.message;

/**
 * Binary message implementation
 */
public record BinaryMessage(byte[] content) implements Message {
    
    @Override
    public MessageType getType() {
        return MessageType.BINARY;
    }
    
    @Override
    public byte[] getContent() {
        return content;
    }
    
    @Override
    public String getContentAsString() {
        return new String(content);
    }
}
