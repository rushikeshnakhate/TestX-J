package org.example.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Solace configuration properties
 */
@Component
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
        int messageTtl,
        int messagePriority,
        String defaultQueue,
        String defaultTopic
) {
}
