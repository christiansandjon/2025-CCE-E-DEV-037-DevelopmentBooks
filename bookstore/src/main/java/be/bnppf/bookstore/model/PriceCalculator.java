package be.bnppf.bookstore.model;

import java.util.*;

public class PriceCalculator {

    public double calculate(ShoppingBasket basket) {
        if (basket.isEmpty()) {
            return 0.0;
        }

        // Compter les livres
        Map<Book, Integer> bookCounts = basket.getBookCounts();

        double totalPrice = 0.0;

        // Tant qu'il reste des livres
        while (!bookCounts.isEmpty()) {
            // Former un groupe avec des livres différents
            int groupSize = 0;
            List<Book> toRemove = new ArrayList<>();

            for (Map.Entry<Book, Integer> entry : bookCounts.entrySet()) {
                Book book = entry.getKey();
                int count = entry.getValue();

                // Prendre un livre de ce type pour le groupe
                groupSize++;

                // Diminuer le compteur
                if (count == 1) {
                    toRemove.add(book);
                } else {
                    bookCounts.put(book, count - 1);
                }
            }

            // Supprimer les livres épuisés
            for (Book book : toRemove) {
                bookCounts.remove(book);
            }

            // Calculer le prix de ce groupe
            totalPrice += groupSize * 50.0 * getDiscount(groupSize);
        }

        return totalPrice;
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