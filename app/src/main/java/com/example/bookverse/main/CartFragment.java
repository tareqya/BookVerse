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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bookverse.R;
import com.example.bookverse.adapter.BookAdapter;
import com.example.bookverse.adapter.CartAdapter;
import com.example.bookverse.callback.CartCallBack;
import com.example.bookverse.callback.OrderCallBack;
import com.example.bookverse.database.AccountController;
import com.example.bookverse.database.Book;
import com.example.bookverse.database.BookCart;
import com.example.bookverse.database.Cart;
import com.example.bookverse.database.CartController;
import com.example.bookverse.database.Order;
import com.example.bookverse.database.OrderController;
import com.google.android.gms.tasks.Task;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;


public class CartFragment extends Fragment {
    private RecyclerView fragCart_RV_books;
    private Button fragCart_BTN_orderNow;
    private TextView fragCart_TV_totalPrice;
    private Context context;
    private CartController cartController;
    private AccountController accountController;
    private OrderController orderController;
    private Cart cart;
    public CartFragment(Context context) {
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View view =inflater.inflate(R.layout.fragment_cart, container, false);
         findViews(view);
         mainFunction();
         return view;
    }

    private void findViews(View view) {
        fragCart_RV_books = view.findViewById(R.id.fragCart_RV_books);
        fragCart_BTN_orderNow = view.findViewById(R.id.fragCart_BTN_orderNow);
        fragCart_TV_totalPrice = view.findViewById(R.id.fragCart_TV_totalPrice);
    }

    private void mainFunction() {
        orderController = new OrderController();
        orderController.setOrderCallBack(new OrderCallBack() {
            @Override
            public void onFetchOrdersComplete(ArrayList<Order> orders) {

            }

            @Override
            public void onAddOrderComplete(Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(context, "Order created", Toast.LENGTH_SHORT).show();
                }else{
                    String err= task.getException().getMessage();
                    Toast.makeText(context, err, Toast.LENGTH_SHORT).show();
                }
            }
        });
        accountController = new AccountController();
        String uid = accountController.getCurrentUser().getUid();
        cartController = new CartController();
        cartController.setCartCallBack(new CartCallBack() {
            @Override
            public void onFetchCartComplete(Cart c) {
                if(c == null) {
                    cart = new Cart();
                }else{
                    cart = c ;
                }
                if(!cart.getBooks().isEmpty()){
                    String totalPrice = "Total Price: " + String.valueOf(cart.getTotalPrice()) + " ₪";
                    fragCart_TV_totalPrice.setText(totalPrice);
                }else {
                    fragCart_TV_totalPrice.setText("");
                }
                displayBooks(cart.getBooks());
            }

            @Override
            public void onCartRemoveComplete(Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(context, "Cart Removed", Toast.LENGTH_SHORT).show();
                    displayBooks(new ArrayList<>());
                }else{
                    String err = task.getException().getMessage();
                    Toast.makeText(context, err, Toast.LENGTH_SHORT).show();
                }
            }
        });

        cartController.fetchUserCart(uid);


        fragCart_BTN_orderNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // add new order
                if(cart == null || cart.getBooks().isEmpty()){
                    return;
                }
                Order order = new Order()
                        .setCart(cart)
                        .setUserId(uid)
                        .setStatus("Order Accepted")
                        .setOrderDate(new Date());
                orderController.addOrder(order);
                Toast.makeText(context, "Creating Order...", Toast.LENGTH_SHORT).show();
                // clean the cart
                cartController.removeCart(cart.getUid());

            }
        });
    }

    private void displayBooks(ArrayList<BookCart> books){
        CartAdapter bookAdapter = new CartAdapter(context, books);
        fragCart_RV_books.setLayoutManager(new GridLayoutManager(context, 2));
        fragCart_RV_books.setHasFixedSize(true);
        fragCart_RV_books.setItemAnimator(new DefaultItemAnimator());
        fragCart_RV_books.setAdapter(bookAdapter);
    }
}