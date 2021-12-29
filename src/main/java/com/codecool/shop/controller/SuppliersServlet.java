package com.codecool.shop.controller;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.dao.implementation.SupplierDaoMem;
import com.codecool.shop.model.Category;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.Supplier;
import com.codecool.shop.serialization.CategorySerialization;
import com.codecool.shop.serialization.ProductSerialization;
import com.codecool.shop.serialization.SupplierSerialization;
import com.codecool.shop.service.CategoryService;
import com.codecool.shop.service.SupplierService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;

@WebServlet(name = "SuppliersServlet", urlPatterns = {"/suppliers"})
public class SuppliersServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        SupplierDao supplierDao = SupplierDaoMem.getInstance();

        SupplierService supplierService = new SupplierService(supplierDao);
        List<Supplier> suppliers = supplierService.getAllSuppliers();

        Gson gson = new GsonBuilder().registerTypeAdapter(Supplier.class, new SupplierSerialization()).serializeNulls().create();
        PrintWriter out = response.getWriter();

        out.println(gson.toJson(suppliers));
    }
}