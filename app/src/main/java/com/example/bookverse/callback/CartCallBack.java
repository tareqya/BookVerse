package com.example.bookverse.callback;

import com.example.bookverse.database.Cart;
import com.google.android.gms.tasks.Task;

public interface CartCallBack {
    void onFetchCartComplete(Cart cart);
    void onCartRemoveComplete(Task<Void> task);

}
