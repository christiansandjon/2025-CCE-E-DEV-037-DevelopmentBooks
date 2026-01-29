package be.bnppf.bookstore.model;

public class PriceCalculator {

    public double calculate(ShoppingBasket basket) {
        return basket.size() * 50.0;
    }
}