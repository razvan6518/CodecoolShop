package com.codecool.shop.serialization;
import com.codecool.shop.model.Category;
import com.google.gson.*;
import java.lang.reflect.Type;

public class CategorySerialization  implements JsonSerializer<Category>, JsonDeserializer<Category> {

    @Override
    public Category deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject obj = jsonElement.getAsJsonObject();
        return new Category(obj.get("name").getAsString(),
                obj.get("department").getAsString(),
                obj.get("description").getAsString());
    }

    @Override
    public JsonElement serialize(Category category, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject result = new JsonObject();
        result.addProperty("name", category.getName());
        result.addProperty("id", category.getId());
        result.addProperty("description", category.getDescription());
        result.addProperty("department", category.getDepartment());
        return result;
    }
}
