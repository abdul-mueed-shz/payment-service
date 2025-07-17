package com.abdul.stripe.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

import com.abdul.stripe.domain.stripe.model.SubscriptionCheckoutRequestInfo;
import com.abdul.stripe.domain.stripe.model.SubscriptionCheckoutResponseInfo;
import com.abdul.stripe.domain.stripe.port.in.CheckoutSubscriptionSession;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.net.RequestOptions;
import com.stripe.param.checkout.SessionCreateParams;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("dev")
class CheckoutSubscriptionSessionImplIntegrationTests {

    @Autowired
    private CheckoutSubscriptionSession checkoutSubscriptionSession;

    @Test
    void shouldReturnStripeRedirectUrl() throws StripeException {
        SubscriptionCheckoutRequestInfo request = new SubscriptionCheckoutRequestInfo();
        request.setSubscriptionPlan("basicMonthly");

        Session mockSession = mock(Session.class);
        when(mockSession.getUrl()).thenReturn("https://checkout.stripe.com/mock-subscription");

        try (MockedStatic<Session> sessionMockedStatic = mockStatic(Session.class)) {
            sessionMockedStatic.when(() -> Session.create(any(SessionCreateParams.class), any(RequestOptions.class)))
                    .thenReturn(mockSession);
            SubscriptionCheckoutResponseInfo response = checkoutSubscriptionSession.execute(request);
            assertThat(response).isNotNull().extracting(SubscriptionCheckoutResponseInfo::getRedirectUrl).asString()
                    .startsWith("https://checkout.stripe.com/");
        }
    }
}
