package ch.hephaistos.fabulinus.annotations;

import ch.hephaistos.fabulinus.adapter.Pair;
import ch.hephaistos.fabulinus.adapter.ValueAdapter;
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

        @POST(validator = PostAnnotationTests.TestingValidatior.class)
        private double number;

        @POST(function = "randomString")
        private String unreachableString;

        private final String RANDOM_STRING = "asjdkhio21398";

        public ExampleObject(String name, int age, double number) {
            this.name = name;
            this.age = age;
            this.number = number;
            unreachableString = "haha can't get me!";
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public void randomString() {
            this.unreachableString = RANDOM_STRING;
        }
    }


    ExampleObject exampleObject;
    String name = "newName";
    int age = 25;
    double number = 27;

    @BeforeEach
    void beforeEach() {
        exampleObject = new ExampleObject(name, age, number);
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
        Assertions.assertTrue(list.size() == 4);
        list.forEach(entry -> {
            Assertions.assertNotNull(entry.getKey());
            Assertions.assertNotNull(entry.getValue());
        });
    }

    @Test
    @DisplayName("Links the correct function to the corresponding variable")
    public void linkExistingGetFunctionToVariable() {
        Assertions.assertTrue(false);
    }

    @Test
    @DisplayName("creates a correct anonymous function for the variable")
    public void linksNewAnonymousFunctionToTheVariable() {
        Assertions.assertTrue(false);
    }

    @Test
    @DisplayName("links given function to variable")
    public void linksFunctionToTheVariable() {
        Assertions.assertTrue(false);
    }

    @Test
    @DisplayName("The generated functions return the correct values")
    public void generatedFunctionsReturnTheCorrectValues() {
        Assertions.assertTrue(false);
    }
}