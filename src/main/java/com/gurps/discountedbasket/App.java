package com.gurps.discountedbasket;

import java.math.BigDecimal;
import java.util.Map;

import com.gurps.discountedbasket.service.BasketPricingService;
import com.gurps.discountedbasket.service.BasketPricingServiceImpl;
import com.gurps.discountedbasket.ui.ReceiptPrinter;
import com.gurps.discountedbasket.ui.SysOutReceiptPrinterImpl;

/**
 * Main class for pricing basket execution
 *
 */
public class App {
	public static void main(String[] args) {
		BasketPricingService basketService = new BasketPricingServiceImpl();
		basketService.priceBasket(args);

		Map<String, BigDecimal> discounts = basketService.getDiscounts();

		ReceiptPrinter output = new SysOutReceiptPrinterImpl();
		output.print(basketService.getSubTotal(), discounts, basketService.getGrandTotal());
	}
}
