package com.abdul.stripe.domain.stripe.port.in;

import com.abdul.stripe.domain.stripe.model.SubscriptionCheckoutRequestInfo;
import com.abdul.stripe.domain.stripe.model.SubscriptionCheckoutResponseInfo;
import com.stripe.exception.StripeException;

public interface CheckoutSubscriptionSession {

    SubscriptionCheckoutResponseInfo execute(SubscriptionCheckoutRequestInfo subscriptionCheckoutRequestInfo)
            throws StripeException;
}
