package cn.edu.swufe.healthmanager.util;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
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
        Request request = new Request.Builder().url(url).build();
        CLIENT.newCall(request).enqueue(callback);
    }

    public static void get_ex(String url, OkHttpCallback callback){
        callback.setUrl(url);
        Request request = new Request.Builder().url(url).build();
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
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder().url(url).build();
        CLIENT.newCall(request).enqueue(callback);
    }

    public static void main(String args[]){


        String captchacode = "sGUd5";

        String url_login = "http://47.119.132.173:9001/user/"+ captchacode +"/1234567890123";


        String jsonStr = "{\n" +
                "    \"userName\": \"test_user_2\",\n" +
                "    \"password\": \"123456\"\n" +
                "}";


        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
        RequestBody requestBody = FormBody.create(mediaType, jsonStr);

        Request request = new Request.Builder()
                .url(url_login)
                .post(requestBody)
                .build();
        Call call = CLIENT.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.printf("Failure");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.printf("Success: " + response.body().string());

            }
        });



    }
}
