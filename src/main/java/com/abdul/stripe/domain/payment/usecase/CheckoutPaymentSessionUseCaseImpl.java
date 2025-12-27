package com.abdul.stripe.domain.payment.usecase;

import com.abdul.stripe.config.StripeProperties;
import com.abdul.stripe.domain.payment.port.in.CheckoutPaymentSessionUseCase;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.net.RequestOptions;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CheckoutPaymentSessionUseCaseImpl implements CheckoutPaymentSessionUseCase {

    private final StripeProperties properties;

    /**
     * @return redirect url
     */
    @Override
    public String execute() throws StripeException {
        SessionCreateParams params = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl(properties.getSuccessUrl())
                .setCancelUrl(properties.getCancelUrl())
                .addLineItem(
                        SessionCreateParams.LineItem.builder()
                                .setQuantity(1L)
                                /*.setPrice(properties.getPrice())*/
                                .build()
                )
                .build();
        RequestOptions requestOptions = RequestOptions.builder()
                .setApiKey(properties.getApiKey())
                .build();
        Session session = Session.create(params, requestOptions);
        return session.getUrl();
    }
}
