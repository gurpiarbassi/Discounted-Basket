package com.gurps.discountedbasket.domain;

import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;

public abstract class BaseDiscountVisitor {

	private String description;
	protected BigDecimal totalDiscount;

	public BaseDiscountVisitor(final String description) {
		if (StringUtils.isEmpty(description)) {
			throw new IllegalArgumentException("Discount description must be specified");
		}
		this.description = description;
		this.totalDiscount = BigDecimal.ZERO;
	}

	public String getDescription() {
		return this.description;
	}

	public BigDecimal getTotalDiscount() {
		return totalDiscount;
	}
}
