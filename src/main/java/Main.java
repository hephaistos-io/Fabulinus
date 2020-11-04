import ch.hephaistos.fabulinus.adapter.Fabulinus;
import ch.hephaistos.fabulinus.adapter.RequestHandler;
import ch.hephaistos.fabulinus.adapter.RequestType;
import ch.hephaistos.fabulinus.formatter.JsonDataFormatter;

public class Main {

    public static void main(String args[]) {
        ExampleObject exampleObject = new ExampleObject("newNameWhoThis?", 27, 27.32, 77f, true, 1337l);
        RequestHandler requestHandler = new Fabulinus()
                .setDataFormatter(new JsonDataFormatter())
                .setupFabulinus(exampleObject)
                .create();


        System.out.println(requestHandler.applyRetrievalRequest(RequestType.GET, "name"));
        System.out.println("Was able to POST value?:" + requestHandler.applyStoringRequest(RequestType.POST, "name", "theFuck"));
        System.out.println(requestHandler.applyRetrievalRequest(RequestType.GET, "name"));

        System.out.println(requestHandler.applyRetrievalRequest(RequestType.GET, "bool"));
        System.out.println("Was able to POST value?:" + requestHandler.applyStoringRequest(RequestType.POST, "bool", "false"));
        System.out.println(requestHandler.applyRetrievalRequest(RequestType.GET, "bool"));

        System.out.println(requestHandler.applyRetrievalRequest(RequestType.GET, "floatingNumber"));
        System.out.println("Was able to POST value?:" + requestHandler.applyStoringRequest(RequestType.POST, "floatingNumber", "27f"));
        System.out.println(requestHandler.applyRetrievalRequest(RequestType.GET, "floatingNumber"));


        System.out.println(requestHandler.applyRetrievalRequest(RequestType.GET, "number"));
        System.out.println("Was able to POST value?:" + requestHandler.applyStoringRequest(RequestType.POST, "number", "12.33"));
        System.out.println(requestHandler.applyRetrievalRequest(RequestType.GET, "number"));


        System.out.println(requestHandler.applyRetrievalRequest(RequestType.GET, "age"));
        System.out.println("Was able to POST value?:" + requestHandler.applyStoringRequest(RequestType.POST, "age", "12"));
        System.out.println(requestHandler.applyRetrievalRequest(RequestType.GET, "age"));

    }

}
