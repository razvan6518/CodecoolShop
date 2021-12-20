package com.codecool.shop.controller;

import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.model.Order;
import com.codecool.shop.model.Product;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ProductAdd", urlPatterns = {"/add"})
public class ProductAdd extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        ProductDao productDao = ProductDaoMem.getInstance();
        Product toBuy = productDao.find(Integer.parseInt(request.getParameter("id")));
        Order order = Order.getInstance();
        order.addProduct(toBuy);
        response.sendRedirect("http://localhost:8888");
    }

}