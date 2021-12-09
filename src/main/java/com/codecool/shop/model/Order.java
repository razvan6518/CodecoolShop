package com.codecool.shop.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Order {

//    private HashMap<Product, Integer> productsInCart;
//    private int id;

    private List<LineItem> items;

    private static Order instance = null;

    public static Order getInstance(){
        if (instance == null)
            instance = new Order();
        return instance;
    }

    private Order() {
//        productsInCart = new HashMap<>();
        items = new ArrayList<>();
    }

    public void addProduct(Product product){
        if (this.items.stream().anyMatch(lineItem -> lineItem.getItem().name.equals(product.getName()))){
            this.items.stream().filter(lineItem -> lineItem.getItem().name.equals(product.getName())).collect(Collectors.toList()).get(0).addQuantity(1);
        } else {
            items.add(new LineItem(product));
        }
    }

    public List<LineItem> getProductsInCart() {
        return items;
    }

}

