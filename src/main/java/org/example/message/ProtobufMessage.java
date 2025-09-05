package org.example.message;

import com.google.protobuf.MessageLite;

/**
 * Protocol Buffer message implementation
 */
public record ProtobufMessage(MessageLite data) implements Message {
    
    @Override
    public MessageType getType() {
        return MessageType.PROTOBUF;
    }
    
    @Override
    public byte[] getContent() {
        return data.toByteArray();
    }
    
    @Override
    public String getContentAsString() {
        return data.toString();
    }
}
