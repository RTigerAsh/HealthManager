package cn.edu.swufe.healthmanager.Welcome;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;

import cn.edu.swufe.healthmanager.common.RequsestApi;
import cn.edu.swufe.healthmanager.model.ServerResult;
import cn.edu.swufe.healthmanager.model.entities.UserEntity;
import cn.edu.swufe.healthmanager.util.JsonUtil;
import cn.edu.swufe.healthmanager.util.OkHttpCallback;
import cn.edu.swufe.healthmanager.util.OkHttpUtils;
import okhttp3.Call;

public class WelcomeViewModel extends ViewModel {

    private MutableLiveData<ServerResult<UserEntity>> serverResultMutableLiveData = new MutableLiveData<>();

    LiveData<ServerResult<UserEntity>> getUserInfoReslut(){return serverResultMutableLiveData;}


    public void getUserInfo(String token){
        String urlGetUseInfo = String.format(RequsestApi.URL_GET_USER_INFO, token);

        OkHttpUtils.get(urlGetUseInfo, new OkHttpCallback(){
            @Override
            public void onFailure(Call call, IOException e) {
                super.onFailure(call, e);
                serverResultMutableLiveData.postValue(ServerResult.createLinkFail());
            }

            @Override
            public void onFinish(boolean isSuccess, String rlt) {
                super.onFinish(isSuccess, rlt);
                Type type = new TypeToken<ServerResult<UserEntity>>(){}.getType();

                ServerResult<UserEntity> userEntityServerResult = JsonUtil.toMutiModel(rlt, type);

                if(userEntityServerResult != null){
                    serverResultMutableLiveData.postValue(userEntityServerResult);
                }else {
                    serverResultMutableLiveData.postValue(ServerResult.createLinkFail());
                }

            }
        });
    }

}
