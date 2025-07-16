package com.abdul.stripe.domain.stripe.port.in;

import com.stripe.exception.StripeException;

public interface HandleStripeWebhook {

    void execute(String payload, String signatureHeader) throws StripeException;
}
