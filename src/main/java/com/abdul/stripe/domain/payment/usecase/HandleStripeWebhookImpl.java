package com.abdul.stripe.domain.payment.usecase;

import com.abdul.stripe.config.StripeProperties;
import com.abdul.stripe.domain.payment.port.in.HandleStripeWebhook;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.net.Webhook;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HandleStripeWebhookImpl implements HandleStripeWebhook {

    private final StripeProperties stripeProperties;
    private final ApplicationContext applicationContext;

    /**
     * Handles stripe webhook callback.
     *
     * @param payload         {@link String}
     * @param signatureHeader {@link String}
     * @throws StripeException // Throws stripe exception if signature if not verified.
     */
    @Override
    public String execute(
            String payload,
            String signatureHeader) throws StripeException, JsonProcessingException {
        Event event = Webhook.constructEvent(payload, signatureHeader,
                stripeProperties.getWebhook().getSecret());
        boolean eventHandlerExists = applicationContext.containsBean(event.getType());
        if (!eventHandlerExists) {
            return "No handler found for event type: " + event.getType();
        }
        AbstractProcessWebhookEvent abstractProcessWebhookEvent =
                (AbstractProcessWebhookEvent) applicationContext.getBean(event.getType());
        return abstractProcessWebhookEvent.execute(event);
    }
}
