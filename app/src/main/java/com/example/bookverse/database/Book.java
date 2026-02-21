package com.example.bookverse.database;

import java.io.Serializable;

public class Book extends Uid implements Serializable {
    private String name;
    private String image;
    private String category;
    private double price;
    private String description;

    public Book(){

    }

    public String getName() {
        return name;
    }

    public Book setName(String name) {
        this.name = name;
        return this;
    }

    public String getImage() {
        return image;
    }

    public Book setImage(String image) {
        this.image = image;
        return this;
    }

    public String getCategory() {
        return category;
    }

    public Book setCategory(String category) {
        this.category = category;
        return this;
    }

    public double getPrice() {
        return price;
    }

    public Book setPrice(double price) {
        this.price = price;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Book setDescription(String description) {
        this.description = description;
        return this;
    }
}
