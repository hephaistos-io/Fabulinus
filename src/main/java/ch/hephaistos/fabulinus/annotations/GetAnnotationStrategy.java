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
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(GET.class)) {
                if (field.getAnnotation(GET.class).function().isEmpty()) {
                    pairs.add(linkAnonymousFunction(field, object, clazz));
                }
            }
        }
        return pairs;
    }

    private Pair<String, ValueAdapter> linkAnonymousFunction(Field field, Object object, Class clazz) {
        String functionName = "get".concat(field.getName().substring(0, 1).toUpperCase()).concat(field.getName().substring(1));
        Method method = Arrays.asList(clazz.getDeclaredMethods()).stream().filter(m -> m.getName().equals(functionName)).findFirst().get();
        if (method.getReturnType() != field.getType()) {
            throw new RuntimeException("Function doesn't return the correct type!");
        }
        return new Pair(field.getName(), new GetValueAdapter(object, method));
    }

}
