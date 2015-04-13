package com.gurps.discountedbasket.data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.gurps.discountedbasket.domain.BasketVisitor;
import com.gurps.discountedbasket.domain.BuyXGetPercentageOfYVisitor;
import com.gurps.discountedbasket.domain.ItemType;
import com.gurps.discountedbasket.domain.PercentageDiscountVisitor;

/**
 * Configuration to see which discounts are configured against which products.
 * Ideally this should be held in some kind of persistence storage / rules
 * engine.
 * 
 * This basically implementation only assumes a single promotion can be
 * configured against a product. Phase 2 of development could build upon this
 * and allow more complex rules which can allow multiple promotions. Although
 * this would prove quite complex since we would need to take into account the
 * precedence of each of the promotions
 * 
 * @author gurps
 *
 */

public class DiscountEngine {

	/**
	 * Mapping of item names against discounts These represent item level
	 * discounts
	 */
	public static final List<BasketVisitor> MAPPINGS = new ArrayList<BasketVisitor>() {

		private static final long serialVersionUID = -1490400411493388287L;

		{
			;
			add(new PercentageDiscountVisitor("Apples 10% off", ItemType.APPLES, new BigDecimal(
					"10.00")));
			add(new BuyXGetPercentageOfYVisitor(
					"Buy 2 tins of soup and get a loaf of bread for half price", 2, ItemType.SOUP,
					ItemType.BREAD, new BigDecimal("50.00")));
		}
	};

}
