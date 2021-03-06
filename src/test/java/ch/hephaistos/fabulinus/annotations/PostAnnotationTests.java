package ch.hephaistos.fabulinus.annotations;

import ch.hephaistos.fabulinus.adapter.Pair;
import ch.hephaistos.fabulinus.adapter.ValueAdapter;
import ch.hephaistos.fabulinus.annotations.post.DefaultPostValidatorStrategy;
import ch.hephaistos.fabulinus.annotations.post.POST;
import ch.hephaistos.fabulinus.annotations.post.PostAnnotationStrategy;
import ch.hephaistos.fabulinus.annotations.post.PostValidatorStrategy;
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
public class PostAnnotationTests {

    public class TestingValidatior implements PostValidatorStrategy {

        @Override
        public boolean validate(String value) {
            try {
                double check = Double.valueOf(value);
                return check < 5 && check > 0;
            } catch (NumberFormatException nfe) {
                return false;
            }
        }
    }


    /**
     * Object used solely for testing.
     */
    public class ExampleObject {

        @POST
        private String name;

        @POST
        private int age;

        @POST
        private double number;

        @POST
        private float floatNumber;

        @POST
        private long longNumber;

        @POST
        private boolean booleanValue;

        @POST(function = "randomString")
        private String randomText;

        private final String finalString = "not parsed for the library!";

        public ExampleObject(String name, int age, double number, float floatNumber, long longNumber, boolean booleanValue) {
            this.name = name;
            this.age = age;
            this.number = number;
            this.floatNumber = floatNumber;
            this.longNumber = longNumber;
            this.booleanValue = booleanValue;
            randomText = "haha can't get me!";
        }

        public void setName(String name) {
            this.name = finalString;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public void randomString(String prefix) {
            randomText = prefix + randomText;
        }

        public void setFloatNumber(float floatNumber) {
            this.floatNumber = 2f * floatNumber;
        }

    }

    PostAnnotationTests.ExampleObject exampleObject;
    String name = "newName";
    int age = 25;
    double number = 27;
    float floatNumber = 15f;
    long longNumber = 12l;
    boolean booleanValue = false;

    @BeforeEach
    void beforeEach() {
        exampleObject = new PostAnnotationTests.ExampleObject(name, age, number, floatNumber, longNumber, booleanValue);
    }

    @Test
    @DisplayName("function variable inside of POST (for unparameterized usages) is empty")
    public void postFunctionValueIsEmptyOnUnparameterizedAnnotation() {
        Arrays.asList(exampleObject.getClass().getFields()).forEach(field -> {
            if (!field.getName().equals("unreachableString")) {
                Arrays.asList(field.getAnnotations()).stream()
                        .filter("POST"::equals)
                        .forEach(entry -> {
                            Assertions.assertTrue(((POST) entry).function().isEmpty());
                        });
            }
        });
    }

    @Test
    @DisplayName("validator variable inside of POST (for unparameterized usages) returns default validator")
    public void postValidatorValueIsDefaultValidatorOnUnparameterizedAnnotation() {
        Arrays.asList(exampleObject.getClass().getFields()).forEach(field -> {
            if (!field.getName().equals("unreachableString")) {
                Arrays.asList(field.getAnnotations()).stream()
                        .filter("POST"::equals)
                        .forEach(entry -> {
                            Assertions.assertTrue(((POST) entry).validator().equals(DefaultPostValidatorStrategy.class));
                        });
            }
        });
    }

    @Test
    @DisplayName("the parsing function of PostAnnotationStrategy returns a list with the correct amount of elements")
    public void parsingObjectReturnsAListWithTheCorrectAmountOfElements() {
        PostAnnotationStrategy postAnnotationStrategy = new PostAnnotationStrategy();
        List<Pair<String, ValueAdapter>> list = postAnnotationStrategy.parseFields(exampleObject);
        Assertions.assertTrue(list.size() == 7);
        list.forEach(entry -> {
            Assertions.assertNotNull(entry.getKey());
            Assertions.assertNotNull(entry.getValue());
        });
    }

    @Test
    @DisplayName("Links the correct function to the corresponding variable")
    public void linkExistingPostFunctionToVariable() {
        int numberToCheckWith = 42;
        float floatToCheckWith = 12.37f;
        float multiplicationValueOfFunction = 2f;
        PostAnnotationStrategy postAnnotationStrategy = new PostAnnotationStrategy();
        List<Pair<String, ValueAdapter>> list = postAnnotationStrategy.parseFields(exampleObject);
        HashMap<String, ValueAdapter> map = new HashMap<>();
        list.forEach(entry -> map.put(entry.getKey(), entry.getValue()));
        map.get("name").invokeFunction("Francis");
        Assertions.assertEquals(exampleObject.name, exampleObject.finalString);
        map.get("age").invokeFunction(String.valueOf(numberToCheckWith));
        Assertions.assertEquals(exampleObject.age, numberToCheckWith);
        map.get("floatNumber").invokeFunction(String.valueOf(floatToCheckWith));
        Assertions.assertEquals(exampleObject.floatNumber, multiplicationValueOfFunction * floatToCheckWith);
    }

    @Test
    @DisplayName("creates a correct anonymous function for the variable")
    public void linksNewAnonymousFunctionToTheVariable() {
        int valueToCheckWith = 12;
        PostAnnotationStrategy postAnnotationStrategy = new PostAnnotationStrategy();
        List<Pair<String, ValueAdapter>> list = postAnnotationStrategy.parseFields(exampleObject);
        HashMap<String, ValueAdapter> map = new HashMap<>();
        list.forEach(entry -> map.put(entry.getKey(), entry.getValue()));
        map.get("number").invokeFunction(String.valueOf(valueToCheckWith));
        Assertions.assertEquals(exampleObject.number, valueToCheckWith);
    }

    @Test
    @DisplayName("links given function to variable")
    public void linksFunctionToTheVariable() {
        String currentValue = exampleObject.randomText;
        String prefix = "pR3sadFixasd213123";
        PostAnnotationStrategy postAnnotationStrategy = new PostAnnotationStrategy();
        List<Pair<String, ValueAdapter>> list = postAnnotationStrategy.parseFields(exampleObject);
        HashMap<String, ValueAdapter> map = new HashMap<>();
        list.forEach(entry -> map.put(entry.getKey(), entry.getValue()));
        map.get("randomText").invokeFunction(prefix);
        Assertions.assertEquals(exampleObject.randomText, prefix + currentValue);
    }

}