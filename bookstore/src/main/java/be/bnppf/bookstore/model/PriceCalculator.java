package be.bnppf.bookstore.model;

import java.util.*;

public class PriceCalculator {

    public double calculate(ShoppingBasket basket) {
        if (basket.isEmpty()) {
            return 0.0;
        }

        Map<Book, Integer> bookCounts = new HashMap<>(basket.getBookCounts());
        List<Integer> groups = new ArrayList<>();

        // Construire les groupes de manière gloutonne
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

        // Optimisation : transformer 5+3 en 4+4
        while (groups.contains(5) && groups.contains(3)) {
            groups.remove(Integer.valueOf(5));
            groups.remove(Integer.valueOf(3));
            groups.add(4);
            groups.add(4);
        }

        // Calculer le prix total
        double total = 0.0;
        for (int size : groups) {
            total += size * 50.0 * getDiscount(size);
        }

        return total;
    }

    private double getDiscount(int groupSize) {
        switch (groupSize) {
            case 2: return 0.95;
            case 3: return 0.90;
            case 4: return 0.80;
            case 5: return 0.75;
            default: return 1.0;
        }
    }
}