package com.abdul.stripe.domain.payment.usecase;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.stripe.model.Event;

public abstract class AbstractProcessWebhookEvent {
    public String execute(Event event) throws JsonProcessingException {
        handleEvent(event);
        return "Handled event: " + event.getType() + " with id: " + event.getId() + " and account: ";
    }

    public abstract void handleEvent(Event event) throws JsonProcessingException;
}
