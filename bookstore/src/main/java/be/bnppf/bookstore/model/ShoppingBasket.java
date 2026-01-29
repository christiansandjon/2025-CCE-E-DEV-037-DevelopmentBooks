package be.bnppf.bookstore.model;

import java.util.ArrayList;
import java.util.List;

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
}