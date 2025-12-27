package com.abdul.stripe.domain.payment.port.in;

import com.stripe.exception.StripeException;

public interface CheckoutPaymentSessionUseCase {

    String execute() throws StripeException;
}
