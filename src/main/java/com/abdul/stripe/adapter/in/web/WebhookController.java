package com.abdul.stripe.adapter.in.web;


import com.abdul.stripe.domain.payment.port.in.HandleStripeWebhook;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.stripe.exception.StripeException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/stripe/webhook")
@RequiredArgsConstructor
public class WebhookController {

    private final HandleStripeWebhook handleStripeWebhook;

    @PostMapping()
    public String webhook(@RequestBody String payload, HttpServletRequest request)
            throws StripeException, JsonProcessingException {
        return handleStripeWebhook.execute(payload, request.getHeader("Stripe-Signature"));
    }
}
