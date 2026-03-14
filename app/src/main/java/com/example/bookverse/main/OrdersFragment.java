package com.example.bookverse.main;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bookverse.R;
import com.example.bookverse.adapter.CartAdapter;
import com.example.bookverse.adapter.OrderAdapter;
import com.example.bookverse.callback.OrderCallBack;
import com.example.bookverse.database.AccountController;
import com.example.bookverse.database.BookCart;
import com.example.bookverse.database.Order;
import com.example.bookverse.database.OrderController;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;

public class OrdersFragment extends Fragment {
    private RecyclerView fragOrder_RV_orders;
    private AccountController accountController;
    private OrderController orderController;
    private Context context;
    public OrdersFragment(Context context) {
       this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_orders, container, false);
        findViews(view);
        mainFunction();
        return view;
    }

    private void findViews(View view) {
        fragOrder_RV_orders = view.findViewById(R.id.fragOrder_RV_orders);
    }

    private void mainFunction() {
        accountController = new AccountController();
        String uid = accountController.getCurrentUser().getUid();
        orderController = new OrderController();
        orderController.setOrderCallBack(new OrderCallBack() {
            @Override
            public void onFetchOrdersComplete(ArrayList<Order> orders) {
                displayBooks(orders);
            }

            @Override
            public void onAddOrderComplete(Task<Void> task) {

            }
        });
        orderController.fetchUserOrders(uid);

    }

    private void displayBooks(ArrayList<Order> orders){
        OrderAdapter orderAdapter = new OrderAdapter(context, orders);
        fragOrder_RV_orders.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        fragOrder_RV_orders.setHasFixedSize(true);
        fragOrder_RV_orders.setItemAnimator(new DefaultItemAnimator());
        fragOrder_RV_orders.setAdapter(orderAdapter);
    }

}