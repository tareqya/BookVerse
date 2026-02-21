package com.example.bookverse.database;

import androidx.annotation.Nullable;

import com.example.bookverse.callback.BookCallBack;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class BookController {
    public static final String BOOKS = "Books";
    private FirebaseFirestore db;
    private BookCallBack bookCallBack;


    public BookController() {
        this.db = FirebaseFirestore.getInstance();
    }

    public void setBookCallBack(BookCallBack bookCallBack){
        this.bookCallBack = bookCallBack;
    }

    public void fetchBooks(){
        this.db.collection(BOOKS).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(value == null) return;
                ArrayList<Book> books = new ArrayList<>();
                for(int i = 0 ; i < value.getDocuments().size(); i++){
                    Book book = value.getDocuments().get(i).toObject(Book.class);
                    book.setUid(value.getDocuments().get(i).getId());
                    books.add(book);
                }
                bookCallBack.onFetchBooksComplete(books);
            }
        });
    }
}
