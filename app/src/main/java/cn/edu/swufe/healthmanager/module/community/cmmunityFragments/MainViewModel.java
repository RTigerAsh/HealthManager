package cn.edu.swufe.healthmanager.module.community.cmmunityFragments;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import cn.edu.swufe.healthmanager.common.RequsestApi;
import cn.edu.swufe.healthmanager.model.ServerResult;
import cn.edu.swufe.healthmanager.model.entities.QuestionEntity;
import cn.edu.swufe.healthmanager.util.JsonUtil;
import cn.edu.swufe.healthmanager.util.OkHttpCallback;
import cn.edu.swufe.healthmanager.util.OkHttpUtils;
import okhttp3.Call;

public class MainViewModel extends ViewModel {
    private final String TAG = this.getClass().getSimpleName();

    private MutableLiveData<ServerResult<List<QuestionEntity>>> requestResult = new MutableLiveData<>();


    public MutableLiveData<ServerResult<List<QuestionEntity>>> getRequestResult() {
        return requestResult;
    }

    public void getQuestionList(int page, int size) {
        // 1. 构建url
        String url = String.format(RequsestApi.URL_GET_QUESTION_LIST, page, size);

        // 2. 发出get请求
        OkHttpUtils.get(url, new OkHttpCallback(){
            @Override
            public void onFinish(boolean isSuccess, String rlt) {
                super.onFinish(isSuccess, rlt);
                if(isSuccess){
                    // 3. 解析服务端数据
                    Type type = new TypeToken<ServerResult<List<QuestionEntity>>>(){}.getType();
                    ServerResult<List<QuestionEntity>> serverResult = JsonUtil.toMutiModel(rlt, type);

                    // 4. 更新LiveData
                    requestResult.postValue(serverResult);

                }else{
                    requestResult.postValue(ServerResult.createLinkFail());
                }
            }
        });

    }

}
