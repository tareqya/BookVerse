package com.example.bookverse.callback;

import com.example.bookverse.database.Order;

import java.util.ArrayList;

public interface OrderCallBack {
    void onFetchOrdersComplete(ArrayList<Order> orders);
}
