import ch.hephaistos.fabulinus.annotations.GET;

public class ExampleObject {

    @GET
    private String name;

    @GET
    private int age;

    public ExampleObject(String name, int age){
        this.name = name;
        this.age = age;
    }

    public String getName(){
        return name;
    }

    public int getAge(){
        return age;
    }

}
