package com.codecool.shop.serialization;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.SupplierDaoMem;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.Supplier;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;

public class ProductSerialization implements JsonSerializer<Product>, JsonDeserializer<Product> {

    @Override
    public Product deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        ProductCategoryDao productCategoryDao = ProductCategoryDaoMem.getInstance();
        SupplierDao supplierDao = SupplierDaoMem.getInstance();
        JsonObject obj = jsonElement.getAsJsonObject();
        return new Product(obj.get("name").getAsString(),
                new BigDecimal(obj.get("defaultPrice").getAsString()),
                obj.get("currencyString").getAsString(),
                obj.get("description").getAsString(),
                productCategoryDao.find(Integer.parseInt(obj.get("productCategoryId").getAsString())),
                supplierDao.find(Integer.parseInt(obj.get("supplierId").getAsString())));
    }

    @Override
    public JsonElement serialize(Product product, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject result = new JsonObject();
        result.addProperty("name", product.getName());
        result.addProperty("defaultPrice", product.getDefaultPrice());
        result.addProperty("currencyString", product.getDefaultCurrency().getSymbol());
        result.addProperty("description", product.getDescription());
        result.addProperty("productCategoryId", product.getProductCategory().getId());
        result.addProperty("supplierId", product.getSupplier().getId());
        return result;
    }
}
