package ch.hephaistos.fabulinus.annotations;

import ch.hephaistos.fabulinus.adapter.Pair;
import ch.hephaistos.fabulinus.adapter.ValueAdapter;
import ch.hephaistos.fabulinus.annotations.get.GET;
import ch.hephaistos.fabulinus.annotations.get.GetAnnotationStrategy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Tests the whole logic around the @GET annotation
 */
public class GetAnnotationTests {

    /**
     * Object used solely for testing.
     */
    public class ExampleObject {

        @GET
        private String name;

        @GET
        private int age;

        @GET
        private double number;

        @GET
        private float floatNumber;

        @GET
        private long longNumber;

        @GET
        private boolean booleanValue;

        @GET(function = "randomString")
        private String unreachableString;

        private final String finalString = "not parsed for the library!";

        public ExampleObject(String name, int age, double number, float floatNumber, long longNumber, boolean booleanValue) {
            this.name = name;
            this.age = age;
            this.number = number;
            this.floatNumber = floatNumber;
            this.longNumber = longNumber;
            this.booleanValue = booleanValue;
            unreachableString = "haha can't get me!";
        }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }

        public String randomString() {
            return "asdhaisdasdpo";
        }

        public float getFloatNumber(){
            return floatNumber + floatNumber;
        }

    }

    ExampleObject exampleObject;
    String name = "newName";
    int age = 25;
    double number = 27;
    float floatNumber = 15f;
    long longNumber = 12l;
    boolean booleanValue = false;

    @BeforeEach
    void beforeEach() {
        exampleObject = new ExampleObject(name, age, number, floatNumber, longNumber, booleanValue);
    }

    @Test
    @DisplayName("function variable inside of GET (for unparameterized usages) is empty")
    public void getFunctionValueIsEmptyOnUnparameterizedAnnotation() {
        Arrays.asList(exampleObject.getClass().getFields()).forEach(field -> {
            if (!field.getName().equals("unreachableString")) {
                Arrays.asList(field.getAnnotations()).stream()
                        .filter("GET"::equals)
                        .forEach(entry -> {
                            Assertions.assertTrue(((GET) entry).function().isEmpty());
                        });
            }
        });
    }

    @Test
    @DisplayName("the parsing function of GetAnnotationStrategy returns a list with the correct amount of elements")
    public void parsingObjectReturnsAListWithTheCorrectAmountOfElements() {
        GetAnnotationStrategy getAnnotationStrategy = new GetAnnotationStrategy();
        List<Pair<String, ValueAdapter>> list = getAnnotationStrategy.parseFields(exampleObject);
        Assertions.assertTrue(list.size() == 7);
        list.forEach(entry -> {
            Assertions.assertNotNull(entry.getKey());
            Assertions.assertNotNull(entry.getValue());
        });
    }

    @Test
    @DisplayName("Links the correct function to the corresponding variable")
    public void linkExistingGetFunctionToVariable() {
        GetAnnotationStrategy getAnnotationStrategy = new GetAnnotationStrategy();
        List<Pair<String, ValueAdapter>> list = getAnnotationStrategy.parseFields(exampleObject);
        HashMap<String, ValueAdapter> map = new HashMap<>();
        list.forEach(entry -> map.put(entry.getKey(), entry.getValue()));
        Assertions.assertEquals(map.get("name").invokeFunction((Object) null), name);
    }

    @Test
    @DisplayName("creates a correct anonymous function for the variable")
    public void linksNewAnonymousFunctionToTheVariable() {
        GetAnnotationStrategy getAnnotationStrategy = new GetAnnotationStrategy();
        List<Pair<String, ValueAdapter>> list = getAnnotationStrategy.parseFields(exampleObject);
        HashMap<String, ValueAdapter> map = new HashMap<>();
        list.forEach(entry -> map.put(entry.getKey(), entry.getValue()));
        Assertions.assertEquals(map.get("number").invokeFunction((Object) null), number);
    }

    @Test
    @DisplayName("links given function to variable")
    public void linksFunctionToTheVariable() {
        GetAnnotationStrategy getAnnotationStrategy = new GetAnnotationStrategy();
        List<Pair<String, ValueAdapter>> list = getAnnotationStrategy.parseFields(exampleObject);
        HashMap<String, ValueAdapter> map = new HashMap<>();
        list.forEach(entry -> map.put(entry.getKey(), entry.getValue()));
        Assertions.assertEquals(map.get("unreachableString").invokeFunction((Object) null), exampleObject.randomString());
    }

    @Test
    @DisplayName("The generated functions return the correct values")
    public void generatedFunctionsReturnTheCorrectValues() {
        GetAnnotationStrategy getAnnotationStrategy = new GetAnnotationStrategy();
        List<Pair<String, ValueAdapter>> list = getAnnotationStrategy.parseFields(exampleObject);
        HashMap<String, ValueAdapter> map = new HashMap<>();
        list.forEach(entry -> map.put(entry.getKey(), entry.getValue()));
        Assertions.assertEquals(map.get("name").invokeFunction((Object) null), name);
        Assertions.assertEquals(map.get("age").invokeFunction((Object) null), age);
        Assertions.assertEquals(map.get("number").invokeFunction((Object) null), number);
        Assertions.assertEquals(map.get("floatNumber").invokeFunction((Object) null), floatNumber + floatNumber);
        Assertions.assertEquals(map.get("unreachableString").invokeFunction((Object) null), exampleObject.randomString());
        Assertions.assertEquals(map.get("longNumber").invokeFunction((Object) null), longNumber);
        Assertions.assertEquals(map.get("booleanValue").invokeFunction((Object) null), booleanValue);
    }


}
