package org.example.message;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * JSON message implementation
 */
public record JsonMessage(Object data) implements Message {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    
    @Override
    public MessageType getType() {
        return MessageType.JSON;
    }
    
    @Override
    public byte[] getContent() {
        try {
            return objectMapper.writeValueAsBytes(data);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize JSON", e);
        }
    }
    
    @Override
    public String getContentAsString() {
        try {
            return objectMapper.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize JSON", e);
        }
    }
}
