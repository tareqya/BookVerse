package com.example.bookverse.database;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.bookverse.callback.CartCallBack;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QuerySnapshot;

public class CartController {
    public static final String CARTS = "Carts";
    private FirebaseFirestore db;
    private CartCallBack cartCallBack;
    private ListenerRegistration cartListenerRegistration;

    public CartController(){
        this.db = FirebaseFirestore.getInstance();
    }

    public void setCartCallBack(CartCallBack cartCallBack){
        this.cartCallBack = cartCallBack;
    }

    public void fetchUserCart(String userId){
        removeListener();
        cartListenerRegistration = this.db.collection(CARTS)
                .whereEqualTo("userId", userId)
                .addSnapshotListener((value, error) -> {

                    if (value == null) return;

                    if (value.getDocuments().size() > 0) {
                        Cart cart = value.getDocuments().get(0).toObject(Cart.class);
                        cart.setUid(value.getDocuments().get(0).getId());
                        cartCallBack.onFetchCartComplete(cart);
                    } else {
                        cartCallBack.onFetchCartComplete(null);
                    }
                });
    }

    public void removeListener(){
        if(cartListenerRegistration != null){
            cartListenerRegistration.remove();
            cartListenerRegistration = null;
        }
    }

    public void updateCart(Cart cart){
        if(cart.getUid() != null){
            this.db.collection(CARTS).document(cart.getUid()).set(cart);
            return;
        }
        this.db.collection(CARTS).document().set(cart);
    }

    public void removeCart(String cartId){
        this.db.collection(CARTS).document(cartId).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                cartCallBack.onCartRemoveComplete(task);
            }
        });
    }
}
