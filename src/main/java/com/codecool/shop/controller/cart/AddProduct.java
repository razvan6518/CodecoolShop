package com.codecool.shop.controller.cart;

import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.model.Order;
import com.codecool.shop.model.Product;
import com.codecool.shop.service.CartService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "AddProduct", urlPatterns = {"/add"})
public class AddProduct extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        CartService cartService = new CartService(ProductDaoMem.getInstance());
        Product product = cartService.getProduct(Integer.parseInt(request.getParameter("id")));

        if (request.getSession().getAttribute("user") != null){
            Order.getInstance().addProduct(product);
        }
    }

}