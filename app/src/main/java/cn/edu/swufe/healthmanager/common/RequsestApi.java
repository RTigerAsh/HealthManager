package cn.edu.swufe.healthmanager.common;

public class RequsestApi {

    // 请求验证码，需要 captcha_key（13位时间戳）
    public static final String URL_CAPTCHA = "http://"+Configs.SERVER_AD+"/user/captcha?captcha_key=%s";

    // 登录
    public static final String URL_LOGIN = "http://"+Configs.SERVER_AD+"/user/%s/%s";
}
