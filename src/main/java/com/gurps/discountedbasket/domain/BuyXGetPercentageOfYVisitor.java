package com.gurps.discountedbasket.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

/**
 * Visitor to model promotion type of Buy n quantity of X and get percentage of
 * Y This is a basket level promotion.
 * 
 * @author gurps
 * 
 */
public class BuyXGetPercentageOfYVisitor extends BaseDiscountVisitor implements BasketVisitor {

	private BigDecimal percentage;
	private int minimumPurchaseQty;
	private ItemType itemX;
	private ItemType itemY;

	/**
	 * 
	 * @param description
	 * @param percentage
	 *            a value from 0 to 100
	 */
	public BuyXGetPercentageOfYVisitor(final String description, final int minimumPurchaseQty,
			final ItemType itemX, final ItemType itemY, final BigDecimal percentage) {
		super(description);

		if (percentage == null) {
			throw new IllegalArgumentException("percentage must be specified");
		} else if (minimumPurchaseQty < 1) {
			throw new IllegalArgumentException("minimumPurchaseQty must be greater than zero");
		} else if (itemX == null) {
			throw new IllegalArgumentException("itemX must be specified");
		} else if (itemY == null) {
			throw new IllegalArgumentException("itemY must be specified");
		}

		this.percentage = percentage;
		this.minimumPurchaseQty = minimumPurchaseQty;
		this.itemX = itemX;
		this.itemY = itemY;
	}

	public void visit(final Basket basket) {

		totalDiscount = BigDecimal.ZERO;

		Map<ItemType, Integer> basketContents = basket.getBasketContents();
		Integer qtyX = basketContents.get(itemX);
		Integer qtyY = basketContents.get(itemY);

		// TODO tidy this logic up a bit...don't like big if-statements.
		if (qtyY != null && qtyX != null && qtyX >= this.minimumPurchaseQty && qtyY >= 1) {

			/*
			 * If we don't have any item Y in the basket so the promotion will
			 * not apply i.e. you have to have a loaf of bread in the basket
			 * first before you can qualify for the promotion.
			 * 
			 * Also you must have purchases at least the minimum number of itemX
			 * before the promotion can kick in.
			 */

			final int noOfTimesPromoApplicable = (qtyX / this.minimumPurchaseQty);
			int factor = Math.min(noOfTimesPromoApplicable, qtyY);

			this.totalDiscount = new BigDecimal(factor).multiply(percentage
					.divide(new BigDecimal("100.00")).multiply(itemY.getPrice())
					.setScale(2, RoundingMode.HALF_UP));
		}
	}
}
