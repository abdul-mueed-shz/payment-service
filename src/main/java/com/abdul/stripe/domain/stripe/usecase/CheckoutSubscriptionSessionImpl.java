package com.abdul.stripe.domain.stripe.usecase;

import com.abdul.stripe.config.StripeProperties;
import com.abdul.stripe.domain.stripe.model.SubscriptionCheckoutRequestInfo;
import com.abdul.stripe.domain.stripe.model.SubscriptionCheckoutResponseInfo;
import com.abdul.stripe.domain.stripe.port.in.CheckoutSubscriptionSession;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.net.RequestOptions;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class CheckoutSubscriptionSessionImpl implements CheckoutSubscriptionSession {

    private final StripeProperties properties;

    /**
     * Process subscription to a product plan using {@link SubscriptionCheckoutRequestInfo}
     *
     * @param subscriptionCheckoutRequestInfo {@link SubscriptionCheckoutRequestInfo}
     * @return redirect url
     * @throws StripeException if stripe fails to process the checkout request
     */
    @Override
    public SubscriptionCheckoutResponseInfo execute(SubscriptionCheckoutRequestInfo subscriptionCheckoutRequestInfo)
            throws StripeException {
        String subscriptionPlanPriceId =
                properties.getSubscriptionPlans().getPrices()
                        .get(subscriptionCheckoutRequestInfo.getSubscriptionPlan());
        if (subscriptionPlanPriceId == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No such plan exists: "
                    + subscriptionCheckoutRequestInfo.getSubscriptionPlan());
        }
        SessionCreateParams params = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.SUBSCRIPTION)
                .setSuccessUrl(properties.getSuccessUrl() + "?session_id={CHECKOUT_SESSION_ID}")
                .setCancelUrl(properties.getCancelUrl())
                .addLineItem(
                        SessionCreateParams.LineItem.builder()
                                .setPrice(subscriptionPlanPriceId)
                                .setQuantity(1L)
                                .build()
                )
                .build();
        RequestOptions requestOptions = RequestOptions.builder()
                .setApiKey(properties.getApiKey())
                .build();
        Session session = Session.create(params, requestOptions);
        SubscriptionCheckoutResponseInfo responseInfo = new SubscriptionCheckoutResponseInfo();
        responseInfo.setRedirectUrl(session.getUrl());
        return responseInfo;
    }
}
