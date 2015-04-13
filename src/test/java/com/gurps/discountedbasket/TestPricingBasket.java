package com.gurps.discountedbasket;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.gurps.discountedbasket.service.BasketPricingService;
import com.gurps.discountedbasket.service.BasketPricingServiceImpl;

/**
 * Unit test for Pricing Basket
 */
public class TestPricingBasket extends TestCase

{
	public static final BigDecimal ZERO = BigDecimal.ZERO.setScale(2);

	/**
	 * Create the test case
	 *
	 * @param testName
	 *            name of the test case
	 */
	public TestPricingBasket(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(TestPricingBasket.class);
	}

	/**
	 * Ensure an empty basket is correctly calculated with zero totals
	 */
	public void testEmptyBasket() {
		BasketPricingService basketService = new BasketPricingServiceImpl();
		basketService.priceBasket(new String[] {});
		assertEquals(ZERO, basketService.getSubTotal());
		assertEquals(0, basketService.getDiscounts().size());
		assertEquals(ZERO, basketService.getGrandTotal());
	}

	/**
	 * Ensure exception is thrown if a unknown item is scanned in
	 */
	public void testUnknownItem() {
		BasketPricingService basketService = new BasketPricingServiceImpl();
		try {
			basketService.priceBasket(new String[] { "Grapes" });
			fail("Exception should have been thrown");
		} catch (Exception e) {

		}
	}

	/**
	 * Ensure a single non discountable item works correctly
	 */
	public void testSingleNonDiscountableItem() {
		BasketPricingService basketService = new BasketPricingServiceImpl();
		basketService.priceBasket(new String[] { "Milk" });
		assertEquals(new BigDecimal("1.30"), basketService.getSubTotal());
		assertEquals(0, basketService.getDiscounts().size());
		assertEquals(new BigDecimal("1.30"), basketService.getGrandTotal());
	}

	/**
	 * Ensure apple discounts are shown
	 */
	public void testSingleAppleDiscount() {
		BasketPricingService basketService = new BasketPricingServiceImpl();
		basketService.priceBasket(new String[] { "Apples" });
		assertEquals(new BigDecimal("1.00"), basketService.getSubTotal());
		assertEquals(1, basketService.getDiscounts().size());

		Map<String, BigDecimal> discounts = basketService.getDiscounts();
		Set<Entry<String, BigDecimal>> entrySet = discounts.entrySet();
		Entry<String, BigDecimal> entry = entrySet.iterator().next();

		assertEquals("Apples 10% off", entry.getKey());
		assertEquals(new BigDecimal("0.10"), entry.getValue());
		assertEquals(new BigDecimal("0.90"), basketService.getGrandTotal());
	}

	/**
	 * Having 1 soup but no bread should not trigger a discount
	 */
	public void test1SoupButNoBread() {

		BasketPricingService basketService = new BasketPricingServiceImpl();
		basketService.priceBasket(new String[] { "Soup" });
		assertEquals(new BigDecimal("0.65"), basketService.getSubTotal());
		assertEquals(0, basketService.getDiscounts().size());

		assertEquals(new BigDecimal("0.65"), basketService.getGrandTotal());
	}

	/**
	 * Having 2 soups but no bread should not trigger a discount Even though 2
	 * is the minimum soup qty required
	 */
	public void test2SoupsButNoBread() {

		BasketPricingService basketService = new BasketPricingServiceImpl();
		basketService.priceBasket(new String[] { "Soup", "Soup" });
		assertEquals(new BigDecimal("1.30"), basketService.getSubTotal());
		assertEquals(0, basketService.getDiscounts().size());

		assertEquals(new BigDecimal("1.30"), basketService.getGrandTotal());
	}

	/**
	 * Ensure the bread is discounted only once
	 */
	public void test2SoupsAnd1Bread() {

		BasketPricingService basketService = new BasketPricingServiceImpl();
		basketService.priceBasket(new String[] { "Soup", "Soup", "Bread" });
		assertEquals(new BigDecimal("2.10"), basketService.getSubTotal());
		assertEquals(1, basketService.getDiscounts().size());

		Map<String, BigDecimal> discounts = basketService.getDiscounts();
		Set<Entry<String, BigDecimal>> entrySet = discounts.entrySet();
		Entry<String, BigDecimal> entry = entrySet.iterator().next();

		assertEquals("Buy 2 tins of soup and get a loaf of bread for half price", entry.getKey());
		assertEquals(new BigDecimal("0.40"), entry.getValue());

		assertEquals(new BigDecimal("1.70"), basketService.getGrandTotal());
	}

	/**
	 * Ensure only 1 of the breads is only discounted
	 */
	public void test2SoupsAnd2Breads() {

		BasketPricingService basketService = new BasketPricingServiceImpl();
		basketService.priceBasket(new String[] { "Soup", "Soup", "Bread", "Bread" });
		assertEquals(new BigDecimal("2.90"), basketService.getSubTotal());
		assertEquals(1, basketService.getDiscounts().size());

		Map<String, BigDecimal> discounts = basketService.getDiscounts();
		Set<Entry<String, BigDecimal>> entrySet = discounts.entrySet();
		Entry<String, BigDecimal> entry = entrySet.iterator().next();

		assertEquals("Buy 2 tins of soup and get a loaf of bread for half price", entry.getKey());
		assertEquals(new BigDecimal("0.40"), entry.getValue());

		assertEquals(new BigDecimal("2.50"), basketService.getGrandTotal());
	}

	/**
	 * Ensure both breads are discounted by 40 pence each
	 */
	public void test5SoupsAnd2Breads() {

		BasketPricingService basketService = new BasketPricingServiceImpl();
		basketService.priceBasket(new String[] { "Soup", "Soup", "Bread", "Bread", "Soup", "Soup",
				"Soup" });
		assertEquals(new BigDecimal("4.85"), basketService.getSubTotal());
		assertEquals(1, basketService.getDiscounts().size());

		Map<String, BigDecimal> discounts = basketService.getDiscounts();
		Set<Entry<String, BigDecimal>> entrySet = discounts.entrySet();
		Entry<String, BigDecimal> entry = entrySet.iterator().next();

		assertEquals("Buy 2 tins of soup and get a loaf of bread for half price", entry.getKey());
		assertEquals(new BigDecimal("0.80"), entry.getValue());

		assertEquals(new BigDecimal("4.05"), basketService.getGrandTotal());
	}

	/**
	 * 3 apples, 4 soups and 2 loafs of bread Ensure the apples are all
	 * discounted by 10% and each of the breads are discounted by 50%
	 */
	public void test3ApplesAnd4SoupsAnd3Breads() {

		BasketPricingService basketService = new BasketPricingServiceImpl();
		basketService.priceBasket(new String[] { "Apples", "Apples", "Apples", "Soup", "Soup",
				"Soup", "Soup", "Bread", "Bread", "Bread" });
		assertEquals(new BigDecimal("8.00"), basketService.getSubTotal());
		assertEquals(2, basketService.getDiscounts().size());

		Map<String, BigDecimal> discounts = basketService.getDiscounts();

		BigDecimal applesDiscount = discounts.get("Apples 10% off");
		BigDecimal breadDiscount = discounts
				.get("Buy 2 tins of soup and get a loaf of bread for half price");

		assertEquals(new BigDecimal("0.30"), applesDiscount);
		assertEquals(new BigDecimal("0.80"), breadDiscount);

		assertEquals(new BigDecimal("6.90"), basketService.getGrandTotal());

	}
}
