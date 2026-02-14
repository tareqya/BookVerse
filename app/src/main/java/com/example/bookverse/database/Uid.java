package com.example.bookverse.database;

import com.google.firebase.firestore.Exclude;

import java.io.Serializable;

public class Uid implements Serializable {
    private String id;
    public Uid(){}

    @Exclude
    public String getUid() {
        return id;
    }
    public void setUid(String id) {
        this.id = id;
    }

}
