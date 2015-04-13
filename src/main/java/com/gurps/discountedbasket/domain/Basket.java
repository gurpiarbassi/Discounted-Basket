package com.gurps.discountedbasket.domain;

import java.math.BigDecimal;
import java.util.Map;

public interface Basket {

	void addItem(final ItemType item);

	void removeItem(final ItemType item);

	void calculate();

	BigDecimal getGrandTotal();

	BigDecimal getSubTotal();

	Map<String, BigDecimal> getDiscounts();

	Map<ItemType, Integer> getBasketContents();
}
