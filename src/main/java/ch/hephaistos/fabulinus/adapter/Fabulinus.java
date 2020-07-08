package ch.hephaistos.fabulinus.adapter;

import ch.hephaistos.fabulinus.annotations.GetAnnotationStrategy;

public class Fabulinus {

    public static RequestHandler setupFabulinus(Object object) {
        RequestHandler requestHandler = new RequestHandler();
        requestHandler.addEntries(new GetAnnotationStrategy().parseFields(object));
        return requestHandler;
    }
}
