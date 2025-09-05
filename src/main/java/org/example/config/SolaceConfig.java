package org.example.config;

import com.solacesystems.jcsmp.JCSMPProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Solace configuration
 */
@Configuration
public class SolaceConfig {
    
    @Bean
    public JCSMPProperties jcsmpProperties(SolaceProperties properties) {
        JCSMPProperties jcsmpProperties = new JCSMPProperties();
        
        jcsmpProperties.setProperty(JCSMPProperties.HOST, properties.host());
        jcsmpProperties.setProperty(JCSMPProperties.USERNAME, properties.username());
        jcsmpProperties.setProperty(JCSMPProperties.PASSWORD, properties.password());
        jcsmpProperties.setProperty(JCSMPProperties.VPN_NAME, properties.vpn());
        jcsmpProperties.setProperty(JCSMPProperties.CLIENT_NAME, properties.clientName());
        
        jcsmpProperties.setProperty(JCSMPProperties.CONNECT_TIMEOUT_MILLIS, properties.connectionTimeout());
        jcsmpProperties.setProperty(JCSMPProperties.CONNECT_RETRIES, properties.connectionRetries());
        jcsmpProperties.setProperty(JCSMPProperties.CONNECT_RETRY_WAIT_MS, properties.connectionRetryInterval());
        
        jcsmpProperties.setProperty(JCSMPProperties.MESSAGE_TTL, properties.messageTtl());
        jcsmpProperties.setProperty(JCSMPProperties.MESSAGE_PRIORITY, properties.messagePriority());
        
        return jcsmpProperties;
    }
}
