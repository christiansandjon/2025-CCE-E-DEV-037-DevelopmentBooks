package be.bnppf.bookstore.core.service.impl;

import be.bnppf.bookstore.core.model.Book;
import be.bnppf.bookstore.core.model.ShoppingBasket;
import be.bnppf.bookstore.core.service.PriceCalculator;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Implementation of PriceCalculator using a greedy algorithm with optimization.
 * 
 * The algorithm builds groups of different books and applies the appropriate discount.
 * It then optimizes by transforming 5+3 groups into 4+4 groups for better pricing.
 */
@Service
public class PriceCalculatorImpl implements PriceCalculator {

    @Override
    public double calculate(ShoppingBasket basket) {
        if (basket.isEmpty()) {
            return 0.0;
        }

        Map<Book, Integer> bookCounts = new HashMap<>(basket.getBookCounts());
        List<Integer> groups = new ArrayList<>();

        // Build groups greedily
        while (!bookCounts.isEmpty()) {
            int groupSize = 0;
            List<Book> toRemove = new ArrayList<>();

            for (Map.Entry<Book, Integer> entry : bookCounts.entrySet()) {
                groupSize++;
                int count = entry.getValue();

                if (count == 1) {
                    toRemove.add(entry.getKey());
                } else {
                    bookCounts.put(entry.getKey(), count - 1);
                }
            }

            toRemove.forEach(bookCounts::remove);
            groups.add(groupSize);
        }

        // Optimization: transform 5+3 into 4+4
        while (groups.contains(5) && groups.contains(3)) {
            groups.remove(Integer.valueOf(5));
            groups.remove(Integer.valueOf(3));
            groups.add(4);
            groups.add(4);
        }

        // Calculate total price
        return calculateTotalPrice(groups);
    }

    /**
     * Calculates the total price for the given groups with applicable discounts.
     * 
     * @param groups list of group sizes
     * @return total price in EUR
     */
    private double calculateTotalPrice(List<Integer> groups) {
        double total = 0.0;
        for (int size : groups) {
            total += size * 50.0 * getDiscount(size);
        }
        return total;
    }

    /**
     * Returns the discount multiplier for a given group size.
     * 
     * @param groupSize number of different books in the group
     * @return discount multiplier (e.g., 0.95 for 5% discount)
     */
    private double getDiscount(int groupSize) {
        switch (groupSize) {
            case 2: return 0.95;  // 5% discount
            case 3: return 0.90;  // 10% discount
            case 4: return 0.80;  // 20% discount
            case 5: return 0.75;  // 25% discount
            default: return 1.0;  // No discount
        }
    }
}