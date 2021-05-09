package cn.edu.swufe.healthmanager.Welcome;

import android.os.Bundle;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.UiThread;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;

import cn.edu.swufe.healthmanager.R;
import cn.edu.swufe.healthmanager.common.Configs;
import cn.edu.swufe.healthmanager.common.RequsestApi;
import cn.edu.swufe.healthmanager.model.LoginUser;
import cn.edu.swufe.healthmanager.model.ServerResult;
import cn.edu.swufe.healthmanager.model.entities.UserEntity;
import cn.edu.swufe.healthmanager.module.community.CommunityActivity;
import cn.edu.swufe.healthmanager.module.login.LoginActivity;
import cn.edu.swufe.healthmanager.ui.activity.Login;
import cn.edu.swufe.healthmanager.ui.activity.MainAvtivity.MainActivity;
import cn.edu.swufe.healthmanager.util.JsonUtil;
import cn.edu.swufe.healthmanager.util.OkHttpCallback;
import cn.edu.swufe.healthmanager.util.OkHttpUtils;
import cn.edu.swufe.healthmanager.util.ToastUtils;
import okhttp3.Call;


public class WelcomeActivity extends AppCompatActivity{
    private final String TAG = getClass().getSimpleName();
    private cn.edu.swufe.healthmanager.util.SharedPreferencesUtil sharedPreferencesUtil;
    private static String tokenKey;
    private WelcomeViewModel welcomeViewModel;

    private final String MAIN = "main";
    private final String LOGIN = "login";
    private final String GUIDE = "guide";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*首先启动该Activity，并判断是否是第一次启动
         * 如果是第一次启动，则先展示开屏页面之后进入功能引导页*/
        boolean isFirstOpen = SharedPreferencesUtil.getBoolean(this, SharedPreferencesUtil.FIRST_OPEN, true);
        if(isFirstOpen){
            System.out.println("运行到这里");
            setContentView(R.layout.activity_welcome);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    /*2秒后进入主页*/
                    enterAcitity(LOGIN);//GUIDE
                    finish(); /*使用finish将该activity进行销毁，否则，在按下手机返回键时，会返回至启动页*/
                }
            },2000);

            return;
        }

//        /*如果不是第一次启动app，则开屏页面之后进入主页面*/
//        setContentView(R.layout.activity_welcome);
//
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                /*2秒后进入主页*/
//                enterHomeActivity();
//            }
//        },2000);


        welcomeViewModel =  new ViewModelProvider.NewInstanceFactory().create(WelcomeViewModel.class);

        sharedPreferencesUtil = cn.edu.swufe.healthmanager.util.SharedPreferencesUtil.getInstance(this);

        // 1. 读取SP中的Token
        tokenKey  = sharedPreferencesUtil.readString(Configs.SP_TOKEN_KEY);

        Log.i(TAG, "token == " + tokenKey);

        // 2. 请求服务器验证
        if(tokenKey.equals("")){ // 用户未登录，跳转登录页面
            Log.i(TAG, "toLogin? " + true);
            enterAcitity(LOGIN);
        }else{
            welcomeViewModel.getUserInfo(tokenKey);
        }

        welcomeViewModel.getUserInfoReslut().observe(this, new Observer<ServerResult<UserEntity>>() {
            @Override
            public void onChanged(ServerResult<UserEntity> userEntityServerResult) {
                if(userEntityServerResult == null){
                    return;
                }
                Log.i(TAG, "isSuccess: " + userEntityServerResult.isSuccess());

                if(userEntityServerResult.getCode()==3){ // 连接失败
                    showFailMsg(userEntityServerResult.getMsg());
                }else if(userEntityServerResult.isSuccess()){
                    // 3. 请求成功，创建单例对象，保存用户数据
                    UserEntity userEntity = userEntityServerResult.getData();

                    UserEntity loginedUser = userEntityServerResult.getData();
                    loginedUser.setTokenKey(tokenKey);
                    LoginUser.getInstance().updateUserEntity(loginedUser);

                    // 4. 刷新token
                    sharedPreferencesUtil.putString(Configs.SP_TOKEN_KEY, userEntityServerResult.getToken());

                    // 5. 跳转主页面
                    enterAcitity(MAIN);



                }else{
                    // 6. token验证失败则提示用户登录
                    showFailMsg(userEntityServerResult.getMsg());
                    enterAcitity(LOGIN);
                }

            }
        });



    }

    private void showFailMsg(String msg){

        new ToastUtils().showShort(this, msg);
    }

    private void enterAcitity(String activityName){
        Intent intent;

        switch (activityName){
            case "guide":
                intent = new Intent(this, WelcomeGuideActivity.class);
                break;
            case "login":
                intent = new Intent(this, LoginActivity.class);
                break;
            case "main":
                intent = new Intent(this, MainActivity.class);
                break;
            default:
                intent = new Intent(this, WelcomeActivity.class);
        }
        startActivity(intent);
        finish();
    }


    @Deprecated
    private void enterGuideActivity(){
        Intent intent = new Intent(this, WelcomeGuideActivity.class);
        startActivity(intent);
        finish();
    }

    @Deprecated
    private void enterHomeActivity(){
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
        finish();
    }
}

