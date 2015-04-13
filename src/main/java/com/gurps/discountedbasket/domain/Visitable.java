package com.gurps.discountedbasket.domain;

/**
 * Visitor pattern.
 * 
 * @author gurps
 *
 */
public interface Visitable {

	public void accept(final BasketVisitor visitor);

}
