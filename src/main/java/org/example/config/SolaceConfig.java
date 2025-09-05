package org.example.config;

import com.solacesystems.jcsmp.JCSMPChannelProperties;
import com.solacesystems.jcsmp.JCSMPProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Solace configuration
 */
@Configuration
@EnableConfigurationProperties(SolaceProperties.class)
public class SolaceConfig {

    @Bean
    public JCSMPProperties jcsmpProperties(@Autowired SolaceProperties solaceProperties) {
        JCSMPProperties jcsmpProperties = new JCSMPProperties();

        // Basic connection properties
        jcsmpProperties.setProperty(JCSMPProperties.HOST, solaceProperties.host());
        jcsmpProperties.setProperty(JCSMPProperties.USERNAME, solaceProperties.username());
        jcsmpProperties.setProperty(JCSMPProperties.PASSWORD, solaceProperties.password());
        jcsmpProperties.setProperty(JCSMPProperties.VPN_NAME, solaceProperties.vpn());
        jcsmpProperties.setProperty(JCSMPProperties.CLIENT_NAME, solaceProperties.clientName());

        // Connection timeout and retry properties - use JCSMPChannelProperties
        JCSMPChannelProperties channelProperties = new JCSMPChannelProperties();
        channelProperties.setConnectTimeoutInMillis(solaceProperties.connectionTimeout());
        channelProperties.setConnectRetries(solaceProperties.connectionRetries());
        channelProperties.setReconnectRetryWaitInMillis(solaceProperties.connectionRetryInterval());

        // Assign the channel properties to the JCSMP properties
        jcsmpProperties.setProperty(JCSMPProperties.CLIENT_CHANNEL_PROPERTIES, channelProperties);

        return jcsmpProperties;
    }
}