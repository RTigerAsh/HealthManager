package cn.edu.swufe.healthmanager.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.HashMap;

import cn.edu.swufe.healthmanager.model.ServerResult;
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


    public static void get_test(String url, OkHttpCallback_test callback){
        callback.setUrl(url);
        Request request = new Request.Builder()
                .url(url)
                .build();
        CLIENT.newCall(request).enqueue(callback);

    }

    public static void post_test(String url, String json, OkHttpCallback_test callback){
        callback.setUrl(url);
        RequestBody body = FormBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        CLIENT.newCall(request).enqueue(callback);
    }

    public static void main(String args[]){


        String captchacode = "vF3KP";
        String url_captcha = "http://localhost:9001/user/captcha?captchakey=1234567890123";

        String url_captcha_portal = "http://localhost:9001/test/captcha_portal?captcha_key=1234567890123";

        String url_login = "http://localhost:9001/user/"+ captchacode +"/1234567890123";


        String jsonStr = "{\n" +
                "    \"userName\": \"test_user_fhl\",\n" +
                "    \"password\": \"123456\"\n" +
                "}";

//        OkHttpUtils.get_test(url_captcha, new OkHttpCallback_test());

        Request request = new Request.Builder()
                .url(url_captcha_portal)
                .build();

        try {
            Response response = CLIENT.newCall(request).execute();
            Gson gson = new Gson();
            ServerResult<HashMap<String, String>> rsl = gson.fromJson(response.body().string(), new TypeToken<ServerResult<HashMap<String, String>>>(){}.getType());
            System.out.println(rsl.getData().get("image"));

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
