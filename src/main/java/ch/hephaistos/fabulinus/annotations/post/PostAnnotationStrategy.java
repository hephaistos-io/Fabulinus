package ch.hephaistos.fabulinus.annotations.post;

import ch.hephaistos.fabulinus.annotations.AnnotationStrategy;
import ch.hephaistos.fabulinus.adapter.Pair;
import ch.hephaistos.fabulinus.adapter.ValueAdapter;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PostAnnotationStrategy implements AnnotationStrategy {

    @Override
    public List<Pair<String, ValueAdapter>> parseFields(Object object) {
        List<Pair<String, ValueAdapter>> pairs = new ArrayList();
        Class clazz = object.getClass();
        Arrays.asList(clazz.getDeclaredFields()).stream()
                .filter(field -> field.isAnnotationPresent(POST.class))
                .forEach(field -> {
                    String functionName = generateFunctionName(field.getName());
                    if (field.getAnnotation(POST.class).function().isEmpty()) {
                        if (Arrays.asList(clazz.getDeclaredMethods()).stream().anyMatch(name -> name.toString().contains(functionName))) {
                            pairs.add(linkFunction(functionName, field, object, clazz));
                        } else {
                            pairs.add(linkAnonymousFunction(field, object));
                        }
                    } else {
                        pairs.add(linkFunction(field.getAnnotation(POST.class).function(), field, object, clazz));
                    }
                });
        return pairs;
    }

    private Pair<String, ValueAdapter> linkFunction(String functionName, Field field, Object object, Class clazz) {
        Method method = getMethodForFunctionName(functionName, clazz);
        System.out.println("linking function with name: " + functionName);
        return new Pair(field.getName(), new PostValueAdapter(object, method, field));
    }

    private Pair<String, ValueAdapter> linkAnonymousFunction(Field field, Object object) {
        return new Pair(field.getName(), new PostValueAdapter(object, field));
    }

    private Method getMethodForFunctionName(String functionName, Class clazz) {
        return Arrays.asList(clazz.getDeclaredMethods()).stream().filter(m -> m.getName().equals(functionName)).findFirst().get();
    }

    private String generateFunctionName(String fieldName) {
        return "set".concat(fieldName.substring(0, 1).toUpperCase()).concat(fieldName.substring(1));
    }

}
