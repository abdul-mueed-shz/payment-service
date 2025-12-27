package com.abdul.stripe.domain.payment.mapper;

import com.abdul.stripe.domain.payment.model.InvoiceLineItemDto;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.ArrayList;
import java.util.List;

public class JsonNodeUtils {

    private JsonNodeUtils() {
        // utility
    }

    public static String getText(JsonNode node, String field) {
        if (node == null) return null;
        JsonNode n = node.path(field);
        return n.isMissingNode() || n.isNull() ? null : n.asText();
    }

    public static Long getLong(JsonNode node, String field) {
        if (node == null) return null;
        JsonNode n = node.path(field);
        return n.isMissingNode() || n.isNull() ? null : n.asLong();
    }

    public static Boolean getBoolean(JsonNode node, String field) {
        if (node == null) return null;
        JsonNode n = node.path(field);
        return n.isMissingNode() || n.isNull() ? null : n.asBoolean();
    }

    public static List<InvoiceLineItemDto> mapLineItems(JsonNode dataArray) {
        List<InvoiceLineItemDto> lines = new ArrayList<>();
        if (dataArray == null || !dataArray.isArray()) return lines;
        for (JsonNode itemNode : dataArray) {
            InvoiceLineItemDto lineDto = new InvoiceLineItemDto();
            lineDto.setId(getText(itemNode, "id"));
            lineDto.setDescription(getText(itemNode, "description"));
            lineDto.setQuantity(getLong(itemNode, "quantity"));
            lineDto.setAmount(getLong(itemNode, "amount"));
            lineDto.setCurrency(getText(itemNode, "currency"));

            JsonNode priceNode = itemNode.path("price");
            if (!priceNode.isMissingNode() && !priceNode.isNull()) {
                if (priceNode.isTextual()) {
                    lineDto.setPriceId(priceNode.asText());
                } else if (priceNode.isObject()) {
                    lineDto.setPriceId(getText(priceNode, "id"));
                    JsonNode prodNode = priceNode.path("product");
                    if (prodNode.isTextual()) {
                        lineDto.setProductId(prodNode.asText());
                    } else if (prodNode.isObject()) {
                        lineDto.setProductId(getText(prodNode, "id"));
                    }
                    lineDto.setPlanId(getText(priceNode, "lookup_key"));
                }
            } else {
                JsonNode planNode = itemNode.path("plan");
                if (planNode.isObject()) {
                    lineDto.setPlanId(getText(planNode, "id"));
                }
                JsonNode productNode = itemNode.path("product");
                if (productNode.isTextual()) {
                    lineDto.setProductId(productNode.asText());
                } else if (productNode.isObject()) {
                    lineDto.setProductId(getText(productNode, "id"));
                }
            }

            lines.add(lineDto);
        }
        return lines;
    }
}
