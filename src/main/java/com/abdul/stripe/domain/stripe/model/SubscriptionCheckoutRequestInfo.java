package com.abdul.stripe.domain.stripe.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubscriptionCheckoutRequestInfo {

    private String subscriptionPlan; // i.e. basicMonthly
}
