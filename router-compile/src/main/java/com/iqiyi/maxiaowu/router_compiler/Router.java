package com.iqiyi.maxiaowu.router_compiler;


import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;

import com.example.annotation.IntentParam;
import com.example.annotation.RouteUri;
import com.example.annotation.UriParam;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Router {

    private  Context context;

    public Router(Context context) {
        this.context = context;
    }
    @SuppressWarnings("unchecked")
    public  <T> T create(final Class<T> service){
        return (T) Proxy.newProxyInstance(service.getClassLoader(), new Class[]{service}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                if (method.isAnnotationPresent(RouteUri.class)){

                    StringBuilder builder = new StringBuilder();

                    RouteUri uriAnnotation = method.getAnnotation(RouteUri.class);

                    String uriValue = uriAnnotation.value();

                    builder.append(uriValue);

                    int position=0;

                    HashMap<String,Object> mapIntentParam = new HashMap<>();

                    Annotation[][] annotations = method.getParameterAnnotations();

                    for (int i = 0;i < annotations.length; i++){
                        for (Annotation paramAnnotation:annotations[i]){
                            if (paramAnnotation instanceof IntentParam){

                                mapIntentParam.put(((IntentParam) paramAnnotation).value(),args[i]);

                            }else if (paramAnnotation instanceof UriParam){
                                if (!TextUtils.isEmpty(((UriParam) paramAnnotation).value())){
                                    builder.append(position == 0 ? "?":"&").append(((UriParam) paramAnnotation).value())
                                            .append("=").append(args[i]);
                                    position++;
                                }

                            }
                        }
                    }

                    performJumpAction(builder.toString(),mapIntentParam);

                }


                return null;
            }
        });
    }

    private  void performJumpAction(String uri, HashMap<String, Object> mapIntentParam) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));

        Bundle bundle = new Bundle();
        for (Map.Entry<String,Object> entry : mapIntentParam.entrySet()){

            String key = entry.getKey();
            Object value = entry.getValue();

            if (value instanceof Integer) {
                bundle.putInt(key, Integer.parseInt(value.toString()));
            } else if (value instanceof Long) {
                bundle.putLong(key, Long.parseLong(value.toString()));
            } else if (value instanceof Double) {
                bundle.putDouble(key, Double.parseDouble(value.toString()));
            } else if (value instanceof Short) {
                bundle.putShort(key, Short.parseShort(value.toString()));
            } else if (value instanceof Float) {
                bundle.putFloat(key, Float.parseFloat(value.toString()));
            } else if (value instanceof String) {
                bundle.putString(key, (String) value);
            } else if (value instanceof CharSequence) {
                bundle.putCharSequence(key, (CharSequence) value);
            } else if (value.getClass().isArray()) {
                if (int[].class.isInstance(value)) {
                    bundle.putIntArray(key, (int[]) value);
                } else if (long[].class.isInstance(value)) {
                    bundle.putLongArray(key, (long[]) value);
                } else if (double[].class.isInstance(value)) {
                    bundle.putDoubleArray(key, (double[]) value);
                } else if (short[].class.isInstance(value)) {
                    bundle.putShortArray(key, (short[]) value);
                } else if (float[].class.isInstance(value)) {
                    bundle.putFloatArray(key, (float[]) value);
                } else if (String[].class.isInstance(value)) {
                    bundle.putStringArray(key, (String[]) value);
                } else if (CharSequence[].class.isInstance(value)) {
                    bundle.putCharSequenceArray(key, (CharSequence[]) value);
                } else if (Parcelable[].class.isInstance(value)) {
                    bundle.putParcelableArray(key, (Parcelable[]) value);
                }
            } else if (value instanceof ArrayList && !((ArrayList) value).isEmpty()) {
                ArrayList list = (ArrayList) value;
                if (list.get(0) instanceof Integer) {
                    bundle.putIntegerArrayList(key, (ArrayList<Integer>) value);
                } else if (list.get(0) instanceof String) {
                    bundle.putStringArrayList(key, (ArrayList<String>) value);
                } else if (list.get(0) instanceof CharSequence) {
                    bundle.putCharSequenceArrayList(key, (ArrayList<CharSequence>) value);
                } else if (list.get(0) instanceof Parcelable) {
                    bundle.putParcelableArrayList(key, (ArrayList<? extends Parcelable>) value);
                }
            } else if (value instanceof Parcelable) {
                bundle.putParcelable(key, (Parcelable) value);
            } else if (value instanceof Serializable) {
                bundle.putSerializable(key, (Serializable) value);
            } else {
                throw new IllegalArgumentException("不支持的参数类型！");
            }
        }

         intent.putExtras(bundle);

        PackageManager packageManager = context.getPackageManager();
        List<ResolveInfo> activities = packageManager.queryIntentActivities(intent,0);

        if (!activities.isEmpty()) {
            context.startActivity(intent);

    }



    }
}
