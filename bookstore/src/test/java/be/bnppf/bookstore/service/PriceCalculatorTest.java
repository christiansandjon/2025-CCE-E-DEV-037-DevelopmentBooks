package be.bnppf.bookstore.service;

import be.bnppf.bookstore.model.Book;
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

    @Test
    public void oneBook_shouldCost50() {
        // Given
        ShoppingBasket basket = new ShoppingBasket();
        basket.add(Book.CLEAN_CODE);
        PriceCalculator calculator = new PriceCalculator();

        // When
        double price = calculator.calculate(basket);

        // Then
        assertEquals(50.0, price);
    }

    @Test
    public void twoIdenticalBooks_shouldCost100() {
        // Given
        ShoppingBasket basket = new ShoppingBasket();
        basket.add(Book.CLEAN_CODE);
        basket.add(Book.CLEAN_CODE);
        PriceCalculator calculator = new PriceCalculator();

        // When
        double price = calculator.calculate(basket);

        // Then
        assertEquals(100.0, price);
    }

    @Test
    public void twoDifferentBooks_shouldCost95() {
        // Given
        ShoppingBasket basket = new ShoppingBasket();
        basket.add(Book.CLEAN_CODE);
        basket.add(Book.THE_CLEAN_CODER);
        PriceCalculator calculator = new PriceCalculator();

        // When
        double price = calculator.calculate(basket);

        // Then
        assertEquals(95.0, price); // 2 * 50 * 0.95 = 95
    }

    @Test
    public void threeDifferentBooks_shouldCost135() {
        // Given
        ShoppingBasket basket = new ShoppingBasket();
        basket.add(Book.CLEAN_CODE);
        basket.add(Book.THE_CLEAN_CODER);
        basket.add(Book.CLEAN_ARCHITECTURE);
        PriceCalculator calculator = new PriceCalculator();

        // When
        double price = calculator.calculate(basket);

        // Then
        assertEquals(135.0, price); // 3 * 50 * 0.90 = 135
    }

    @Test
    public void fourDifferentBooks_shouldCost160() {
        // Given
        ShoppingBasket basket = new ShoppingBasket();
        basket.add(Book.CLEAN_CODE);
        basket.add(Book.THE_CLEAN_CODER);
        basket.add(Book.CLEAN_ARCHITECTURE);
        basket.add(Book.TEST_DRIVEN_DEVELOPMENT);
        PriceCalculator calculator = new PriceCalculator();

        // When
        double price = calculator.calculate(basket);

        // Then
        assertEquals(160.0, price); // 4 * 50 * 0.80 = 160
    }

    @Test
    public void fiveDifferentBooks_shouldCost187point5() {
        // Given
        ShoppingBasket basket = new ShoppingBasket();
        basket.add(Book.CLEAN_CODE);
        basket.add(Book.THE_CLEAN_CODER);
        basket.add(Book.CLEAN_ARCHITECTURE);
        basket.add(Book.TEST_DRIVEN_DEVELOPMENT);
        basket.add(Book.WORKING_EFFECTIVELY_WITH_LEGACY_CODE);
        PriceCalculator calculator = new PriceCalculator();

        // When
        double price = calculator.calculate(basket);

        // Then
        assertEquals(187.5, price); // 5 * 50 * 0.75 = 187.5
    }
}