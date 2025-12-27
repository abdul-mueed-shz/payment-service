package com.abdul.stripe.domain.payment.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubscriptionCheckoutRequestDto {

    private String successUrl;
    private String cancelUrl;
    // Optional: explicit price id to use for the subscription (overrides product default lookup)
    private String priceId;
}
