package be.bnppf.bookstore.model;

public class PriceCalculator {

    public double calculate(ShoppingBasket basket) {
        int totalBooks = basket.size();
        int uniqueBooks = basket.countUniqueBooks();

        // Si tous identiques, pas de remise
        if (uniqueBooks == 1) {
            return totalBooks * 50.0;
        }

        // Si tous différents, appliquer la remise correspondante
        if (uniqueBooks == totalBooks) {
            return totalBooks * 50.0 * getDiscount(uniqueBooks);
        }

        // Par défaut, pas de remise
        return totalBooks * 50.0;
    }

    private double getDiscount(int uniqueBooks) {
        switch (uniqueBooks) {
            case 2: return 0.95;  // 5% remise
            case 3: return 0.90;  // 10% remise
            case 4: return 0.80;  // 20% remise
            case 5: return 0.75;  // 25% remise
            default: return 1.0;  // Pas de remise
        }
    }
}