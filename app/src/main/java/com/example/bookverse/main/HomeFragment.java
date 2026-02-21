package com.example.bookverse.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Spinner;

import com.example.bookverse.R;
import com.example.bookverse.adapter.BookAdapter;
import com.example.bookverse.callback.BookCallBack;
import com.example.bookverse.database.Book;
import com.example.bookverse.database.BookController;

import java.util.ArrayList;


public class HomeFragment extends Fragment {
    public static final String BOOK_KEY = "book";
    public static final String [] CATEGORIES = {};
    private Context context;
    private RecyclerView fragHome_RV_books;
    private SearchView fragHome_SV_search;
    private Spinner fragHome_SP_category;
    private BookController bookController;
    private ArrayList<Book> allBooks;

    public HomeFragment(Context context) {
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        findViews(view);
        mainFunction();
        return view;
    }
    private void findViews(View view) {
        fragHome_RV_books = view.findViewById(R.id.fragHome_RV_books);
        fragHome_SV_search = view.findViewById(R.id.fragHome_SV_search);
        fragHome_SP_category = view.findViewById(R.id.fragHome_SP_category);
    }
    private void mainFunction() {
        allBooks = new ArrayList<>();
        bookController = new BookController();
        bookController.setBookCallBack(new BookCallBack() {
            @Override
            public void onFetchBooksComplete(ArrayList<Book> books) {
                allBooks = books;
                displayBooks(books);
            }
        });

        bookController.fetchBooks();

        fragHome_SV_search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String s) {
                if(s.isEmpty()){
                    displayBooks(allBooks);
                }
                ArrayList<Book> books = new ArrayList<>();
                for(int i = 0 ; i < allBooks.size(); i++){
                    if(allBooks.get(i).getName().toLowerCase().startsWith(s.toLowerCase())){
                        books.add(allBooks.get(i));
                    }
                }
                displayBooks(books);
                return true;
            }

            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }
        });
    }

    private void displayBooks(ArrayList<Book> books){
        BookAdapter bookAdapter = new BookAdapter(context, books);
        bookAdapter.OnBookClickListener(new BookAdapter.OnBookClickListener() {
            @Override
            public void onBookClick(Book book) {
                Intent intent = new Intent(context, BookActivity.class);
                intent.putExtra(BOOK_KEY, book);
                startActivity(intent);
            }
        });
        fragHome_RV_books.setLayoutManager(new GridLayoutManager(context, 2));
        fragHome_RV_books.setHasFixedSize(true);
        fragHome_RV_books.setItemAnimator(new DefaultItemAnimator());
        fragHome_RV_books.setAdapter(bookAdapter);
    }

}