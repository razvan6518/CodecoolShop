package com.codecool.shop.controller;

import com.codecool.shop.model.LineItem;
import com.codecool.shop.model.Order;
import com.codecool.shop.model.Product;
import com.codecool.shop.serialization.ProductSerialization;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "Cart", urlPatterns = {"/get_cart"})
public class Cart extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Order order = Order.getInstance();
        List<LineItem> productsList = order.getProductsInCart();

        Gson gson = new GsonBuilder().registerTypeAdapter(Product.class, new ProductSerialization()).serializeNulls().create();
        PrintWriter out = response.getWriter();

        out.println(gson.toJson(productsList));
    }

}
