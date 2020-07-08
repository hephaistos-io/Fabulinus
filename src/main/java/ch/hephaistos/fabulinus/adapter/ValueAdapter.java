package ch.hephaistos.fabulinus.adapter;

import java.lang.reflect.Type;

public interface ValueAdapter {

    Object invokeFunction();

    Class getType();
}
