package com.codecool.shop.controller.product;

import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.model.Category;
import com.codecool.shop.serialization.CategorySerialization;
import com.codecool.shop.service.ProductCategoryService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "GetProductCategories", urlPatterns = {"/categories"})
public class GetProductCategories extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        ProductCategoryService categoryService = new ProductCategoryService(
                ProductCategoryDaoMem.getInstance(),
                ProductDaoMem.getInstance()
        );

        List<Category> categories = categoryService.getAllCategories();

        Gson gson = new GsonBuilder().registerTypeAdapter(Category.class, new CategorySerialization()).serializeNulls().create();
        PrintWriter out = response.getWriter();

        out.println(gson.toJson(categories));
    }
}