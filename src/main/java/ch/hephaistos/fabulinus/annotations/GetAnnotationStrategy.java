package ch.hephaistos.fabulinus.annotations;

import ch.hephaistos.fabulinus.adapter.AnnotationStrategy;
import ch.hephaistos.fabulinus.adapter.Pair;
import ch.hephaistos.fabulinus.adapter.ValueAdapter;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GetAnnotationStrategy implements AnnotationStrategy {

    @Override
    public List<Pair<String, ValueAdapter>> parseFields(Object object) {
        List<Pair<String, ValueAdapter>> pairs = new ArrayList();
        Class clazz = object.getClass();
        Arrays.asList(clazz.getDeclaredFields()).stream()
                .filter(field -> field.isAnnotationPresent(GET.class))
                .forEach(field -> {
                    String functionName = generateFunctionName(field.getName());
                    if (field.getAnnotation(GET.class).function().isEmpty()) {
                        if (Arrays.asList(clazz.getDeclaredMethods()).stream().anyMatch(functionName::equals)) {
                            pairs.add(linkFunction(functionName, field, object, clazz));
                        } else {
                            pairs.add(linkAnonymousFunction(field, object));
                        }
                    } else {
                        pairs.add(linkFunction(field.getAnnotation(GET.class).function(), field, object, clazz));
                    }
                });
        return pairs;
    }

    private Pair<String, ValueAdapter> linkFunction(String functionName, Field field, Object object, Class clazz) {
        Method method = getMethodForFunctionName(functionName, clazz);
        if (method.getReturnType() != field.getType()) {
            throw new RuntimeException("Function doesn't return the correct type!");
        }
        return new Pair(field.getName(), new GetValueAdapter(object, method));
    }

    private Pair<String, ValueAdapter> linkAnonymousFunction(Field field, Object object) {
        return new Pair(field.getName(), new GetValueAdapter(object, field));
    }

    private Method getMethodForFunctionName(String functionName, Class clazz) {
        return Arrays.asList(clazz.getDeclaredMethods()).stream().filter(m -> m.getName().equals(functionName)).findFirst().get();
    }

    private String generateFunctionName(String fieldName) {
        return "get".concat(fieldName.substring(0, 1).toUpperCase()).concat(fieldName.substring(1));
    }

}
