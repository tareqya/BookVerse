package com.example.bookverse.database;

import java.io.Serializable;
import java.util.Date;

public class Order extends Uid implements Serializable {
    private String userId;
    private Cart cart;
    private Date orderDate;
    private String status; // shipped arrived

    public Order(){}

    public String getUserId() {
        return userId;
    }

    public Order setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public Cart getCart() {
        return cart;
    }

    public Order setCart(Cart cart) {
        this.cart = cart;
        return this;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public Order setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public Order setStatus(String status) {
        this.status = status;
        return this;
    }
}
