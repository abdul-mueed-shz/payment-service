package com.abdul.stripe.domain.payment.usecase;

import com.abdul.stripe.domain.payment.mapper.InvoiceMapper;
import com.abdul.stripe.domain.payment.model.InvoicePaymentSucceededDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stripe.model.Event;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@SuppressWarnings("SpringBeanName")
@Service("invoice.payment_succeeded")  // NOSONAR
@RequiredArgsConstructor
@Slf4j
public class InvoicePaymentSucceeded extends AbstractProcessWebhookEvent {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final InvoiceMapper invoiceMapper;

    @Override
    public void handleEvent(Event event) throws JsonProcessingException {
        Optional<String> rawOpt = extractRawJson(event);
        if (rawOpt.isEmpty()) {
            log.warn("No raw JSON for event data object; skipping event id={}", event.getId());
            return;
        }
        JsonNode invoiceNode = objectMapper.readTree(rawOpt.get());
        InvoicePaymentSucceededDto dto = invoiceMapper.toDto(invoiceNode);
        log.info("Invoice payment succeeded event mapped to DTO: {}", dto);
    }

    private Optional<String> extractRawJson(Event event) throws JsonProcessingException {
        if (event == null || event.getDataObjectDeserializer() == null) {
            return Optional.empty();
        }
        String rawObj = event.getDataObjectDeserializer().getRawJson();
        if (rawObj != null) {
            return Optional.of(rawObj);
        }
        Optional<?> objOpt = event.getDataObjectDeserializer().getObject();
        if (objOpt.isPresent()) {
            return Optional.of(objectMapper.writeValueAsString(objOpt.get()));
        }
        return Optional.empty();
    }
}
