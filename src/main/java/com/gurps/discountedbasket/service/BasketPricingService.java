package com.gurps.discountedbasket.service;

import java.math.BigDecimal;
import java.util.Map;

public interface BasketPricingService {

	void priceBasket(final String[] items);

	BigDecimal getSubTotal();

	BigDecimal getGrandTotal();

	Map<String, BigDecimal> getDiscounts();
}
