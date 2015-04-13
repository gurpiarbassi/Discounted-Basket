package com.gurps.discountedbasket.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedHashMap;
import java.util.Map;

import com.gurps.discountedbasket.domain.Basket;
import com.gurps.discountedbasket.domain.ItemType;
import com.gurps.discountedbasket.domain.ShoppingBasketImpl;

public class BasketPricingServiceImpl implements BasketPricingService {

	private BigDecimal subTotal = BigDecimal.ZERO;
	private BigDecimal grandTotal = BigDecimal.ZERO;

	/** Each distinct item has its discounts associated **/
	private Map<String, BigDecimal> discounts = new LinkedHashMap<String, BigDecimal>();

	public void priceBasket(final String[] itemsInput) {

		Basket shoppingBasket = new ShoppingBasketImpl();

		for (String itemName : itemsInput) {
			// ensure the item passed in is a valid one i.e. it has to be a
			// valid enum class
			ItemType itemType = ItemType.valueOf(itemName.toUpperCase());

			shoppingBasket.addItem(itemType);
		}

		shoppingBasket.calculate();

		this.subTotal = shoppingBasket.getSubTotal().setScale(2, RoundingMode.HALF_UP);
		this.grandTotal = shoppingBasket.getGrandTotal().setScale(2, RoundingMode.HALF_UP);
		this.discounts = shoppingBasket.getDiscounts();
	}

	public BigDecimal getSubTotal() {
		return subTotal;
	}

	public BigDecimal getGrandTotal() {
		return grandTotal;
	}

	public Map<String, BigDecimal> getDiscounts() {
		return discounts;
	}
}
