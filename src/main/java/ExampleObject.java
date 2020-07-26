import ch.hephaistos.fabulinus.annotations.GET;
import ch.hephaistos.fabulinus.annotations.NotNullPostValidatorStrategy;
import ch.hephaistos.fabulinus.annotations.POST;

public class ExampleObject {

    @GET
    @POST(validator = NotNullPostValidatorStrategy.class)
    private String name;

    @GET
    @POST
    private int age;

    @GET
    @POST
    private double number;

    @GET(function="randomString")
    @POST(function="randomString")
    private String unreachableString;

    private final String RANDOM_STRING = "asidjkslamdlkasdop";

    public ExampleObject(String name, int age, double number){
        this.name = name;
        this.age = age;
        this.number = number;
        unreachableString = "haha can't get me!";
    }

    public void setName(String name){
        this.name = name;
    }

    public void setAge(int age){
        this.age = age;
    }

    public void setRandomString(){
        this.name = RANDOM_STRING;
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
