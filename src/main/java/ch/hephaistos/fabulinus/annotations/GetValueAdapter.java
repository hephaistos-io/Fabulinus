package ch.hephaistos.fabulinus.annotations;

import ch.hephaistos.fabulinus.adapter.ValueAdapter;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class GetValueAdapter implements ValueAdapter {

    private Object object;
    
    private Method method;

    private Field field;

    public GetValueAdapter(Object object, Method method){
        this.object = object;
        this.method = method;
    }

    public GetValueAdapter(Object object, Field field){
        this.object = object;
        this.field = field;
    }

    @Override
    public Object invokeFunction(Object... parameters) {
        try {
            if (method == null) {
                field.setAccessible(true);
                Object returnObject = field.get(object);
                field.setAccessible(false);
                return returnObject;
            } else {
                return method.invoke(object);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}
