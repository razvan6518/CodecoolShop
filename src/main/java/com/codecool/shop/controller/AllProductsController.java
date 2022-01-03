package com.codecool.shop.controller;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.dao.implementation.SupplierDaoMem;
import com.codecool.shop.model.Order;
import com.codecool.shop.service.ProductService;
import com.codecool.shop.config.TemplateEngineUtil;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = {"/"})
public class AllProductsController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        ProductDao productDataStore = ProductDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        SupplierDao supplierDao = SupplierDaoMem.getInstance();
        Order order = Order.getInstance();

        ProductService productService = new ProductService(productDataStore,productCategoryDataStore, supplierDao);

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());

        context.setVariable("category", "All products");

        List<Object> categories = new ArrayList<>(productCategoryDataStore.getAll());
        context.setVariable("categories", categories);

        List<Object> suppliers = new ArrayList<>(supplierDao.getAll());
        context.setVariable("suppliers", suppliers);

        List<Object> products = new ArrayList<>();
        productCategoryDataStore.getAll().forEach(productCategory -> products.addAll(productService.getProductsForCategory(productCategory.getId())));

        context.setVariable("products", products);
        context.setVariable("productsInCart", order.getNrOfProducts());

        engine.process("product/index.html", context, resp.getWriter());
    }

}
