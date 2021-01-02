package cn.edu.swufe.healthmanager.Welcome;

import android.os.Bundle;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;

import cn.edu.swufe.healthmanager.MainActivity;
import cn.edu.swufe.healthmanager.R;


public class WelcomeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*首先启动该Activity，并判断是否是第一次启动
         * 如果是第一次启动，则先进入功能引导页*/
        boolean isFirstOpen = SharedPreferencesUtil.getBoolean(this, SharedPreferencesUtil.FIRST_OPEN, true);
        if(isFirstOpen){
            Intent intent = new Intent(this, WelcomeGuideActivity.class);
            startActivity(intent);

            finish(); /*使用finish将该activity进行销毁，否则，在按下手机返回键时，会返回至启动页*/
            return;
        }
        /*如果不是第一次启动app，则启动页*/
        setContentView(R.layout.activity_welcome);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                /*2秒后进入主页*/
                enterHomeActivity();
            }
        },2000);
    }

    private void enterHomeActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}

