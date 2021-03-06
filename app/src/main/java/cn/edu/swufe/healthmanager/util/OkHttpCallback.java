package cn.edu.swufe.healthmanager.util;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class OkHttpCallback implements Callback {
    private final String TAG = OkHttpCallback.class.getSimpleName();

    public String url;
    public String result;


    /**
     * 请求失败，则调用此方法
     * @param call
     * @param e
     */
    @Override
    public void onFailure(Call call, IOException e) {
        Log.d(TAG, "url: " + url);
        Log.d(TAG, "请求失败" + e.toString());
        onFinish(false, e.toString());
    }

    /**
     * 请求成功，则调用此方法
     * @param call
     * @param response
     * @throws IOException
     */
    @Override
    public void onResponse(Call call, Response response) throws IOException {
        Log.d(TAG, "url: "+url);
        result = response.body().string().toString();
        Log.d(TAG, "请求成功" + result);
        onFinish(true, result);
    }

    /**
     * 自定义方法，用以查看参数
     * @param isSuccess
     * @param rlt
     */
    public void  onFinish(boolean isSuccess, String rlt){
        Log.d(TAG, "url: " + url + " status: " + isSuccess );
    }



    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
