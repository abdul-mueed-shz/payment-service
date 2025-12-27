package com.abdul.stripe.domain.payment.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubscriptionCheckoutResponseDto {

    private String redirectUrl;
}
