package com.gurps.discountedbasket.domain;

import java.math.BigDecimal;

public interface BasketVisitor {

	void visit(final Basket basket);

	BigDecimal getTotalDiscount();

	String getDescription();
}
