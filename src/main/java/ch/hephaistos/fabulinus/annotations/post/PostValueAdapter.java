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


    public PostValueAdapter(Object object, Method method){
        this.object = object;
        this.method = method;
    }

    public PostValueAdapter(Object object, Field field){
        this.object = object;
        this.field = field;
    }

    protected static Function<String, ?> getConverterForPrimitiveTypes(Class clazz){
        switch(clazz.toString()){
            case "int":
            case "Integer":
                return Integer::valueOf;
            case "double":
            case "Double":
                return Double::valueOf;
            case "String":
                return String::valueOf;
            case "long":
            case "Long":
                return Long::valueOf;
            case "float":
            case "Float":
                return Float::valueOf;
            case "boolean":
            case "Boolean":
                return Boolean::valueOf;
            default:
                throw new RuntimeException("Class wasnt recognized: " + clazz.toString());
        }
    }

    @Override
    public Object invokeFunction(Object ...parameters) {
        try {
            if(method == null){
                field.setAccessible(true);
                Function function = getConverterForPrimitiveTypes(field.getType());
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
