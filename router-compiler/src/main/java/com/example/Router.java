package com.example;


import com.example.router.IntentParam;
import com.example.router.RouteUri;
import com.example.router.UriParam;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class Router {

    @SuppressWarnings("unchecked")
    public static <T> T create(final Class<T> service){
        return (T) Proxy.newProxyInstance(service.getClassLoader(), new Class[]{service}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                if (method.isAnnotationPresent(RouteUri.class)){

                    StringBuilder builder = new StringBuilder();

                    RouteUri uriAnnotation = method.getAnnotation(RouteUri.class);

                    String uriValue = uriAnnotation.value();

                    builder.append(uriValue);

                    int position=0;


                    Annotation[][] annotations = method.getParameterAnnotations();

                    for (int i = 0;i < annotations.length; i++){
                        for (Annotation paramAnnotation:annotations[i]){
                            if (paramAnnotation instanceof IntentParam){




                            }else if (paramAnnotation instanceof UriParam){
                                builder.append(position == 0 ? "?":"&").append(((UriParam) paramAnnotation).value())
                                        .append("=").append(args[i]);
                                position++;

                            }
                        }
                    }



                }


                return null;
            }
        });
    }
}
