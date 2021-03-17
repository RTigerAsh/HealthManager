package cn.edu.swufe.healthmanager.module.community.cmmunityFragments;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import cn.edu.swufe.healthmanager.model.ServerResult;
import cn.edu.swufe.healthmanager.util.JsonUtil;
import cn.edu.swufe.healthmanager.util.OkHttpCallback;
import cn.edu.swufe.healthmanager.util.OkHttpUtils;
import cn.edu.swufe.healthmanager.util.TextUtil;
import cn.edu.swufe.healthmanager.util.UrlUtil;
import okhttp3.HttpUrl;


public class OtherViewModel extends ViewModel {
    private final String TAG = this.getClass().getSimpleName();

    private String jsonStr = "{\n" +
            "\"categoryId\": \"%s\",\n" +
            "\"content\": \"%s\",\n" +
            "\"labels\": \"%s\"\n" +
            "}";

    private MutableLiveData<ServerResult> uploadResult = new MutableLiveData<>();


    public MutableLiveData<ServerResult> getUploadResult() {
        return uploadResult;
    }

    public void uploadQuestion(String categoryId, String content, String label, String tokenKey) {
        // 1. 构建URL
        HttpUrl httpUrl = UrlUtil.getPostArticleUrl(tokenKey);

        // 2. 构建请求体
        String requestBodyStr = String.format(jsonStr, categoryId, TextUtil.TextFormat(content), label);
        Log.i(TAG, "requestBody: " + requestBodyStr);

        // 3. 发出请求，解析结果
        OkHttpUtils.post(httpUrl.toString(), requestBodyStr, new OkHttpCallback(){
            @Override
            public void onFinish(boolean isSuccess, String rlt) {
                super.onFinish(isSuccess, rlt);
                if(isSuccess){
                    // 解析结果
                    uploadResult.postValue(JsonUtil.toModel(rlt, ServerResult.class));

                }else{
                    uploadResult.postValue(ServerResult.createLinkFail());
                }
            }
        });

    }

    // TODO：从服务器获得类别，标签选项
    // TODO: 输入文本检测
}
