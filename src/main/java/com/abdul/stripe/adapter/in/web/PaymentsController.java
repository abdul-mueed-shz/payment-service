package com.abdul.stripe.adapter.in.web;

import com.abdul.stripe.domain.payment.model.SubscriptionCheckoutRequestDto;
import com.abdul.stripe.domain.payment.model.SubscriptionCheckoutResponseDto;
import com.abdul.stripe.domain.payment.model.SubscriptionProductDto;
import com.abdul.stripe.domain.payment.port.in.CheckoutSubscriptionSession;
import com.abdul.stripe.domain.payment.port.in.SubscriptionProductsUseCase;
import com.stripe.exception.StripeException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/payments")
@RequiredArgsConstructor
public class PaymentsController {

    private final CheckoutSubscriptionSession checkoutSubscriptionSession;
    private final SubscriptionProductsUseCase subscriptionProductsUseCase;

    @PostMapping("/{productId}/subscription")
    public ResponseEntity<SubscriptionCheckoutResponseDto>
    createSubscriptionSession(@PathVariable String productId,
                              @RequestBody SubscriptionCheckoutRequestDto subscriptionCheckoutRequestDto)
            throws StripeException {
        return ResponseEntity.ok(checkoutSubscriptionSession.execute(productId, subscriptionCheckoutRequestDto));
    }

    @GetMapping("/products")
    public ResponseEntity<List<SubscriptionProductDto>> listProducts() throws StripeException {
        return ResponseEntity.ok(subscriptionProductsUseCase.listProducts());
    }
}
