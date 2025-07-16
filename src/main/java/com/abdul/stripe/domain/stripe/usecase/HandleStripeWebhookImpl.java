package com.abdul.stripe.domain.stripe.usecase;

import com.abdul.stripe.config.StripeProperties;
import com.abdul.stripe.domain.stripe.port.in.HandleStripeWebhook;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.net.Webhook;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HandleStripeWebhookImpl implements HandleStripeWebhook {

    private final StripeProperties stripeProperties;

    /**
     * Handles stripe webhook callback.
     *
     * @param payload         {@link String}
     * @param signatureHeader {@link String}
     * @throws StripeException // Throws stripe exception if signature if not verified.
     */
    @Override
    public void execute(
            String payload,
            String signatureHeader) throws StripeException {
        Event event = Webhook.constructEvent(payload, signatureHeader,
                stripeProperties.getWebhook().getSecret());
        System.out.println(event);
    }
}
