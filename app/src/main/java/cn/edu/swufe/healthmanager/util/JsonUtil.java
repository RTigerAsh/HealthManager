package cn.edu.swufe.healthmanager.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;


public class JsonUtil {

    private static Gson gson = new Gson();

    // 转一般对象
    public static <T> T toModel(String  jsonStr, Class<T> tClass){
       return gson.fromJson(jsonStr, tClass);
    }

    public static <T> T toMutiModel(String jsonStr, Type type){
         return  gson.fromJson(jsonStr, type);
    }

    public static String  toJsonStr(Object model){
        return gson.toJson(model);
    }
}
