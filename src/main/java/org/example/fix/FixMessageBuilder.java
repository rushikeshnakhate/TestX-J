package org.example.fix;

import org.example.message.FixMessage;
import org.springframework.stereotype.Component;
import quickfix.field.*;
import quickfix.fix44.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Builder class for creating QuickFIX messages
 */
@Component
public class FixMessageBuilder {

    private static final DateTimeFormatter TIMESTAMP_FORMAT = DateTimeFormatter.ofPattern("yyyyMMdd-HH:mm:ss.SSS");

    /**
     * Create a Logon message
     */
    public FixMessage createLogon(String senderCompID, String targetCompID, int heartbeatInterval) {
        Logon logon = new Logon();

        // Set header fields
        logon.getHeader().setString(SenderCompID.FIELD, senderCompID);
        logon.getHeader().setString(TargetCompID.FIELD, targetCompID);
        logon.getHeader().setString(SendingTime.FIELD, getCurrentTimestamp());

        // Set body fields
        logon.setInt(HeartBtInt.FIELD, heartbeatInterval);
        logon.setString(EncryptMethod.FIELD, "0"); // None

        return new FixMessage(logon);
    }

    /**
     * Create a Logout message
     */
    public FixMessage createLogout(String senderCompID, String targetCompID, String text) {
        Logout logout = new Logout();

        // Set header fields
        logout.getHeader().setString(SenderCompID.FIELD, senderCompID);
        logout.getHeader().setString(TargetCompID.FIELD, targetCompID);
        logout.getHeader().setString(SendingTime.FIELD, getCurrentTimestamp());

        // Set body fields
        if (text != null && !text.isEmpty()) {
            logout.setString(Text.FIELD, text);
        }

        return new FixMessage(logout);
    }

    /**
     * Create a Heartbeat message
     */
    public FixMessage createHeartbeat(String senderCompID, String targetCompID) {
        Heartbeat heartbeat = new Heartbeat();

        // Set header fields
        heartbeat.getHeader().setString(SenderCompID.FIELD, senderCompID);
        heartbeat.getHeader().setString(TargetCompID.FIELD, targetCompID);
        heartbeat.getHeader().setString(SendingTime.FIELD, getCurrentTimestamp());

        return new FixMessage(heartbeat);
    }

    /**
     * Create a Test Request message
     */
    public FixMessage createTestRequest(String senderCompID, String targetCompID, String testReqID) {
        TestRequest testRequest = new TestRequest();

        // Set header fields
        testRequest.getHeader().setString(SenderCompID.FIELD, senderCompID);
        testRequest.getHeader().setString(TargetCompID.FIELD, targetCompID);
        testRequest.getHeader().setString(SendingTime.FIELD, getCurrentTimestamp());

        // Set body fields
        testRequest.setString(TestReqID.FIELD, testReqID);

        return new FixMessage(testRequest);
    }

    /**
     * Create a New Order Single message
     */
    public FixMessage createNewOrderSingle(String senderCompID, String targetCompID,
                                           String clOrdID, String symbol, char side,
                                           double quantity, double price) {
        NewOrderSingle newOrder = new NewOrderSingle();

        // Set header fields
        newOrder.getHeader().setString(SenderCompID.FIELD, senderCompID);
        newOrder.getHeader().setString(TargetCompID.FIELD, targetCompID);
        newOrder.getHeader().setString(SendingTime.FIELD, getCurrentTimestamp());

        // Set body fields
        newOrder.setString(ClOrdID.FIELD, clOrdID);
        newOrder.setString(Symbol.FIELD, symbol);
        newOrder.setChar(Side.FIELD, side); // 1=Buy, 2=Sell
        newOrder.setDouble(OrderQty.FIELD, quantity);
        newOrder.setDouble(Price.FIELD, price);
        newOrder.setChar(OrdType.FIELD, '2'); // Limit order
        newOrder.setChar(TimeInForce.FIELD, '0'); // Day
        newOrder.setString(TransactTime.FIELD, getCurrentTimestamp());

        return new FixMessage(newOrder);
    }

    /**
     * Create an Execution Report message
     */
    public FixMessage createExecutionReport(String senderCompID, String targetCompID,
                                            String orderID, String execID, char execType,
                                            char ordStatus, String symbol, char side,
                                            double quantity, double price) {
        ExecutionReport execReport = new ExecutionReport();

        // Set header fields
        execReport.getHeader().setString(SenderCompID.FIELD, senderCompID);
        execReport.getHeader().setString(TargetCompID.FIELD, targetCompID);
        execReport.getHeader().setString(SendingTime.FIELD, getCurrentTimestamp());

        // Set body fields
        execReport.setString(OrderID.FIELD, orderID);
        execReport.setString(ExecID.FIELD, execID);
        execReport.setChar(ExecType.FIELD, execType);
        execReport.setChar(OrdStatus.FIELD, ordStatus);
        execReport.setString(Symbol.FIELD, symbol);
        execReport.setChar(Side.FIELD, side);
        execReport.setDouble(OrderQty.FIELD, quantity);
        execReport.setDouble(Price.FIELD, price);
        execReport.setDouble(LastQty.FIELD, quantity);
        execReport.setDouble(LastPx.FIELD, price);
        execReport.setDouble(CumQty.FIELD, quantity);
        execReport.setDouble(AvgPx.FIELD, price);

        return new FixMessage(execReport);
    }

    /**
     * Create a generic FIX message from raw string
     */
    public FixMessage createFromString(String fixString) {
        try {
            return FixMessage.fromString(fixString);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create FIX message from string: " + fixString, e);
        }
    }

    /**
     * Get current timestamp in FIX format
     */
    private String getCurrentTimestamp() {
        return LocalDateTime.now().format(TIMESTAMP_FORMAT);
    }
}