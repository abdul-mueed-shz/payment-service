package com.abdul.stripe.unit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

import com.abdul.stripe.config.StripeProperties;
import com.abdul.stripe.domain.stripe.model.SubscriptionCheckoutRequestInfo;
import com.abdul.stripe.domain.stripe.model.SubscriptionCheckoutResponseInfo;
import com.abdul.stripe.domain.stripe.usecase.CheckoutSubscriptionSessionImpl;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.net.RequestOptions;
import com.stripe.param.checkout.SessionCreateParams;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;

@SpringBootTest
class CheckoutSubscriptionSessionImplTests {

    private CheckoutSubscriptionSessionImpl checkoutSubscriptionSession;

    @BeforeEach
    void setUp() {
        StripeProperties stripeProperties = mock(StripeProperties.class);
        StripeProperties.SubscriptionPlans subscriptionPlans = mock(StripeProperties.SubscriptionPlans.class);

        when(subscriptionPlans.getPrices()).thenReturn(Map.of("basic", "price_abc123"));
        when(stripeProperties.getSubscriptionPlans()).thenReturn(subscriptionPlans);
        when(stripeProperties.getApiKey()).thenReturn("sk_test_12345");
        when(stripeProperties.getSuccessUrl()).thenReturn("https://example.com/success");
        when(stripeProperties.getCancelUrl()).thenReturn("https://example.com/cancel");

        checkoutSubscriptionSession = new CheckoutSubscriptionSessionImpl(stripeProperties);
    }

    @Test
    void testExecuteReturnsRedirectUrl() throws StripeException {
        SubscriptionCheckoutRequestInfo request = new SubscriptionCheckoutRequestInfo();
        request.setSubscriptionPlan("basic");

        Session mockSession = mock(Session.class);
        when(mockSession.getUrl()).thenReturn("https://checkout.stripe.com/test-subscription");

        try (MockedStatic<Session> sessionMockedStatic = mockStatic(Session.class)) {
            sessionMockedStatic
                    .when(() -> Session.create(any(SessionCreateParams.class), any(RequestOptions.class)))
                    .thenReturn(mockSession);

            SubscriptionCheckoutResponseInfo response = checkoutSubscriptionSession.execute(request);

            assertThat(response)
                    .isNotNull()
                    .extracting(SubscriptionCheckoutResponseInfo::getRedirectUrl)
                    .asString()
                    .isEqualTo("https://checkout.stripe.com/test-subscription");
        }
    }

    @Test
    void testExecuteThrowsIfPlanMissing() {
        SubscriptionCheckoutRequestInfo request = new SubscriptionCheckoutRequestInfo();
        request.setSubscriptionPlan("invalid"); // this key won't be found in the preconfigured map

        assertThatThrownBy(() -> checkoutSubscriptionSession.execute(request))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("No such plan exists: invalid");
    }
}
