package com.asiadream.jcode.tool.share.util.reflection;

import java.lang.reflect.Method;
import java.util.Optional;

public class ObjectCallUtil {
    //
    public static String invokeStringGetMethod(Object targetObject, String methodName) throws Exception {
        //
        Object object = invokeGetMethod(targetObject, methodName);
        return Optional.ofNullable(object).map(Object::toString).orElse(null);
    }

    public static Object invokeGetMethod(Object targetObject, String methodName) throws Exception {
        //
        Class clazz = targetObject.getClass();
        Method method = clazz.getMethod(methodName);
        return method.invoke(targetObject);
    }
}
