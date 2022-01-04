package com.codecool.shop.dao;

import com.codecool.shop.model.Category;

import java.util.List;

public interface ProductCategoryDao {

    void add(Category category);
    Category find(int id);

    List<Category> getAll();

}
