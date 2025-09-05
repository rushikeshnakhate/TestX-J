package org.example.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * QuickFIX configuration
 */
@Configuration
public class QuickFixConfig {

    @Bean
    @ConfigurationProperties(prefix = "quickfix.default")
    public QuickFixProperties quickFixProperties() {
        return new QuickFixProperties();
    }

    /**
     * QuickFIX configuration properties
     */
    public static class QuickFixProperties {
        private String senderCompId = "SENDER";
        private String targetCompId = "TARGET";
        private int heartbeatInterval = 30;

        // Getters and setters
        public String getSenderCompId() {
            return senderCompId;
        }

        public void setSenderCompId(String senderCompId) {
            this.senderCompId = senderCompId;
        }

        public String getTargetCompId() {
            return targetCompId;
        }

        public void setTargetCompId(String targetCompId) {
            this.targetCompId = targetCompId;
        }

        public int getHeartbeatInterval() {
            return heartbeatInterval;
        }

        public void setHeartbeatInterval(int heartbeatInterval) {
            this.heartbeatInterval = heartbeatInterval;
        }
    }
}