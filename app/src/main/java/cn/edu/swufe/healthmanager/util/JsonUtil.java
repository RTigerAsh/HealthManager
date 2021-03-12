package cn.edu.swufe.healthmanager.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;


public class JsonUtil {

    private static Gson gson = new Gson();

    public static <T> T toModel(String  jsonStr, Class<T> tClass){
       return gson.fromJson(jsonStr, tClass);
    }

    public static String  toJsonStr(Object modle){
        return gson.toJson(modle);
    }
}
