package ch.hephaistos.fabulinus.formatter;

import com.google.gson.JsonObject;

public class JsonDataFormatter implements DataFormatter {

    @Override
    public String formatData(String variableName, String value) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(variableName, value);
        return jsonObject.toString();
    }
}

