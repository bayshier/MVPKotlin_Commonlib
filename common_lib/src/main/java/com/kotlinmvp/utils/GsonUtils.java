package com.kotlinmvp.utils;

import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.List;

/**
 */
public class GsonUtils {

    public static String createGsonString(Object object) {
        Gson gson = new Gson();
        String gsonString = gson.toJson(object);
        return gsonString;
    }

    public static <T> T changeGsonToBean(String gsonString, Class<T> cls) {
        Gson gson = new Gson();
        T t = gson.fromJson(gsonString, cls);
        return t;
    }

    public static <T> List<T> changeGsonToList(String gsonString, Type type) {
        Gson gson = new Gson();
        List<T> list = gson.fromJson(gsonString, type);
        return list;
    }

}

