package com.abdul.stripe.domain.payment.model;

import lombok.Data;

import java.util.List;

@Data
public class InvoicePaymentSucceededDto {

    private String id; // invoice id
    private String customerId;
    private String subscriptionId;
    private String hostedInvoiceUrl;
    private String invoicePdf;
    private Long amountPaid; // in cents
    private Long amountDue;
    private Long created; // epoch seconds
    private Long periodStart; // epoch seconds
    private Long periodEnd; // epoch seconds
    private Boolean paid;
    private Boolean attempted;
    private List<InvoiceLineItemDto> lines;
}
