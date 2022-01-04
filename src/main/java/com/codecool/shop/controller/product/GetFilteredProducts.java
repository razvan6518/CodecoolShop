package com.codecool.shop.controller.product;

import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.dao.implementation.SupplierDaoMem;
import com.codecool.shop.model.Product;
import com.codecool.shop.serialization.ProductSerialization;
import com.codecool.shop.service.ProductService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@WebServlet(name = "GetFilteredProducts", urlPatterns = {"/filter_products"})
public class GetFilteredProducts extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        ProductService productService = new ProductService(
                ProductDaoMem.getInstance(),
                ProductCategoryDaoMem.getInstance(),
                SupplierDaoMem.getInstance()
        );

        BigDecimal maxPrice;
        Set<Product> productsList;
        boolean categoryFilter = false;
        boolean supplierFilter = false;

        //TODO: Refactor !!!
        if (!request.getParameter("by").equals("")){
            productsList = new HashSet<>();
            String[] filters = request.getParameter("by").split(",");
            for (String filter: filters){
                if (filter.split("_")[0].equals("category")){
                    categoryFilter = true;
                    productsList.addAll(productService.getProductsForCategory(Integer.parseInt(filter.split("_")[1])));
                }
            }
            if (!categoryFilter){
                productsList = new HashSet<>(productService.getAllProducts());
            }
            Set<Product> productsFilteredBySuppliers = new HashSet<>();
            for (String filter: filters){
                if (filter.split("_")[0].equals("supplier")){
                    supplierFilter = true;
                    productsFilteredBySuppliers.addAll(productsList.stream().filter(product -> product.getSupplier().getId() == Integer.parseInt(filter.split("_")[1])).collect(Collectors.toSet()));
                }
            }
            if (supplierFilter){
                productsList = productsFilteredBySuppliers;
            }
            maxPrice = BigDecimal.valueOf(Double.parseDouble(request.getParameter("maxPrice")));
            productsList = productsList.stream().filter(product -> product.getDefaultPrice().compareTo(maxPrice) <= 0).collect(Collectors.toSet());
        }else{
            productsList = new HashSet<>(productService.getAllProducts());
            maxPrice = BigDecimal.valueOf(Double.parseDouble(request.getParameter("maxPrice")));
            productsList = productsList.stream().filter(product -> product.getDefaultPrice().compareTo(maxPrice) <= 0).collect(Collectors.toSet());
        }

        Gson gson = new GsonBuilder().registerTypeAdapter(Product.class, new ProductSerialization()).serializeNulls().create();
        PrintWriter out = response.getWriter();

        out.println(gson.toJson(productsList));
    }
}