package ch.hephaistos.fabulinus.annotations;

import ch.hephaistos.fabulinus.adapter.ValueAdapter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class GetValueAdapter implements ValueAdapter {

    private Object object;

    private Method method;


    public GetValueAdapter(Object object, Method method){
        this.object = object;
        this.method = method;
    }

    @Override
    public Object invokeFunction() {
        try {
            return method.invoke(object);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

}
