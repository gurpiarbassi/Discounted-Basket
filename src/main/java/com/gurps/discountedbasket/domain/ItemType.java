package com.gurps.discountedbasket.domain;

import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;

/**
 * All the possible item types that the program supports Please add new ones
 * here.
 * 
 * @author gurps
 *
 */
public enum ItemType {

	APPLES("Apples", new BigDecimal("1.00")), MILK("Milk", new BigDecimal("1.30")), BREAD("Bread",
			new BigDecimal("0.80")), SOUP("Soup", new BigDecimal("0.65"));

	private BigDecimal price;
	private String displayName;

	ItemType(final String displayName, final BigDecimal price) {
		if (price == null) {
			throw new IllegalArgumentException("Price must be a valid decimal");
		} else if (StringUtils.isEmpty(displayName)) {
			throw new IllegalArgumentException("displayName needs to be set");
		}

		this.displayName = displayName;
		this.price = price;
	}

	public BigDecimal getPrice() {
		return this.price;
	}

	public String getDisplayName() {
		return displayName;
	}
}
