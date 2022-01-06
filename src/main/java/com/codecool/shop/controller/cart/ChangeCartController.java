package com.codecool.shop.controller.cart;

import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.model.OrderData;
import com.codecool.shop.service.CartService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "ChangeCartController", urlPatterns = {"/change_cart", "/add_orderData"})
public class ChangeCartController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response){
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        CartService cartService = new CartService(ProductDaoMem.getInstance());

        switch (request.getServletPath()){
            case "/change_cart":
                String productId = request.getParameter("id");
                String productQuantity = request.getParameter("quantity");
                cartService.changeProductQuantity(productId, productQuantity);
                break;
            case "/add_orderData":
                cartService.addOrderData(
                        new OrderData(request.getParameter("fname"),
                        request.getParameter("lname"),
                        request.getParameter("email"),
                        request.getParameter("phone_number"),
                        request.getParameter("billing_address"),
                        request.getParameter("shipping_address"))
                );
        }
    }
}