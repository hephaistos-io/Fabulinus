package ch.hephaistos.fabulinus.annotations;

import ch.hephaistos.fabulinus.adapter.ValueAdapter;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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

    @Override
    public Object invokeFunction(Object ...parameters) {
        try {
            if(method == null){
                field.setAccessible(true);
                field.set(this.object, parameters[0]);
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
