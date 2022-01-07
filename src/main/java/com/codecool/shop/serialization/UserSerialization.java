package com.codecool.shop.serialization;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.UserDao;
import com.codecool.shop.dao.db.UserDaoJdbc;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.SupplierDaoMem;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.User;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.math.BigDecimal;

public class UserSerialization implements JsonSerializer<User>, JsonDeserializer<User> {

    @Override
    public User deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject obj = jsonElement.getAsJsonObject();
        return new User(obj.get("firstName").getAsString(),
                obj.get("lastName").getAsString(),
                obj.get("email").getAsString(),
                obj.get("phoneNumber").getAsString(),
                obj.get("address").getAsString(),
                obj.get("city").getAsString(),
                obj.get("state").getAsString(),
                obj.get("country").getAsString(),
                obj.get("password").getAsString()
                );
    }

    @Override
    public JsonElement serialize(User user, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject result = new JsonObject();
        result.addProperty("firstName", user.getFirstName());
        result.addProperty("lastName", user.getLastName());
        result.addProperty("email", user.getEmail());
        result.addProperty("customerId", user.getCustomerId());
        result.addProperty("phoneNumber", user.getPhoneNumber());
        result.addProperty("address", user.getAddress());
        result.addProperty("city", user.getCity());
        result.addProperty("state", user.getState());
        result.addProperty("country", user.getCountry());
        return result;
    }
}
