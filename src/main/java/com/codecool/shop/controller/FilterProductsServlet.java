package com.codecool.shop.controller;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.dao.implementation.SupplierDaoMem;
import com.codecool.shop.model.Product;
import com.codecool.shop.serialization.ProductSerialization;
import com.codecool.shop.service.ProductService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@WebServlet(name = "ProductsServlet", urlPatterns = {"/filter_products"})
public class FilterProductsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        ProductDao productDao = ProductDaoMem.getInstance();
        ProductCategoryDao categoryDao = ProductCategoryDaoMem.getInstance();
        SupplierDao supplierDao = SupplierDaoMem.getInstance();

        ProductService productService = new ProductService(productDao, categoryDao, supplierDao);
        int maxPrice;
        Set<Product> productsList;
        if (!request.getParameter("by").equals("")){
            productsList = new HashSet<>();
            String[] filters = request.getParameter("by").split(",");
            for (String filter: filters){
                if (filter.split("_")[0].equals("category")){
                    productsList.addAll(productService.getProductsForCategory(Integer.parseInt(filter.split("_")[1])));
                }
                if (filter.split("_")[0].equals("supplier")){
                    productsList.addAll(productService.getProductsForSupplier(Integer.parseInt(filter.split("_")[1])));
                }
            }
            maxPrice = Integer.parseInt(request.getParameter("maxPrice"));
            productsList = productsList.stream().filter(product -> product.getDefaultPrice().setScale(0, RoundingMode.UP).intValueExact() < maxPrice).collect(Collectors.toSet());
        }else{
            productsList = new HashSet<>(productService.getAllProducts());
            maxPrice = Integer.parseInt(request.getParameter("maxPrice"));
            productsList = productsList.stream().filter(product -> product.getDefaultPrice().setScale(0, RoundingMode.UP).intValueExact() < maxPrice).collect(Collectors.toSet());
        }

        Gson gson = new GsonBuilder().registerTypeAdapter(Product.class, new ProductSerialization()).serializeNulls().create();
        PrintWriter out = response.getWriter();

        out.println(gson.toJson(productsList));
    }
}