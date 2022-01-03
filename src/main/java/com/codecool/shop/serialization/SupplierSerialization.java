package com.codecool.shop.serialization;

import com.codecool.shop.model.Supplier;
import com.google.gson.*;

import java.lang.reflect.Type;

public class SupplierSerialization implements JsonSerializer<Supplier>, JsonDeserializer<Supplier> {

    @Override
    public Supplier deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject obj = jsonElement.getAsJsonObject();
        return new Supplier(obj.get("name").getAsString(),
                obj.get("description").getAsString());
    }

    @Override
    public JsonElement serialize(Supplier supplier, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject result = new JsonObject();
        result.addProperty("name", supplier.getName());
        result.addProperty("id", supplier.getId());
        result.addProperty("description", supplier.getDescription());
        return result;
    }
}
