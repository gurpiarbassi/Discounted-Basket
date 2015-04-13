package com.gurps.discountedbasket.domain;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.gurps.discountedbasket.data.DiscountEngine;

public class ShoppingBasketImpl implements Basket, Visitable {

	// Aggregated basket with each item and its respective quantity.
	private Map<ItemType, Integer> basketContents = new EnumMap<ItemType, Integer>(ItemType.class);

	private BigDecimal subTotal = null;
	private BigDecimal grandTotal = null;

	/** Map of discount text against amount **/
	private Map<String, BigDecimal> discounts = new LinkedHashMap<String, BigDecimal>();

	public BigDecimal getSubTotal() {
		return subTotal;
	}

	public BigDecimal getGrandTotal() {
		return grandTotal;
	}

	public void addItem(final ItemType itemType) {

		Integer qty = basketContents.get(itemType);
		if (qty == null) {
			basketContents.put(itemType, 1);
		} else {
			basketContents.put(itemType, qty.intValue() + 1);
		}

		resetTotals();
	}

	private void resetTotals() {
		subTotal = null;
		grandTotal = null;
		discounts.clear();
	}

	public void removeItem(final ItemType itemType) {
		throw new UnsupportedOperationException("Not currently supported");
	}

	public void calculate() {

		this.subTotal = BigDecimal.ZERO;
		BigDecimal discountTotal = BigDecimal.ZERO;
		Set<Entry<ItemType, Integer>> entries = basketContents.entrySet();

		for (Entry<ItemType, Integer> entry : entries) {

			this.subTotal = this.subTotal.add(entry.getKey().getPrice()
					.multiply(new BigDecimal(entry.getValue())));
		}

		List<BasketVisitor> discountVisitors = DiscountEngine.MAPPINGS;

		for (BasketVisitor visitor : discountVisitors) {

			// Each individual discount routine will calculate its own discount
			this.accept(visitor);
			BigDecimal calculatedPromoDiscount = visitor.getTotalDiscount();

			if (calculatedPromoDiscount.signum() > 0) {
				discountTotal = discountTotal.add(calculatedPromoDiscount);
				discounts.put(visitor.getDescription(), visitor.getTotalDiscount());
			}

		}

		this.grandTotal = this.subTotal.subtract(discountTotal);

	}

	public Map<String, BigDecimal> getDiscounts() {
		return discounts;
	}

	public Map<ItemType, Integer> getBasketContents() {
		return Collections.unmodifiableMap(basketContents);
	}

	public void accept(final BasketVisitor visitor) {
		visitor.visit(this);
	}
}