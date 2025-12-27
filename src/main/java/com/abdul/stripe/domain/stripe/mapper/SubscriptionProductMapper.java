package com.abdul.stripe.domain.stripe.mapper;

import com.abdul.stripe.domain.stripe.model.SubscriptionProductDto;
import com.stripe.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface SubscriptionProductMapper {

    SubscriptionProductDto toDto(Product product);

    List<SubscriptionProductDto> toDtoList(List<Product> product);

}
