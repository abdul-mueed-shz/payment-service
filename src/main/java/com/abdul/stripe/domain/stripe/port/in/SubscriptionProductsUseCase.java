package com.abdul.stripe.domain.stripe.port.in;

import com.abdul.stripe.domain.stripe.model.SubscriptionProductDto;
import com.stripe.exception.StripeException;

import java.util.List;

public interface SubscriptionProductsUseCase {

    List<SubscriptionProductDto> listProducts() throws StripeException;

    String getPriceIdForProduct(String productId) throws StripeException;
}
