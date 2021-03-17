package cn.edu.swufe.healthmanager.module.login;

import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import org.litepal.LitePal;

import java.util.List;

import cn.edu.swufe.healthmanager.MainActivity;
import cn.edu.swufe.healthmanager.R;
import cn.edu.swufe.healthmanager.common.Configs;
import cn.edu.swufe.healthmanager.db.LoginUser;
import cn.edu.swufe.healthmanager.db.model.User;
import cn.edu.swufe.healthmanager.model.ServerResult;
import cn.edu.swufe.healthmanager.model.entities.UserEntity;
import cn.edu.swufe.healthmanager.module.community.CommunityActivity;
import cn.edu.swufe.healthmanager.ui.activity.BaseDataFragment.GetBaseData;
import cn.edu.swufe.healthmanager.util.MD5;
import cn.edu.swufe.healthmanager.util.SharedPreferencesUtil;
import cn.edu.swufe.healthmanager.util.ToastUtils;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private final String TAG = this.getClass().getSimpleName();
    private volatile boolean doLogin = true;

    private EditText et_name,et_password, et_captcha, et_ensure_password;
    private Button bt_login;

    private ImageView iv_eye,iv_more_account, iv_captcha;
    private LoginViewModel loginViewModel;
    private SharedPreferencesUtil sharedPreferencesUtil;

    private ProgressBar loading;

    private CheckBox cb_remember;
    private boolean passwordVisible = false;
    private ToastUtils toastUtils = new ToastUtils();
    private TextView register;
    private LinearLayout ly_captcha, ly_ensure, ly_login, ly_login_background;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginViewModel = new ViewModelProvider(this, new LoginVIewModelFactory()).get(LoginViewModel.class);

        initView();


        // 设置动画
        LayoutTransition transition = new LayoutTransition();

        ObjectAnimator animator_x = ObjectAnimator.ofFloat(null, "scaleX",   1, 0);
        ObjectAnimator animator_y = ObjectAnimator.ofFloat(null, "scaleX",  0, 1);


        transition.setAnimator(LayoutTransition.DISAPPEARING, animator_x);
        transition.setAnimator(LayoutTransition.APPEARING, animator_y);
        ly_login.setLayoutTransition(transition);


        // 设置监听器
        bt_login.setOnClickListener(this);
        register.setOnClickListener(this);
        iv_eye.setOnClickListener(this);
        iv_more_account.setOnClickListener(this);
        iv_captcha.setOnClickListener(this);


        sharedPreferencesUtil = SharedPreferencesUtil.getInstance(this);

        // 检测验证码获取
        loginViewModel.getCaptchaResult().observe(this, new Observer<ServerResult<Bitmap>>() {
            @Override
            public void onChanged(ServerResult<Bitmap> bitmapServerResult) {
                if(bitmapServerResult == null){
                    return;
                }

                if(bitmapServerResult.isSuccess()) {
                    // 显示验证码
                    iv_captcha.setImageBitmap(bitmapServerResult.getData());
                }else{
                    showLoginFailed(bitmapServerResult.getMsg());
                }

            }
        });


        // 检测用户输入判断结果
        loginViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {
            @Override
            public void onChanged(LoginFormState loginFormState) {
                if(loginFormState == null){
                    return;
                }
                // 根据用户输入，决定登录按钮是否有效
                bt_login.setEnabled(loginFormState.isDataValid());

                if(loginFormState.getUsernameError() !=null){
                    et_name.setError(getString(loginFormState.getUsernameError()));
                }

                if(loginFormState.getPasswordError() !=null){
                    et_password.setError(getString(loginFormState.getPasswordError()));
                }

                if(loginFormState.getCaptchaCodeError() !=null){
                    et_captcha.setError(getString(loginFormState.getCaptchaCodeError()));
                }

                if(loginFormState.getEnsurePasswordError() != null && !doLogin){
                    Log.i(TAG, "LoginFormState - > getEnsurePasswordError ->" + getString(loginFormState.getEnsurePasswordError()));
                    et_ensure_password.setError(getString(loginFormState.getEnsurePasswordError()));
                }

            }
        });

        // 检测登录/注册的结果
        loginViewModel.getRequestResult().observe(this, new Observer<ServerResult<UserEntity>>() {
            @Override
            public void onChanged(ServerResult<UserEntity> userEntityServerResult) {
                if(userEntityServerResult == null){
                    return;
                }
                loading.setVisibility(View.GONE);
                bt_login.setVisibility(View.VISIBLE);

                if(!userEntityServerResult.isSuccess() || userEntityServerResult.getData() == null){
                    // 登录/注册失败，显示失败信息
                    showLoginFailed(userEntityServerResult.getMsg());

                }else{

                    // 登录/注册成功，显示成功信息
                    updateUiWithUser(userEntityServerResult.getMsg());
                    final String token = userEntityServerResult.getToken();

                    Log.i(TAG, "Token ->" + token);
                    // 存储Token
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            loginViewModel.saveData(sharedPreferencesUtil, token);
                        }
                    }, "ThreadSaveToken").start();


                    toastUtils.showShort(LoginActivity.this,"账户"+userEntityServerResult.getData().getUserName()+" 登录成功");

                    // 存储LoginUser，设置跳转的页面
                    UserEntity loginedUser = userEntityServerResult.getData();
                    loginedUser.setTokenKey(token);

                    cn.edu.swufe.healthmanager.model.LoginUser.getInstance().updateUserEntity(loginedUser);


                    Intent intent = new Intent(LoginActivity.this, CommunityActivity.class);
                    intent.putExtra(Configs.SP_TOKEN_KEY, token);

                    // 跳转页面
                    startActivity(intent);
                    finish();
                }


            }
        });


        // 检测文本输入
        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {
                // 一旦输入框修改完毕，就立刻更新输入状态
                loginViewModel.editTextDataChanged(
                        et_name.getText().toString(),
                        et_password.getText().toString(),
                        et_ensure_password.getText().toString(),
                        et_captcha.getText().toString(),
                        doLogin);
            }
        };

        et_name.addTextChangedListener(afterTextChangedListener);
        et_password.addTextChangedListener(afterTextChangedListener);
        et_captcha.addTextChangedListener(afterTextChangedListener);
        et_ensure_password.addTextChangedListener(afterTextChangedListener);

        // 监控输入完后的键盘操作
        et_captcha.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_DONE){ // 输入完毕，直接进行登录

                    loginViewModel.doRequest(
                            et_captcha.getText().toString(),
                            et_name.getText().toString(),
                            et_password.getText().toString(),
                            doLogin);
                }

                return false;
            }
        });
    }

    private void initView() {
        // EditText
        et_name = findViewById(R.id.et_account_name);
        et_password =  findViewById(R.id.et_password);
        et_ensure_password = findViewById(R.id.et_ensure_password);
        et_captcha = findViewById(R.id.et_captcha);

        // Button
        bt_login = findViewById(R.id.login);

        // LinearLayout
        ly_login_background = findViewById(R.id.login_background_ly);
        ly_captcha = findViewById(R.id.ly_captcha);
        ly_ensure = findViewById(R.id.ly_ensure);
        ly_login = findViewById(R.id.ly_login);

        // ImageView
        iv_captcha = findViewById(R.id.iv_captcha);
        iv_eye =  findViewById(R.id.iv_eye);
        iv_more_account = findViewById(R.id.iv_more_accout);

        // Text
        register = findViewById(R.id.register);

        // CheckBox
        cb_remember = findViewById(R.id.cb_remember);

        // ProcessBar
        loading = findViewById(R.id.loading);
    }


    @Override
    protected void onStart() {
        super.onStart();
        // 请求验证码
        loginViewModel.getCaptcha();

    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //注册按钮的逻辑， 点一次进入注册， 再点一次返回登录
            case R.id.register:
                if(doLogin){
                    // 显示再次输入密码框
                    ly_login_background.setBackgroundResource(R.drawable.wallpape_22);
                    ly_ensure.setVisibility(View.VISIBLE);
                    register.setText(R.string.back_login);
                    // 设置按钮为注册
                    bt_login.setText(R.string.register);
                    doLogin = false;
                }else{
                    // 隐藏再次输入密码框
                    ly_login_background.setBackgroundResource(R.drawable.wallpaper_11);
                    ly_ensure.setVisibility(View.GONE);
                    register.setText(R.string.back_login);
                    // 设置按钮为登录
                    bt_login.setText(R.string.login);
                    doLogin = true;
                }
                break;
            //登录按钮的逻辑
            case R.id.login:

                loginViewModel.doRequest(
                        et_captcha.getText().toString(),
                        et_name.getText().toString(),
                        et_password.getText().toString(),
                        doLogin);

//                loginProcess(name, password);
                break;
            //隐藏密码功能
            case R.id.iv_eye:
                if(passwordVisible){  //如果可见，则转为不可见
                    iv_eye.setSelected(false);
                    et_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    passwordVisible = false;
                }else {  //如果不可见，则转为可见
                    iv_eye.setSelected(true);
                    et_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    passwordVisible = true;
                }
                break;
            //显示本地所有登录信息
            case R.id.iv_more_accout:
                List<User> users1 = LitePal.findAll(User.class);
                for(User u:users1) Log.d("food",""+u.toString());
                break;
            // 刷新验证码
            case R.id.iv_captcha:
                loginViewModel.getCaptcha();

        }
    }

    private void updateUiWithUser(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }

    private void showLoginFailed(String errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }


    @Deprecated
    private void loginProcess(String name, String password) {
        boolean login_flag = false; //是否登录成功的标志，
        User user = LitePal.where("name=?",name).findFirst(User.class);

        //根据user的remember状态，判断是否需要MD5加密
        if (password.equals("12345678")) password = user.getPassword();
        else password = MD5.md5(password);
        //密码正确则登录成功
        if (user.checkPassword(password)){
            //更新remember状态
            if(cb_remember.isChecked()) {
                user.setRemember(1);
            }else{
                user.setRemember(0);
            }
            user.update(user.getId());
            //用户登入，存入LoginUser
            LoginUser.getInstance().login(user);

            //第一次注册的用户登录跳转到基础信息获取页面
            if(getIntent().getBooleanExtra("first_register",true)) {
                Log.d("health","first_register");
                Intent intent1 = new Intent(LoginActivity.this, GetBaseData.class);
                startActivity(intent1);

            }else {
                //启动主界面


                Intent intent1 = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent1);
                login_flag = true;


                toastUtils.showShort(LoginActivity.this,"账户"+user.getName()+" 登录成功");
                return;
            }
        }else {
            user.setRemember(0);
        }

        if(login_flag == false){
            toastUtils.showShort(LoginActivity.this,"登录失败");
        }
    }
}
