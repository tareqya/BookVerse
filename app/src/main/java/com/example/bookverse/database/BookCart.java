package com.example.bookverse.database;

import java.io.Serializable;

public class BookCart extends Book implements Serializable {
    private int quantity;
    private String bookId;

    public BookCart(){

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
