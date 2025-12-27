package com.abdul.stripe.domain.payment.port.in;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.stripe.exception.StripeException;

public interface HandleStripeWebhook {

    String execute(String payload, String signatureHeader) throws StripeException, JsonProcessingException;
}
