package com.abdul.stripe.adapter.in.web;

import com.abdul.stripe.domain.stripe.model.SubscriptionCheckoutRequestInfo;
import com.abdul.stripe.domain.stripe.model.SubscriptionCheckoutResponseInfo;
import com.abdul.stripe.domain.stripe.port.in.CheckoutPaymentSessionUseCase;
import com.abdul.stripe.domain.stripe.port.in.CheckoutSubscriptionSession;
import com.abdul.stripe.domain.stripe.port.in.HandleStripeWebhook;
import com.stripe.exception.StripeException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/payments/")
@RequiredArgsConstructor
public class StripeController {

    private final CheckoutPaymentSessionUseCase checkoutPaymentSessionUseCase;
    private final CheckoutSubscriptionSession checkoutSubscriptionSession;
    private final HandleStripeWebhook handleStripeWebhook;

    // TODO -> Add Auth
    @PostMapping
    public void checkoutPaymentSession(HttpServletResponse response) throws StripeException, IOException {
        response.sendRedirect(checkoutPaymentSessionUseCase.execute());
    }

    /**
     * // TODO -> Add Auth
     *
     * @param subscriptionCheckoutRequestInfo {@link SubscriptionCheckoutRequestInfo}
     * @return redirect url
     * @throws StripeException if stripe fails to process the request
     */
    @PostMapping("/stripe/subscription")
    public ResponseEntity<SubscriptionCheckoutResponseInfo> createSubscriptionSession(
            @RequestBody SubscriptionCheckoutRequestInfo subscriptionCheckoutRequestInfo)
            throws StripeException {
        return ResponseEntity.ok(checkoutSubscriptionSession.execute(subscriptionCheckoutRequestInfo));
    }

    @PostMapping("/stripe/webhook")
    public void webhook(@RequestBody String payload, HttpServletRequest request) throws StripeException {
        handleStripeWebhook.execute(payload, request.getHeader("Stripe-Signature"));
    }
}
