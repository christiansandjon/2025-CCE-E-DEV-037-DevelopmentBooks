package be.bnppf.bookstore.rest;

import be.bnppf.bookstore.core.model.Book;
import be.bnppf.bookstore.core.model.ShoppingBasket;
import be.bnppf.bookstore.core.service.PriceCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/bookstore")
public class BookstoreController {

    @Autowired
    private PriceCalculator priceCalculator;

    /**
     * Calculate the price of a basket of books.
     * 
     * POST /api/bookstore/calculate
     * Body: ["CLEAN_CODE", "CLEAN_CODE", "THE_CLEAN_CODER"]
     * 
     * @param bookNames list of book names
     * @return price and details
     */
    @PostMapping("/calculate")
    public ResponseEntity<PriceResponse> calculatePrice(@RequestBody List<String> bookNames) {
        ShoppingBasket basket = new ShoppingBasket();
        
        for (String bookName : bookNames) {
            try {
                Book book = Book.valueOf(bookName);
                basket.add(book);
            } catch (IllegalArgumentException e) {
                return ResponseEntity.badRequest()
                    .body(new PriceResponse(0.0, "Invalid book name: " + bookName, null));
            }
        }
        
        double price = priceCalculator.calculate(basket);
        
        return ResponseEntity.ok(new PriceResponse(
            price, 
            "Price calculated successfully", 
            basket.getBookCounts()
        ));
    }

    /**
     * Get list of available books.
     * 
     * GET /api/bookstore/books
     */
    @GetMapping("/books")
    public ResponseEntity<Book[]> getAvailableBooks() {
        return ResponseEntity.ok(Book.values());
    }

    /**
     * Health check endpoint.
     * 
     * GET /api/bookstore/health
     */
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Bookstore API is running");
    }

    // Response DTO
    public static class PriceResponse {
        private double price;
        private String message;
        private Map<Book, Integer> bookCounts;

        public PriceResponse(double price, String message, Map<Book, Integer> bookCounts) {
            this.price = price;
            this.message = message;
            this.bookCounts = bookCounts;
        }

        public double getPrice() { return price; }
        public void setPrice(double price) { this.price = price; }
        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }
        public Map<Book, Integer> getBookCounts() { return bookCounts; }
        public void setBookCounts(Map<Book, Integer> bookCounts) { this.bookCounts = bookCounts; }
    }
}