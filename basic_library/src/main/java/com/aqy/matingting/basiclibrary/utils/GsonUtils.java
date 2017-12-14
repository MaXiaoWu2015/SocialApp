package com.aqy.matingting.basiclibrary.utils;

import com.google.gson.Gson;

/**
 * Created by matingting on 2017/12/12.
 */

public class GsonUtils {
    public static Gson gson = new Gson();

    public static Object string2Object(String json,Class clazz){
        return gson.fromJson(json,clazz);
    }
}
