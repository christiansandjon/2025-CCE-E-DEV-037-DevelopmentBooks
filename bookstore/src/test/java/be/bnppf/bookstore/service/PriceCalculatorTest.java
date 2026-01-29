package be.bnppf.bookstore.service;

import be.bnppf.bookstore.model.PriceCalculator;
import be.bnppf.bookstore.model.ShoppingBasket;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PriceCalculatorTest {

    @Test
    public void emptyBasket_shouldCostZero() {
        // Given
        ShoppingBasket basket = new ShoppingBasket();
        PriceCalculator calculator = new PriceCalculator();
        
        // When
        double price = calculator.calculate(basket);
        
        // Then
        assertEquals(0.0, price);
    }
}