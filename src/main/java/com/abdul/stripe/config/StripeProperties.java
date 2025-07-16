package com.abdul.stripe.config;

import java.util.Map;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "stripe")
public class StripeProperties {

    private String domain;
    private String apiKey;
    private String price;
    private String successUrl;
    private String cancelUrl;

    private final Webhook webhook = new Webhook();
    private final SubscriptionPlans subscriptionPlans = new SubscriptionPlans();

    @Data
    public static class Webhook {

        private String endpoint;
        private String secret;
    }

    @Data
    public static class SubscriptionPlans {

        private Map<String, String> prices;

    }
}
