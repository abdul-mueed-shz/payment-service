package com.abdul.stripe.domain.payment.usecase;

import com.abdul.stripe.domain.payment.mapper.SubscriptionProductMapper;
import com.abdul.stripe.domain.payment.model.SubscriptionProductDto;
import com.abdul.stripe.domain.payment.port.in.SubscriptionProductsUseCase;
import com.stripe.exception.StripeException;
import com.stripe.model.Price;
import com.stripe.model.PriceCollection;
import com.stripe.model.Product;
import com.stripe.param.PriceListParams;
import com.stripe.param.ProductListParams;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class SubscriptionProductsUseCaseImpl implements SubscriptionProductsUseCase {

    private final SubscriptionProductMapper subscriptionProductMapper;

    @Override
    public List<SubscriptionProductDto> listProducts() throws StripeException {
        List<Product> products = new ArrayList<>();
        ProductListParams params = ProductListParams.builder().setLimit(100L).build();
        for (Product p : Product.list(params).autoPagingIterable()) {
            products.add(p);
        }
        return subscriptionProductMapper.toDtoList(products);

    }

    @Override
    public String getPriceIdForProduct(String productId) throws StripeException {
        Product product = Product.retrieve(productId);
        String defaultPriceId = product.getDefaultPrice();

        if (defaultPriceId != null && !defaultPriceId.isEmpty()) {
            return defaultPriceId;
        }

        Price defaultPriceObj = product.getDefaultPriceObject();
        if (defaultPriceObj != null && defaultPriceObj.getId() != null) {
            return defaultPriceObj.getId();
        }

        PriceListParams priceListParams = PriceListParams.builder().setProduct(productId).setLimit(10L).build();
        PriceCollection prices = Price.list(priceListParams);
        if (prices.getData() == null || prices.getData().isEmpty()) {
            return null; // No price found for product
        }

        for (Price price : prices.getData()) {
            if (Boolean.TRUE.equals(price.getActive())) {
                return price.getId();
            }
        }

        return prices.getData().get(0).getId();
    }
}
