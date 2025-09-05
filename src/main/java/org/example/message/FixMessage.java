package org.example.message;

import quickfix.Message;
import quickfix.InvalidMessage;

/**
 * QuickFIX message implementation
 */
public record FixMessage(Message fixMessage) implements org.example.message.Message {

    @Override
    public MessageType getType() {
        return MessageType.FIX;
    }

    @Override
    public byte[] getContent() {
        return fixMessage.toString().getBytes();
    }

    @Override
    public String getContentAsString() {
        return fixMessage.toString();
    }

    /**
     * Get the underlying QuickFIX message
     *
     * @return QuickFIX Message object
     */
    public Message getFixMessage() {
        return fixMessage;
    }

    /**
     * Get message type (35=MsgType)
     *
     * @return FIX message type
     */
    public String getMsgType() {
        try {
            return fixMessage.getHeader().getString(quickfix.field.MsgType.FIELD);
        } catch (Exception e) {
            return "Unknown";
        }
    }

    /**
     * Get sender comp ID (49=SenderCompID)
     *
     * @return Sender Company ID
     */
    public String getSenderCompID() {
        try {
            return fixMessage.getHeader().getString(quickfix.field.SenderCompID.FIELD);
        } catch (Exception e) {
            return "Unknown";
        }
    }

    /**
     * Get target comp ID (56=TargetCompID)
     *
     * @return Target Company ID
     */
    public String getTargetCompID() {
        try {
            return fixMessage.getHeader().getString(quickfix.field.TargetCompID.FIELD);
        } catch (Exception e) {
            return "Unknown";
        }
    }

    /**
     * Factory method to create FixMessage from string
     *
     * @param fixString FIX message as string
     * @return FixMessage instance
     * @throws InvalidMessage if the FIX string is invalid
     */
    public static FixMessage fromString(String fixString) throws InvalidMessage {
        Message message = new Message(fixString);
        return new FixMessage(message);
    }

    /**
     * Validate if the FIX message is well-formed
     *
     * @return true if valid, false otherwise
     */
    public boolean isValid() {
        try {
            // Basic validation - check if we can access standard header fields
            fixMessage.getHeader().getString(quickfix.field.MsgType.FIELD);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}