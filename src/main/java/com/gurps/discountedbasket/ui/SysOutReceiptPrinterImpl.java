package com.gurps.discountedbasket.ui;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.Map.Entry;

public class SysOutReceiptPrinterImpl implements ReceiptPrinter {

	public void print(final BigDecimal subtotal, final Map<String, BigDecimal> discounts,
			final BigDecimal grandTotal) {
		System.out.println("Subtotal: £"
				+ subtotal.setScale(2, RoundingMode.HALF_UP).toPlainString());

		if (discounts.isEmpty()) {
			System.out.println("(No offers available)");

		} else {
			for (Entry<String, BigDecimal> entry : discounts.entrySet()) {
				System.out.println(entry.getKey() + " " + ":" + " -" + "£"
						+ entry.getValue().setScale(2, RoundingMode.HALF_UP).toPlainString());
			}
		}

		System.out.println("Total: £"
				+ grandTotal.setScale(2, RoundingMode.HALF_UP).toPlainString());

	}
}