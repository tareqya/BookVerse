package com.example.bookverse.database;

import androidx.annotation.Nullable;

import com.example.bookverse.callback.CartCallBack;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

public class CartController {
    public static final String CARTS = "Carts";
    private FirebaseFirestore db;
    private CartCallBack cartCallBack;

    public CartController(){
        this.db = FirebaseFirestore.getInstance();
    }

    public void setCartCallBack(CartCallBack cartCallBack){
        this.cartCallBack = cartCallBack;
    }

    public void fetchUserCart(String userId){
        this.db.collection(CARTS).whereEqualTo("userId", userId).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(value == null) return;
                if(value.getDocuments().size() > 0){
                    Cart cart = value.getDocuments().get(0).toObject(Cart.class);
                    cart.setUid(value.getDocuments().get(0).getId());
                    cartCallBack.onFetchCartComplete(cart);
                }
            }
        });
    }

    public void updateCart(Cart cart){
        if(cart.getUid() != null){
            this.db.collection(CARTS).document(cart.getUid()).set(cart);
            return;
        }
        this.db.collection(CARTS).document().set(cart);
    }

}
