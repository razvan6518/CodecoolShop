package com.codecool.shop.controller.product;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.dao.implementation.SupplierDaoMem;
import com.codecool.shop.model.Product;
import com.codecool.shop.serialization.ProductSerialization;
import com.codecool.shop.service.ProductService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "GetProducts", urlPatterns = {"/products"})
public class GetProducts extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        ProductService productService = new ProductService(
                ProductDaoMem.getInstance(),
                ProductCategoryDaoMem.getInstance(),
                SupplierDaoMem.getInstance()
        );

        List<Product> productsList = productService.getAllProducts();

        Gson gson = new GsonBuilder().registerTypeAdapter(Product.class, new ProductSerialization()).serializeNulls().create();
        PrintWriter out = response.getWriter();

        out.println(gson.toJson(productsList));
    }
}