package com.abdul.stripe.domain.stripe.model;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class SubscriptionProductDto {

    private String id;
    private String name;
    private String description;
    private boolean active;
    private List<String> images;
    private Map<String, String> metadata;
    private String defaultPriceId;
}

