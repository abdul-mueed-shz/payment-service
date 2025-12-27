package com.abdul.stripe.domain.payment.model;

import lombok.Data;

@Data
public class InvoiceLineItemDto {
    private String id;
    private String description;
    private Long quantity;
    private Long amount; // amount for the line (in cents)
    private String currency;
    private String priceId;
    private String productId;
    private String planId;
}

