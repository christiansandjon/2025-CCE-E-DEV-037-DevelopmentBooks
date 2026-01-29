package be.bnppf.bookstore.model;

import java.util.*;

public class ShoppingBasket {
    
    private List<Book> books;
    
    public ShoppingBasket() {
        this.books = new ArrayList<>();
    }
    
    public void add(Book book) {
        books.add(book);
    }
    
    public int size() {
        return books.size();
    }
    
    public boolean isEmpty() {
        return books.isEmpty();
    }

    public int countUniqueBooks() {
        Set<Book> uniqueBooks = new HashSet<>(books);
        return uniqueBooks.size();
    }

    public Map<Book, Integer> getBookCounts() {
        Map<Book, Integer> counts = new HashMap<>();
        for (Book book : books) {
            counts.put(book, counts.getOrDefault(book, 0) + 1);
        }
        return counts;
    }
}