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

@WebServlet(name = "CartChange", urlPatterns = {"/change_cart", "/add_orderData"})
public class CartChange extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Order order = Order.getInstance();

        if (request.getServletPath().equals("/change_cart")){
            ProductDao productDao = ProductDaoMem.getInstance();
            Product product = productDao.find(Integer.parseInt(request.getParameter("id")));
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            order.setProductQuantity(product, quantity);
        }
        if (request.getServletPath().equals("/add_orderData")){
            order.setOrderData(request.getParameter("fname"),
                    request.getParameter("lname"),
                    request.getParameter("email"),
                    request.getParameter("phone_number"),
                    request.getParameter("billing_address"),
                    request.getParameter("shipping_address"));
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

//        ProductDao productDao = ProductDaoMem.getInstance();
//        Product product = productDao.find(Integer.parseInt(request.getParameter("id")));
//        int quantity = Integer.parseInt(request.getParameter("quantity"));
//        Order order = Order.getInstance();
//        order.setProductQuantity(product, quantity);
        response.sendRedirect("http://localhost:8888");
    }
}