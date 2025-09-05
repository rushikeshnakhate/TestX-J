package org.example.handler;

import org.example.message.Message;
import org.example.message.MessageType;
import org.example.message.FixMessage;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * QuickFIX message handler
 */
@Component
public class FixMessageHandler implements MessageHandler {

    private static final Logger logger = LoggerFactory.getLogger(FixMessageHandler.class);

    @Override
    public boolean canHandle(Message message) {
        return message.getType() == MessageType.FIX;
    }

    @Override
    public String process(Message message) {
        if (!(message instanceof FixMessage fixMessage)) {
            throw new IllegalArgumentException("Expected FixMessage, got: " + message.getClass().getSimpleName());
        }

        try {
            // Log FIX message details
            logger.info("Processing FIX message - Type: {}, Sender: {}, Target: {}",
                    fixMessage.getMsgType(),
                    fixMessage.getSenderCompID(),
                    fixMessage.getTargetCompID());

            // Validate the message
            if (!fixMessage.isValid()) {
                logger.warn("Invalid FIX message received: {}", fixMessage.getContentAsString());
                return "INVALID_FIX_MESSAGE";
            }

            // Process based on message type
            return processFixMessage(fixMessage);

        } catch (Exception e) {
            logger.error("Error processing FIX message: {}", e.getMessage(), e);
            return "ERROR_PROCESSING_FIX_MESSAGE: " + e.getMessage();
        }
    }

    /**
     * Process FIX message based on its type
     *
     * @param fixMessage The FIX message to process
     * @return Processing result
     */
    private String processFixMessage(FixMessage fixMessage) {
        String msgType = fixMessage.getMsgType();

        return switch (msgType) {
            case "A" -> processLogon(fixMessage);
            case "5" -> processLogout(fixMessage);
            case "0" -> processHeartbeat(fixMessage);
            case "1" -> processTestRequest(fixMessage);
            case "2" -> processResendRequest(fixMessage);
            case "4" -> processSequenceReset(fixMessage);
            case "D" -> processNewOrderSingle(fixMessage);
            case "8" -> processExecutionReport(fixMessage);
            case "G" -> processOrderCancelRequest(fixMessage);
            case "F" -> processOrderCancelReject(fixMessage);
            case "9" -> processOrderCancelReject(fixMessage);
            default -> processGenericMessage(fixMessage);
        };
    }

    private String processLogon(FixMessage fixMessage) {
        logger.info("Processing Logon message from: {}", fixMessage.getSenderCompID());
        return "LOGON_PROCESSED";
    }

    private String processLogout(FixMessage fixMessage) {
        logger.info("Processing Logout message from: {}", fixMessage.getSenderCompID());
        return "LOGOUT_PROCESSED";
    }

    private String processHeartbeat(FixMessage fixMessage) {
        logger.debug("Processing Heartbeat message from: {}", fixMessage.getSenderCompID());
        return "HEARTBEAT_PROCESSED";
    }

    private String processTestRequest(FixMessage fixMessage) {
        logger.info("Processing Test Request from: {}", fixMessage.getSenderCompID());
        return "TEST_REQUEST_PROCESSED";
    }

    private String processResendRequest(FixMessage fixMessage) {
        logger.info("Processing Resend Request from: {}", fixMessage.getSenderCompID());
        return "RESEND_REQUEST_PROCESSED";
    }

    private String processSequenceReset(FixMessage fixMessage) {
        logger.info("Processing Sequence Reset from: {}", fixMessage.getSenderCompID());
        return "SEQUENCE_RESET_PROCESSED";
    }

    private String processNewOrderSingle(FixMessage fixMessage) {
        logger.info("Processing New Order Single from: {}", fixMessage.getSenderCompID());
        return "NEW_ORDER_SINGLE_PROCESSED";
    }

    private String processExecutionReport(FixMessage fixMessage) {
        logger.info("Processing Execution Report from: {}", fixMessage.getSenderCompID());
        return "EXECUTION_REPORT_PROCESSED";
    }

    private String processOrderCancelRequest(FixMessage fixMessage) {
        logger.info("Processing Order Cancel Request from: {}", fixMessage.getSenderCompID());
        return "ORDER_CANCEL_REQUEST_PROCESSED";
    }

    private String processOrderCancelReject(FixMessage fixMessage) {
        logger.info("Processing Order Cancel Reject from: {}", fixMessage.getSenderCompID());
        return "ORDER_CANCEL_REJECT_PROCESSED";
    }

    private String processGenericMessage(FixMessage fixMessage) {
        logger.info("Processing generic FIX message type: {} from: {}",
                fixMessage.getMsgType(), fixMessage.getSenderCompID());
        return "GENERIC_FIX_MESSAGE_PROCESSED";
    }
}