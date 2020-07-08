import ch.hephaistos.fabulinus.adapter.Fabulinus;
import ch.hephaistos.fabulinus.adapter.RequestHandler;

public class Main {

    public static void main(String args[]){
        ExampleObject exampleObject = new ExampleObject("newNameWhoThis?", 27);
        RequestHandler requestHandler = Fabulinus.setupFabulinus(exampleObject);
        System.out.println(requestHandler.getValue("name"));
        System.out.println(requestHandler.getValue("age"));
    }

}
