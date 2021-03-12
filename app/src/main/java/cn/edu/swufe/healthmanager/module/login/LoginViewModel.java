package cn.edu.swufe.healthmanager.module.login;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.io.IOException;
import java.io.InputStream;

import cn.edu.swufe.healthmanager.R;
import cn.edu.swufe.healthmanager.common.Configs;
import cn.edu.swufe.healthmanager.common.RequsestApi;
import cn.edu.swufe.healthmanager.model.ServerResult;
import cn.edu.swufe.healthmanager.util.OkHttpCallback;
import cn.edu.swufe.healthmanager.util.OkHttpUtils;
import cn.edu.swufe.healthmanager.util.PasswordUtil;
import cn.edu.swufe.healthmanager.util.SharedPreferencesUtil;
import cn.edu.swufe.healthmanager.util.JsonUtil;
import okhttp3.Call;
import okhttp3.Response;

public class LoginViewModel  extends ViewModel {
    private final String TAG = this.getClass().getSimpleName();

    private String captchaKey;


    private String loginJson = "{\n" +
            "\"userName\": \"%s\",\n" +
            "\"password\": \"%s\"" +
            "}";


    private MutableLiveData<ServerResult<Bitmap>> resultCaptchaGraph = new MutableLiveData<>();
    private MutableLiveData<ServerResult> resultLoginResult = new MutableLiveData<>();
    private MutableLiveData<LoginFormState> loginFormStateMutableLiveData = new MutableLiveData<>();


    LiveData<ServerResult<Bitmap>> getCaptchaReslut(){return resultCaptchaGraph;}
    LiveData<LoginFormState> getLoginFormState(){return  loginFormStateMutableLiveData;}
    LiveData<ServerResult> getLoginReslut(){return resultLoginResult;}


    // 请求验证码
    public void getCaptcha(){

        // 1. 获得当前时间戳，保存在captchaKey中
        captchaKey = String.valueOf(System.currentTimeMillis());


        // 2. 构建请求
        String url = String.format(RequsestApi.URL_CAPTCHA, captchaKey);
        Log.i(TAG, "captchaUrl: " + url);
        OkHttpUtils.get(url, new OkHttpCallback(){
            @Override
            public void onFailure(Call call, IOException e) {
                super.onFailure(call, e);
                resultCaptchaGraph.postValue(ServerResult.createLinkFail());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream in = response.body().byteStream();
                Bitmap captchaImg = BitmapFactory.decodeStream(in);
                ServerResult<Bitmap> bitmapServerResult = new ServerResult<>();
                bitmapServerResult.setData(captchaImg);
                resultCaptchaGraph.postValue(bitmapServerResult);
            }
        });



    }

    // 进行Login操作
    public void login(String captcha, String username, String password){
        Response response = null;

        // 密码加密
        password = PasswordUtil.encodePassword(password);


        // 构造请求Json
        String requestJson = String.format(loginJson, username, password);
        Log.i(TAG, "requestStr: " + requestJson);

        // 构造URL
        String url = String.format(RequsestApi.URL_LOGIN, captcha, captchaKey);
        Log.i(TAG, "url_login: " + url);

        // 2. 发出http请求
        OkHttpUtils.post(url, requestJson, new OkHttpCallback(){
            @Override
            public void onFailure(Call call, IOException e) {
                super.onFailure(call, e);
                resultLoginResult.postValue(ServerResult.createLinkFail());
            }

            @Override
            public void onFinish(boolean isSuccess, String rlt) {
                super.onFinish(isSuccess, rlt);
                // 连接成功
                if(isSuccess){
                    // 解析Json数据
                    resultLoginResult.postValue(JsonUtil.toModel(rlt, ServerResult.class));
                }else{
                    // 响应错误
                    resultLoginResult.postValue(ServerResult.createLinkFail());
                }
            }
        });
    }

    // 保存Token
    public void saveData(SharedPreferencesUtil sharedPreferencesUtil, String tokenKey){
        sharedPreferencesUtil.putObject("TokenKey", tokenKey);
    }

    // 输入数据验证
    public void loginDataChanged(String username, String password, String captchaCode){
        if(!isUserNameValid(username)){
            loginFormStateMutableLiveData.setValue(new LoginFormState(R.string.invalid_username, null, null));

        }else if(!isUserNameExist(username)){
            loginFormStateMutableLiveData.setValue(new LoginFormState(R.string.not_exist_usename, null, null));

        } else if (!isPasswordValid(password)){
            loginFormStateMutableLiveData.setValue(new LoginFormState(null, R.string.invalid_password, null));

        }else if (!isCaptChaCodeValid(captchaCode)){
            loginFormStateMutableLiveData.setValue(new LoginFormState(null, null, R.string.invalid_captchaCode));

        }else{
            loginFormStateMutableLiveData.setValue(new LoginFormState());
        }
    }

    // 用户名验证
    private boolean isUserNameValid(String username){
        // 空参数判定
        if(username == null){
            return false;
        }


        return !username.trim().isEmpty();
    }

    // 其他判定（用户名不存在等）
    private boolean isUserNameExist(String username){
        return true;
    }


    // 密码验证
    private boolean isPasswordValid(String password){
        return password != null && password.trim().length() > Configs.MIN_PASSWORD_LENGTH;
    }

    // 验证码验证
    private boolean isCaptChaCodeValid(String captchaCode){
        // 其他判定（验证码长度限制等）

        return !captchaCode.trim().isEmpty();
    }


}
