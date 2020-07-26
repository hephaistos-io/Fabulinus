package ch.hephaistos.fabulinus.adapter;

import ch.hephaistos.fabulinus.formatter.DataFormatter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RequestHandler {

    private Map<String, Map<String, ValueAdapter>> resources;
    private DataFormatter dataFormatter;

    public RequestHandler() {
        resources = new HashMap();
        setupRequestTypes();
    }

    private void setupRequestTypes(){
        for(RequestType requestType : RequestType.values()){
            resources.put(requestType.getName(), new HashMap<String, ValueAdapter>());
        }
    }

    public void addEntry(RequestType requestType, Pair<String, ValueAdapter> entry){
        resources.get(requestType.getName()).put(entry.getKey(), entry.getValue());
    }

    public void addEntries(RequestType requestType, List<Pair<String, ValueAdapter>> entries){
        entries.forEach(entry -> addEntry(requestType, entry));
    }

    public String getValue(String variableName){
        if(resources.get(RequestType.GET.getName()).containsKey(variableName)){
            ValueAdapter valueAdapter = resources.get(RequestType.GET.getName()).get(variableName);
            return dataFormatter.formatData(variableName, valueAdapter.invokeFunction(null).toString());
        }
        return "No such variable found";
    }

    public void setDataFormatter(DataFormatter dataFormatter){
        this.dataFormatter = dataFormatter;
    }
}
