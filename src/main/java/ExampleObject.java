import ch.hephaistos.fabulinus.annotations.GET;

public class ExampleObject {

    @GET
    private String name;

    @GET
    private int age;

    @GET
    private double number;

    @GET(function="randomString")
    private String unreachableString;

    public ExampleObject(String name, int age, double number){
        this.name = name;
        this.age = age;
        this.number = number;
        unreachableString = "haha can't get me!";
    }

    public String getName(){
        return name;
    }

    public int getAge(){
        return age;
    }

    public String randomString(){
        return "asdhaisdasdpo";
    }

}
