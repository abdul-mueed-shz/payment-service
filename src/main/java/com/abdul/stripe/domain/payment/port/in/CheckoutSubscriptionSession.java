package com.abdul.stripe.domain.payment.port.in;

import com.abdul.stripe.domain.payment.model.SubscriptionCheckoutRequestDto;
import com.abdul.stripe.domain.payment.model.SubscriptionCheckoutResponseDto;
import com.stripe.exception.StripeException;

public interface CheckoutSubscriptionSession {

    SubscriptionCheckoutResponseDto execute(
            String productId,
            SubscriptionCheckoutRequestDto subscriptionCheckoutRequestDto)
            throws StripeException;
}
