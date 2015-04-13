package com.gurps.discountedbasket.ui;

import java.math.BigDecimal;
import java.util.Map;

public interface ReceiptPrinter {

	void print(final BigDecimal subtotal, final Map<String, BigDecimal> discounts,
			final BigDecimal grandTotal);
}
