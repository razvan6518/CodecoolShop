package com.codecool.shop.service;

import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.model.Order;
import com.codecool.shop.model.OrderData;
import com.codecool.shop.model.Product;

public class CartService {

    private ProductDao productDao;

    public CartService(ProductDao productDao) {
        this.productDao = productDao;
    }

    public void changeProductQuantity(String productId, String productQuantity){
        Product product = productDao.find(Integer.parseInt(productId));
        int quantity = Integer.parseInt(productQuantity);
        Order.getInstance().setProductQuantity(product, quantity);
    }

    public void addOrderData(OrderData orderData){
        Order.getInstance().setOrderData(orderData);
    }

    public Product getProduct(int id){
        return this.productDao.find(id);
    }
}
