package com.codecool.shop.dao;

import com.codecool.shop.model.Category;
import com.codecool.shop.model.Supplier;
import com.codecool.shop.model.Product;

import java.util.List;

public interface ProductDao {

    void add(Product product);
    Product find(int id);

    List<Product> getAll();
    List<Product> getBy(Supplier supplier);
    List<Product> getBy(Category productCategory);

}
