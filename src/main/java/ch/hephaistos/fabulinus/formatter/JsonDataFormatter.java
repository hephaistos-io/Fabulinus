package ch.hephaistos.fabulinus.formatter;

public class JsonDataFormatter implements DataFormatter {

    @Override
    public String formatData(String variableName, String value) {
        return "{".concat("\"").concat(variableName).concat("\":\"").concat(value).concat("\"}");
    }
}

