package com.abdul.stripe.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import com.abdul.stripe.domain.stripe.usecase.CheckoutPaymentSessionUseCaseImpl;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.net.RequestOptions;
import com.stripe.param.checkout.SessionCreateParams;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class CheckoutPaymentSessionUseCaseTests {

    @Autowired
    private CheckoutPaymentSessionUseCaseImpl checkoutPaymentSessionUseCase;

    @BeforeEach
    void setUp() {
        // Setup test context or security context.
    }

    @Test
    void testExecuteReturnsSessionUrl() throws StripeException {
        Session mockSession = mock(Session.class);
        when(mockSession.getUrl()).thenReturn("https://checkout.stripe.com/test-session");

        try (MockedStatic<Session> sessionMockedStatic = mockStatic(Session.class)) {
            sessionMockedStatic.when(() -> Session.create(any(SessionCreateParams.class), any(RequestOptions.class)))
                    .thenReturn(mockSession);

            String url = checkoutPaymentSessionUseCase.execute();
            assertEquals("https://checkout.stripe.com/test-session", url);
            sessionMockedStatic.verify(() -> Session.create(any(SessionCreateParams.class), any(RequestOptions.class)),
                    times(1));
        }
    }
}
