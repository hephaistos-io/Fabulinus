package ch.hephaistos.fabulinus.formatter;


public class XmlDataFormatter implements DataFormatter {

    @Override
    public String formatData(String variableName, String value) {
        return "<".concat(variableName).concat(">").concat(value).concat("<").concat(variableName).concat("/>");
    }
}

