package com.example.bookverse.database;

import androidx.annotation.Nullable;

import com.example.bookverse.callback.OrderCallBack;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class OrderController {
    private OrderCallBack orderCallBack;
    public static final String ORDERS = "Orders";
    private FirebaseFirestore db;
    public OrderController(){
        this.db = FirebaseFirestore.getInstance();
    }

    public void setOrderCallBack(OrderCallBack orderCallBack){
        this.orderCallBack = orderCallBack;
    }

    public void addOrder(Order order){
        db.collection(ORDERS).document().set(order);
    }

    public void fetchUserOrders(String userId){
        this.db.collection(ORDERS).whereEqualTo("userId", userId).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(value == null) return;
                ArrayList<Order> orders = new ArrayList<>();
                for(int i = 0; i < value.getDocuments().size(); i++){
                    DocumentSnapshot doc = value.getDocuments().get(i);
                    Order order = doc.toObject(Order.class);
                    order.setUid(doc.getId());
                    orders.add(order);
                }

                orderCallBack.onFetchOrdersComplete(orders);

            }
        });
    }
}
