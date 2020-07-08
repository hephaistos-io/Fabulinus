package ch.hephaistos.fabulinus.adapter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RequestHandler {

    private Map<String, ValueAdapter> resources;

    public RequestHandler(){
        resources = new HashMap();
    }

    public void addEntry(Pair<String, ValueAdapter> entry){
        resources.put(entry.getKey(), entry.getValue());
    }

    public void addEntries(List<Pair<String, ValueAdapter>> entries){
        entries.forEach(this::addEntry);
    }

    public String getValue(String variableName){
        if(resources.containsKey(variableName)){
            ValueAdapter valueAdapter = resources.get(variableName);
            return valueAdapter.getType().cast(valueAdapter.invokeFunction()).toString();
        }
        return "No such variable found";
    }
}
