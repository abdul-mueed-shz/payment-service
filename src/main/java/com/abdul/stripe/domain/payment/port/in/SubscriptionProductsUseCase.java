package com.abdul.stripe.domain.payment.port.in;

import com.abdul.stripe.domain.payment.model.SubscriptionProductDto;
import com.stripe.exception.StripeException;

import java.util.List;

public interface SubscriptionProductsUseCase {

    List<SubscriptionProductDto> listProducts() throws StripeException;

    String getPriceIdForProduct(String productId) throws StripeException;
}
