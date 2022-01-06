package com.codecool.shop.controller;

import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.dao.implementation.SupplierDaoMem;
import com.codecool.shop.model.*;
import com.codecool.shop.service.ProductService;
import com.codecool.shop.config.TemplateEngineUtil;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(urlPatterns = {"/"})
public class ProductController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(request.getServletContext());
        WebContext context = new WebContext(request, response, request.getServletContext());

        ProductService productService = new ProductService(
                ProductDaoMem.getInstance(),
                ProductCategoryDaoMem.getInstance(),
                SupplierDaoMem.getInstance()
        );

        context.setVariable("category", "All products");
        context.setVariable("categories", productService.getAllCategories());
        context.setVariable("suppliers", productService.getAllSuppliers());
        context.setVariable("products", productService.getAllProducts());
        context.setVariable("productsInCart", Order.getInstance().getNrOfProducts());
        context.setVariable("user", (User) session.getAttribute("user"));

        engine.process("product/index.html", context, response.getWriter());
    }
}
