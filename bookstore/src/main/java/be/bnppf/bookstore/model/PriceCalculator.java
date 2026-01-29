package be.bnppf.bookstore.model;

public class PriceCalculator {

    public double calculate(ShoppingBasket basket) {
        int totalBooks = basket.size();
        int uniqueBooks = basket.countUniqueBooks();

        // Si tous les livres sont identiques, pas de remise
        if (uniqueBooks == 1) {
            return totalBooks * 50.0;
        }

        // Si 2 livres différents, 5% de remise
        if (uniqueBooks == 2 && totalBooks == 2) {
            return 2 * 50.0 * 0.95;
        }

        // Si 3 livres différents, 10% de remise
        if (uniqueBooks == 3 && totalBooks == 3) {
            return 3 * 50.0 * 0.90;
        }

        // Par défaut, pas de remise
        return totalBooks * 50.0;
    }
}