package be.bnppf.bookstore.core.service;

import be.bnppf.bookstore.core.model.ShoppingBasket;

/**
 * Service for calculating the price of a shopping basket with applicable discounts.
 */
public interface PriceCalculator {
    
    /**
     * Calculates the total price of the basket with optimal grouping and discounts.
     * 
     * @param basket the shopping basket
     * @return the total price in EUR
     */
    double calculate(ShoppingBasket basket);
}