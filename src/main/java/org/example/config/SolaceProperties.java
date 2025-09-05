package org.example.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Solace configuration properties
 */
@ConfigurationProperties(prefix = "solace")
public record SolaceProperties(
        String host,
        String username,
        String password,
        String vpn,
        String clientName,
        int connectionTimeout,
        int connectionRetries,
        int connectionRetryInterval,
        String defaultQueue,
        String defaultTopic
) {
}