package com.codecool.shop.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Order {

    private HashMap<Product, Integer> productsInCart;
    private int id;
    private static Order instance = null;

    public static Order getInstance(){
        if (instance == null)
            instance = new Order();
        return instance;
    }

    private Order() {
        productsInCart = new HashMap<>();
    }

    public void addProduct(Product product){
        if (this.productsInCart.containsKey(product)){
            int count = this.productsInCart.get(product);
            this.productsInCart.put(product, count+1);
        } else {
            this.productsInCart.put(product, 1);
        }
    }

    public HashMap<Product, Integer> getProductsInCart() {
        return productsInCart;
    }
}
