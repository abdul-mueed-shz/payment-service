package com.abdul.stripe.domain.stripe.port.in;

import com.abdul.stripe.domain.stripe.model.SubscriptionCheckoutRequestDto;
import com.abdul.stripe.domain.stripe.model.SubscriptionCheckoutResponseDto;
import com.stripe.exception.StripeException;

public interface CheckoutSubscriptionSession {

    SubscriptionCheckoutResponseDto execute(
            String productId,
            SubscriptionCheckoutRequestDto subscriptionCheckoutRequestDto)
            throws StripeException;
}
