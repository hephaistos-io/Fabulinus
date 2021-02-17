import ch.hephaistos.fabulinus.annotations.get.GET;
import ch.hephaistos.fabulinus.annotations.post.NotNullPostValidatorStrategy;
import ch.hephaistos.fabulinus.annotations.post.POST;

public class ExampleObject {

    @GET
    @POST(validator = NotNullPostValidatorStrategy.class)
    private String name;

    @GET
    @POST
    private int age;

    @GET
    @POST
    private float floatingNumber;

    @GET
    @POST
    private boolean bool;

    @GET
    @POST
    private long longBoy;

    @GET
    @POST(function="doubleValue")
    private double number;

    @GET(function="randomString")
    @POST(function="setRandomString")
    private String unreachableString;

    private final String RANDOM_STRING = "asidjkslamdlkasdop";

    public ExampleObject(String name, int age, double number, float floatingNumber, boolean bool, long longBoy){
        this.name = name;
        this.age = age;
        this.number = number;
        this.floatingNumber = floatingNumber;
        this.longBoy = longBoy;
        this.bool = bool;
        unreachableString = "haha can't get me!";
    }

    public void setName(String name){
        this.name = "lmao you " + name + " wish";
    }

    public void setAge(int age){
        this.age += age;
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

    public void doubleValue(double value){
        this.number = 2*value;
    }

    public String randomString(){
        return "asdhaisdasdpo";
    }

}
