package com.abdul.stripe.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "stripe")
public class StripeProperties {

    private String domain;
    private String apiKey;
    private String successUrl;
    private String cancelUrl;

    private final Webhook webhook = new Webhook();

    @Data
    public static class Webhook {

        private String endpoint;
        private String secret;
    }
}
