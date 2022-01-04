package com.codecool.shop.controller.product;

import com.codecool.shop.dao.implementation.SupplierDaoMem;
import com.codecool.shop.model.Supplier;
import com.codecool.shop.serialization.SupplierSerialization;
import com.codecool.shop.service.SupplierService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "GetSuppliers", urlPatterns = {"/suppliers"})
public class GetSuppliers extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        SupplierService supplierService = new SupplierService(SupplierDaoMem.getInstance());
        List<Supplier> suppliers = supplierService.getAllSuppliers();

        Gson gson = new GsonBuilder().registerTypeAdapter(Supplier.class, new SupplierSerialization()).serializeNulls().create();
        PrintWriter out = response.getWriter();

        out.println(gson.toJson(suppliers));
    }
}