package ch.hephaistos.fabulinus.annotations.post;

import ch.hephaistos.fabulinus.adapter.ValueAdapter;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class PostValueAdapter implements ValueAdapter {

    private Object object;

    private Method method;

    private Field field;

    private static Map<Type, Function<String, ?>> converterMap;

    public PostValueAdapter(Object object, Method method){
        this.object = object;
        this.method = method;
        constructConverterMapIfNotConstructed();
    }

    public PostValueAdapter(Object object, Field field){
        this.object = object;
        this.field = field;
        constructConverterMapIfNotConstructed();
    }

    private void constructConverterMapIfNotConstructed(){
        if (converterMap == null){
            converterMap = new HashMap<>();
            converterMap.put(int.class, Integer::valueOf);
            converterMap.put(Integer.class, Integer::valueOf);
            converterMap.put(String.class, String::valueOf);
            converterMap.put(long.class, Long::valueOf);
            converterMap.put(Long.class, Long::valueOf);
            converterMap.put(float.class, Float::valueOf);
            converterMap.put(Float.class, Float::valueOf);
        }
    }

    @Override
    public Object invokeFunction(Object ...parameters) {
        try {
            if(method == null){
                field.setAccessible(true);
                Function function = converterMap.get(field.getType());
                field.set(this.object, function.apply(parameters[0]));
                field.setAccessible(false);
            } else {
                method.invoke(parameters[0]);
            }
            return getValueOfField();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Object getValueOfField() throws IllegalAccessException {
        field.setAccessible(true);
        Object value = field.get(object);
        field.setAccessible(false);
        return value;
    }

}
