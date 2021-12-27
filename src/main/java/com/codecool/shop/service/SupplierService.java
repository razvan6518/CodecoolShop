package com.codecool.shop.service;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.model.Category;
import com.codecool.shop.model.Supplier;

import java.util.HashMap;
import java.util.List;

public class SupplierService {

    private SupplierDao supplierDao;

    public SupplierService(SupplierDao supplierDao) {
        this.supplierDao = supplierDao;
    }

    public List<Supplier> getAllSuppliers(){
        return supplierDao.getAll();
    }

}
