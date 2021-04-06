package cn.edu.swufe.healthmanager.module.login;

import androidx.annotation.Nullable;

public class LoginFormState {
    @Nullable
    private Integer usernameError;
    @Nullable
    private Integer passwordError;
    @Nullable
    private Integer captchaCodeError;
    @Nullable
    private Integer ensurePasswordError = null;
    private  boolean isDataValid;

    LoginFormState(@Nullable Integer usernameError, @Nullable Integer passwordError, @Nullable Integer captchaCodeError){
        this.usernameError = usernameError;
        this.passwordError = passwordError;
        this.captchaCodeError = captchaCodeError;
        this.isDataValid = false;
    }

    LoginFormState(@Nullable Integer usernameError, @Nullable Integer passwordError, @Nullable Integer captchaCodeError, @Nullable Integer ensurePasswordError){
        this.usernameError = usernameError;
        this.passwordError = passwordError;
        this.captchaCodeError = captchaCodeError;
        this.ensurePasswordError = ensurePasswordError;
        this.isDataValid = false;
    }


    LoginFormState(){
        this.usernameError = null;
        this.passwordError = null;
        this.captchaCodeError = null;
        this.isDataValid = true;
    }

    @Nullable
    public Integer getUsernameError() {
        return usernameError;
    }

    @Nullable
    public Integer getPasswordError() {
        return passwordError;
    }

    @Nullable
    public Integer getCaptchaCodeError() {
        return captchaCodeError;
    }

    @Nullable
    public Integer getEnsurePasswordError() {
        return ensurePasswordError;
    }


    public boolean isDataValid() {
        return isDataValid;
    }
}
