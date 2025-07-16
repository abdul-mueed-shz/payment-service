package com.abdul.stripe.domain.stripe.port.in;

import com.stripe.exception.StripeException;

public interface CheckoutPaymentSessionUseCase {

    String execute() throws StripeException;
}
