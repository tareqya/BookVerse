package com.example.bookverse.callback;

import com.example.bookverse.database.Order;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;

public interface OrderCallBack {
    void onFetchOrdersComplete(ArrayList<Order> orders);
    void onAddOrderComplete( Task<Void> task);
}
