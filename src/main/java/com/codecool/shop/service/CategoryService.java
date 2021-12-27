package com.codecool.shop.service;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.model.Category;
import com.codecool.shop.model.Product;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class CategoryService {

    private ProductCategoryDao productCategoryDao;
    private ProductDao productDao;

    public CategoryService(ProductCategoryDao productCategoryDao, ProductDao productDao) {
        this.productCategoryDao = productCategoryDao;
        this.productDao = productDao;
    }

    public HashMap<String, Integer> getAllCategoriesWithNrOfItems(){
        HashMap<String, Integer> result = new HashMap<>();
        for (Category category: productCategoryDao.getAll())
            result.put(category.getName(), productDao.getBy(category).size());
        return result;
    }

}
