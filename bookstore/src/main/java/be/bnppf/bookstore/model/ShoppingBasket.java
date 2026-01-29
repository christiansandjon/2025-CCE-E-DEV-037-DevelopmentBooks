package be.bnppf.bookstore.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
}