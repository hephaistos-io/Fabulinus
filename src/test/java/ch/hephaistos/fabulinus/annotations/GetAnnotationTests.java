package ch.hephaistos.fabulinus.annotations;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.annotation.Annotation;
import java.util.Arrays;

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

        @GET(function = "randomString")
        private String unreachableString;

        public ExampleObject(String name, int age, double number) {
            this.name = name;
            this.age = age;
            this.number = number;
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


}
