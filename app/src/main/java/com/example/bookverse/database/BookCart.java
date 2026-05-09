package com.example.bookverse.database;

import com.google.firebase.firestore.Exclude;

import java.io.Serializable;

public class BookCart extends Book implements Serializable {
    private int quantity;
    private String bookId;

    public BookCart(){

    }

    @Exclude
    @Override
    public String getImage() {
        return super.getImage();
    }

    public int getQuantity(){
        return quantity;
    }

    public void setQuantity(int quantity){
        this.quantity = quantity;
    }

    public void setBookId(String bookId){
        this.bookId = bookId;
    }

    public String getBookId(){
        return bookId;
    }
}
