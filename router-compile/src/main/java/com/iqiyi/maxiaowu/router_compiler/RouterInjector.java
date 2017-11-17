package com.iqiyi.maxiaowu.router_compiler;

import android.content.Context;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by maxiaowu on 2017/11/14.
 */

public class RouterInjector {

    private static final Map<Class<?>, Constructor<? extends ParametersInjector>> INJECTING_MAP = new LinkedHashMap<>();

    public static void inject(Context target) {
        createInjecting(target);
    }

    private static ParametersInjector createInjecting(Context target) {
        Constructor<? extends ParametersInjector> constructor = findConstuctorByClass(target.getClass());

        if (constructor == null) {
            return ParametersInjector.EMPTY;
        }

        try {
            return constructor.newInstance(target);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (cause instanceof RuntimeException) {
                throw (RuntimeException) cause;
            }

            if (cause instanceof Error) {
                throw (Error) cause;
            }
            throw new RuntimeException("Unable to create injecting instance.",cause);
        }
        return ParametersInjector.EMPTY;
    }


    private static Constructor<? extends ParametersInjector> findConstuctorByClass(Class<? extends Context> clazz) {

        Constructor<? extends  ParametersInjector> constructor = INJECTING_MAP.get(clazz);
        if (constructor != null){
            return constructor;
        }

        String className = clazz.getName();

        try{
            Class<?> injectingClass = Class.forName(className+"_RouterInjecting");

            constructor = (Constructor<? extends ParametersInjector>) injectingClass.getConstructor(clazz);


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        INJECTING_MAP.put(clazz,constructor);

        return constructor;
    }
}
