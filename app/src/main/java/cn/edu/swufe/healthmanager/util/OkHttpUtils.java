package cn.edu.swufe.healthmanager.util;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;

import cn.edu.swufe.healthmanager.model.ServerResult;
import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHttpUtils {
    private static final OkHttpClient CLIENT = new OkHttpClient();


    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    /**
     * get请求
     * @param url
     * @param callback
     */
    public static void get(String url, OkHttpCallback callback){
        callback.setUrl(url);
        Request request = new Request.Builder()
                .url(url)
                .build();
        CLIENT.newCall(request).enqueue(callback);

    }



    /**
     * post请求
     * @param url
     * @param json
     * @param callback
     */
    public static void post(String url, String json, OkHttpCallback callback){
        callback.setUrl(url);
        RequestBody body = FormBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        CLIENT.newCall(request).enqueue(callback);
    }

    public static Response post(String url, String json) throws IOException {
        RequestBody body = FormBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        return CLIENT.newCall(request).execute();
    }


    public static void get_test(HttpUrl url, MutableLiveData date, Type type){
        MyOkHttpCallback callback = new MyOkHttpCallback(date, type);
        Log.i("OkHttpUtils", "RequestAt: " + url.toString());
        Request request = new Request.Builder()
                .get()
                .url(url)
                .build();
        CLIENT.newCall(request).enqueue(callback);
    }

    public static void get_test(String url, MutableLiveData date, Type type){
        MyOkHttpCallback callback = new MyOkHttpCallback(date, type);
        Log.i("OkHttpUtils", "RequestAt: " + url.toString());
        Request request = new Request.Builder()
                .get()
                .url(url)
                .build();
        CLIENT.newCall(request).enqueue(callback);
    }

    public static void post_test(String url, String json,  MutableLiveData date, Type type){
        MyOkHttpCallback callback = new MyOkHttpCallback(date, type);
        Log.i("OkHttpUtils", "RequestAt: " + url.toString());
        RequestBody body = FormBody.create(JSON, json);
        Request request = new Request.Builder()
                .post(body)
                .url(url)
                .build();
        CLIENT.newCall(request).enqueue(callback);
    }

}
