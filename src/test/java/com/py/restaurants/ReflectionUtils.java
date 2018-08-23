package com.py.restaurants;

import java.lang.reflect.Field;

public class ReflectionUtils {
    private ReflectionUtils() {
    }

    public static void setPrivateField(Object obj, String privFieldName, Object value) {
        try {
            Field field = obj.getClass().getDeclaredField(privFieldName);
            field.setAccessible(true);
            field.set(obj, value);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
