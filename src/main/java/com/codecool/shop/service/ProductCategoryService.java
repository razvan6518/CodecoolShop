package com.codecool.shop.service;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.model.Category;

import java.util.List;

public class ProductCategoryService {

    private ProductCategoryDao productCategoryDao;

    public ProductCategoryService(ProductCategoryDao productCategoryDao, ProductDao productDao) {
        this.productCategoryDao = productCategoryDao;
    }

    public List<Category> getAllCategories(){
        return productCategoryDao.getAll();
    }

}
