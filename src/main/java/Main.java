import ch.hephaistos.fabulinus.adapter.Fabulinus;
import ch.hephaistos.fabulinus.adapter.RequestHandler;
import ch.hephaistos.fabulinus.formatter.JsonDataFormatter;
import ch.hephaistos.fabulinus.formatter.XmlDataFormatter;

public class Main {

    public static void main(String args[]) {
        ExampleObject exampleObject = new ExampleObject("newNameWhoThis?", 27);
        RequestHandler requestHandler = new Fabulinus()
                .setDataFormatter(new JsonDataFormatter())
                .setupFabulinus(exampleObject)
                .create();
        System.out.println(requestHandler.getValue("name"));
        System.out.println(requestHandler.getValue("age"));

        requestHandler = new Fabulinus()
                .setDataFormatter(new XmlDataFormatter())
                .setupFabulinus(exampleObject)
                .create();
        System.out.println(requestHandler.getValue("name"));
        System.out.println(requestHandler.getValue("age"));
    }

}
