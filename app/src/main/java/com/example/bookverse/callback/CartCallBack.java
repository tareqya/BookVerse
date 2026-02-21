package com.example.bookverse.callback;

import com.example.bookverse.database.Cart;

public interface CartCallBack {
    void onFetchCartComplete(Cart cart);
}
