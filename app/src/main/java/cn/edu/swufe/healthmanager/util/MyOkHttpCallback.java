package cn.edu.swufe.healthmanager.util;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import java.io.IOException;
import java.lang.reflect.Type;

import cn.edu.swufe.healthmanager.model.ServerResult;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MyOkHttpCallback implements Callback {
    private final String TAG = getClass().getSimpleName();
    private MutableLiveData data;
    private Type rltType;

    public MyOkHttpCallback(MutableLiveData data, Type rltType){
        this.data = data;
        this.rltType = rltType;
    }

    @Override
    public void onFailure(Call call, IOException e) {
        Log.i(TAG, "LinkFail");
        data.postValue(ServerResult.createLinkFail());

    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
       String serverRlt  =  response.body().string();
       Log.i(TAG, "LinkSuccess: " + serverRlt);

       data.postValue(JsonUtil.toMutiModel(serverRlt, rltType));

    }
}
