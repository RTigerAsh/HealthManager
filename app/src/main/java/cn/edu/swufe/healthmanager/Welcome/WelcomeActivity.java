package cn.edu.swufe.healthmanager.Welcome;

import android.os.Bundle;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;

import cn.edu.swufe.healthmanager.R;
import cn.edu.swufe.healthmanager.ui.activity.Login;


public class WelcomeActivity extends Activity {

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
                    enterGuideActivity();
                    finish(); /*使用finish将该activity进行销毁，否则，在按下手机返回键时，会返回至启动页*/
                }
            },2000);

            return;
        }
        /*如果不是第一次启动app，则开屏页面之后进入主页面*/
        setContentView(R.layout.activity_welcome);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                /*2秒后进入主页*/
                enterHomeActivity();
            }
        },2000);
    }
//
    private void enterGuideActivity(){
        Intent intent = new Intent(this, WelcomeGuideActivity.class);
        startActivity(intent);
        finish();
    }
    private void enterHomeActivity(){
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
        finish();
    }
}

