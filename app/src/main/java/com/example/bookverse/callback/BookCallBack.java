package com.example.bookverse.callback;

import com.example.bookverse.database.Book;

import java.util.ArrayList;

public interface BookCallBack {
    void onFetchBooksComplete(ArrayList<Book> books);
}
