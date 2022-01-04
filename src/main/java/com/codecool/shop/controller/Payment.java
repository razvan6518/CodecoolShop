package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.model.LineItem;
import com.codecool.shop.model.Order;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@WebServlet(name = "Payment", urlPatterns = {"/payment"})
public class Payment extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Order order = Order.getInstance();
        List<LineItem> productsList = order.getProductsInCart();

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(request.getServletContext());
        WebContext context = new WebContext(request, response, request.getServletContext());

        BigDecimal totalPrice = new BigDecimal("0");
        for (LineItem item: productsList)
            totalPrice = totalPrice.add(item.getItem().getDefaultPrice().multiply(BigDecimal.valueOf(item.getQuantity() * 1.0)));
        context.setVariable("total_price", totalPrice);

        engine.process("product/payment.html", context, response.getWriter());
    }

}
