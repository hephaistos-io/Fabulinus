package ch.hephaistos.fabulinus.adapter;

import ch.hephaistos.fabulinus.annotations.GetAnnotationStrategy;
import ch.hephaistos.fabulinus.formatter.DataFormatter;

public class Fabulinus {

    private RequestHandler requestHandler;

    public Fabulinus(){
        requestHandler = new RequestHandler();
    }

    public Fabulinus setupFabulinus(Object object) {
        requestHandler.addEntries(RequestType.GET, new GetAnnotationStrategy().parseFields(object));
        return this;
    }

    public Fabulinus setDataFormatter(DataFormatter dataFormatter){
        requestHandler.setDataFormatter(dataFormatter);
        return this;
    }

    public RequestHandler create(){
        return requestHandler;
    }
}
