package com.abdul.stripe.config;

import com.stripe.Stripe;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class StripeConfig {
    private final StripeProperties stripeProperties;

    @PostConstruct
    public void init() {
        if (stripeProperties == null || stripeProperties.getApiKey() == null) {
            return;
        }
        Stripe.apiKey = stripeProperties.getApiKey();
    }
}
