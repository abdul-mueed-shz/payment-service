package com.abdul.stripe.integration;

import static org.assertj.core.api.Assertions.assertThat;

import com.abdul.stripe.config.StripeProperties;
import com.abdul.stripe.domain.stripe.port.in.CheckoutPaymentSessionUseCase;
import com.stripe.exception.StripeException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("dev")
class CheckoutPaymentSessionUseCaseImplIntegrationTests {

    @Autowired
    private CheckoutPaymentSessionUseCase checkoutPaymentSessionUseCase;

    @Autowired
    private StripeProperties stripeProperties;

    @Test
    void shouldCreateCheckoutSessionAndReturnValidUrl() throws StripeException {
        // Act
        String url = checkoutPaymentSessionUseCase.execute();

        // Assert
        assertThat(url).as("Stripe session URL should be valid").isNotBlank().startsWith("https://");
        System.out.println("Stripe Checkout URL: " + url);
    }
}
