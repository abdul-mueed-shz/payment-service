package com.abdul.stripe.adapter.in.web;

import com.abdul.stripe.domain.stripe.model.SubscriptionCheckoutRequestDto;
import com.abdul.stripe.domain.stripe.model.SubscriptionCheckoutResponseDto;
import com.abdul.stripe.domain.stripe.model.SubscriptionProductDto;
import com.abdul.stripe.domain.stripe.port.in.CheckoutPaymentSessionUseCase;
import com.abdul.stripe.domain.stripe.port.in.CheckoutSubscriptionSession;
import com.abdul.stripe.domain.stripe.port.in.HandleStripeWebhook;
import com.abdul.stripe.domain.stripe.port.in.SubscriptionProductsUseCase;
import com.stripe.exception.StripeException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/payments")
@RequiredArgsConstructor
public class StripeController {

    private final CheckoutPaymentSessionUseCase checkoutPaymentSessionUseCase;
    private final CheckoutSubscriptionSession checkoutSubscriptionSession;
    private final HandleStripeWebhook handleStripeWebhook;
    private final SubscriptionProductsUseCase subscriptionProductsUseCase;

    @PostMapping("/{productId}/subscription")
    public ResponseEntity<SubscriptionCheckoutResponseDto>
    createSubscriptionSession(@PathVariable String productId,
                              @RequestBody SubscriptionCheckoutRequestDto subscriptionCheckoutRequestDto)
            throws StripeException {
        return ResponseEntity.ok(checkoutSubscriptionSession.execute(productId, subscriptionCheckoutRequestDto));
    }

    @PostMapping("/stripe/webhook")
    public void webhook(@RequestBody String payload, HttpServletRequest request) throws StripeException {
        handleStripeWebhook.execute(payload, request.getHeader("Stripe-Signature"));
    }

    @GetMapping("/products")
    public ResponseEntity<List<SubscriptionProductDto>> listProducts() throws StripeException {
        return ResponseEntity.ok(subscriptionProductsUseCase.listProducts());
    }
}
