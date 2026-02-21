package com.example.bookverse.database;

import com.google.firebase.firestore.Exclude;

import java.io.Serializable;
import java.util.ArrayList;

public class Cart extends Uid implements Serializable {
    private ArrayList<BookCart> books;
    private String userId;
    public Cart(){
        books = new ArrayList<>();
    }

    public ArrayList<BookCart> getBooks() {
        return books;
    }

    public void setBooks(ArrayList<BookCart> books) {
        this.books = books;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void addBook(BookCart book){
        this.books.add(book);
    }

    @Exclude
    public double getTotalPrice(){
        double sum = 0;
        for(int i = 0 ; i < this.books.size(); i++) {
            sum += this.books.get(i).getPrice() * this.books.get(i).getQuantity();
        }
        return sum;
    }
}
