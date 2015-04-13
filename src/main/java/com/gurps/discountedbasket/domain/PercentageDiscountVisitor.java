package com.gurps.discountedbasket.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

public class PercentageDiscountVisitor extends BaseDiscountVisitor implements BasketVisitor {

	private BigDecimal percentage;
	private ItemType itemType;

	/**
	 * 
	 * @param description
	 * @param itemType
	 * @param percentage
	 *            a value from 0 to 100
	 */
	public PercentageDiscountVisitor(final String description, final ItemType itemType,
			final BigDecimal percentage) {
		super(description);

		if (percentage == null) {
			throw new IllegalArgumentException("Discount percentage must be specified");
		} else if (itemType == null) {
			throw new IllegalArgumentException("A valid ItemType must be supplied");
		}
		this.percentage = percentage;
		this.itemType = itemType;
	}

	/**
	 * Look for the item type in the basket and knock off a percentage of the
	 * total price for that item type.
	 */
	public void visit(final Basket basket) {

		totalDiscount = BigDecimal.ZERO;
		Map<ItemType, Integer> basketContents = basket.getBasketContents();

		for (ItemType itemType : basketContents.keySet()) {
			if (itemType.equals(this.itemType)) {
				Integer qty = basketContents.get(itemType);

				BigDecimal currentTotal = itemType.getPrice().multiply(new BigDecimal(qty));

				totalDiscount = percentage.divide(new BigDecimal("100.00")).multiply(currentTotal)
						.setScale(2, RoundingMode.HALF_UP);
			}
		}

	}

}
