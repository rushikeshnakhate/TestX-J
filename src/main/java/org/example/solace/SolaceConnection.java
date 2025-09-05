package org.example.solace;

import com.solacesystems.jcsmp.JCSMPException;
import com.solacesystems.jcsmp.JCSMPSession;
import com.solacesystems.jcsmp.JCSMPProperties;
import org.springframework.stereotype.Component;

/**
 * Solace connection management
 */
@Component
public class SolaceConnection {
    
    private final JCSMPProperties properties;
    private JCSMPSession session;
    private boolean connected = false;
    
    public SolaceConnection(JCSMPProperties properties) {
        this.properties = properties;
    }
    
    public void connect() throws JCSMPException {
        if (connected) {
            return;
        }
        
        session = com.solacesystems.jcsmp.JCSMPFactory.onlyInstance().createSession(properties);
        session.connect();
        connected = true;
    }
    
    public void disconnect() {
        if (!connected) {
            return;
        }
        
        try {
            if (session != null) {
                session.closeSession();
            }
        } catch (JCSMPException e) {
            // Log error but don't throw
        }
        connected = false;
    }
    
    public boolean isConnected() {
        return connected;
    }
    
    public JCSMPSession getSession() {
        return session;
    }
}
