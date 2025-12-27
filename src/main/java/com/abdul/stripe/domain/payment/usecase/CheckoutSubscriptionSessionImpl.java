package com.abdul.stripe.domain.payment.usecase;

import com.abdul.stripe.config.StripeProperties;
import com.abdul.stripe.domain.payment.model.SubscriptionCheckoutRequestDto;
import com.abdul.stripe.domain.payment.model.SubscriptionCheckoutResponseDto;
import com.abdul.stripe.domain.payment.port.in.CheckoutSubscriptionSession;
import com.abdul.stripe.domain.payment.port.in.SubscriptionProductsUseCase;
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
    private final SubscriptionProductsUseCase subscriptionProductsUseCase;

    /**
     *
     * @return redirect url
     * @throws StripeException if stripe fails to process the checkout request
     */
    @Override
    public SubscriptionCheckoutResponseDto execute(
            String productId,
            SubscriptionCheckoutRequestDto subscriptionCheckoutRequestDto)
            throws StripeException {
        String priceId = subscriptionProductsUseCase.getPriceIdForProduct(productId);
        if (priceId == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No such plan exists for product: "
                    + productId);
        }
        String successUrl = subscriptionCheckoutRequestDto.getSuccessUrl() != null
                ? subscriptionCheckoutRequestDto.getSuccessUrl() : properties.getSuccessUrl();

        String cancelUrl = subscriptionCheckoutRequestDto.getCancelUrl() != null ?
                subscriptionCheckoutRequestDto.getCancelUrl() : properties.getCancelUrl();

        SessionCreateParams params = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.SUBSCRIPTION)
                .setSuccessUrl(successUrl + "?session_id={CHECKOUT_SESSION_ID}")
                .setCancelUrl(cancelUrl)
                .addLineItem(
                        SessionCreateParams.LineItem.builder()
                                .setPrice(priceId)
                                .setQuantity(1L)
                                .build()
                )
                .build();
        RequestOptions requestOptions = RequestOptions.builder()
                .setApiKey(properties.getApiKey())
                .build();
        Session session = Session.create(params, requestOptions);
        SubscriptionCheckoutResponseDto responseInfo = new SubscriptionCheckoutResponseDto();
        responseInfo.setRedirectUrl(session.getUrl());
        return responseInfo;
    }
}
