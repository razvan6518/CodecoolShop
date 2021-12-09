package com.codecool.shop.model;

import java.util.ArrayList;
import java.util.List;

public class Order {

    private List<Product> productsInCart;
    private int id;
    private static Order instance = null;

    public static Order getInstance(){
        if (instance == null)
            instance = new Order();
        return instance;
    }

    private Order() {
        productsInCart = new ArrayList<>();
    }

    public void addProduct(Product product){
        this.productsInCart.add(product);
    }

    public List<Product> getProductsInCart() {
        return productsInCart;
    }
}
