package com.abdul.stripe.domain.payment.mapper;

import com.abdul.stripe.domain.payment.model.InvoicePaymentSucceededDto;
import com.fasterxml.jackson.databind.JsonNode;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@SuppressWarnings("unused")
@Mapper(componentModel = "spring")
public interface InvoiceMapper {

    @Mapping(target = "id", expression = "java(com.abdul.stripe.domain.payment.mapper.JsonNodeUtils.getText(node, \"id\"))")
    @Mapping(target = "customerId", expression = "java(com.abdul.stripe.domain.payment.mapper.JsonNodeUtils.getText(node, \"customer\"))")
    @Mapping(target = "subscriptionId", expression = "java(com.abdul.stripe.domain.payment.mapper.JsonNodeUtils.getText(node, \"subscription\"))")
    @Mapping(target = "hostedInvoiceUrl", expression = "java(com.abdul.stripe.domain.payment.mapper.JsonNodeUtils.getText(node, \"hosted_invoice_url\"))")
    @Mapping(target = "invoicePdf", expression = "java(com.abdul.stripe.domain.payment.mapper.JsonNodeUtils.getText(node, \"invoice_pdf\"))")
    @Mapping(target = "amountPaid", expression = "java(com.abdul.stripe.domain.payment.mapper.JsonNodeUtils.getLong(node, \"amount_paid\"))")
    @Mapping(target = "amountDue", expression = "java(com.abdul.stripe.domain.payment.mapper.JsonNodeUtils.getLong(node, \"amount_due\"))")
    @Mapping(target = "created", expression = "java(com.abdul.stripe.domain.payment.mapper.JsonNodeUtils.getLong(node, \"created\"))")
    @Mapping(target = "periodStart", expression = "java(com.abdul.stripe.domain.payment.mapper.JsonNodeUtils.getLong(node, \"period_start\"))")
    @Mapping(target = "periodEnd", expression = "java(com.abdul.stripe.domain.payment.mapper.JsonNodeUtils.getLong(node, \"period_end\"))")
    @Mapping(target = "paid", expression = "java(com.abdul.stripe.domain.payment.mapper.JsonNodeUtils.getBoolean(node, \"paid\"))")
    @Mapping(target = "attempted", expression = "java(com.abdul.stripe.domain.payment.mapper.JsonNodeUtils.getBoolean(node, \"attempted\"))")
    @Mapping(target = "lines", expression = "java(com.abdul.stripe.domain.payment.mapper.JsonNodeUtils.mapLineItems(node.path(\"lines\").path(\"data\")))")
    InvoicePaymentSucceededDto toDto(JsonNode node);
}
