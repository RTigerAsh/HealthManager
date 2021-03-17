package cn.edu.swufe.healthmanager.common;

@Deprecated
public class RequsestApi {

    // 请求验证码，需要 captcha_key（13位时间戳）
    public static final String URL_CAPTCHA = "http://" + Configs.SERVER_AD + "/user/captcha?captcha_key=%s";

    // 登录
    public static final String URL_LOGIN = "http://" + Configs.SERVER_AD + "/user/%s/%s";

    // 注册REGISTER
    public static final String URL_REGISTER = "http://" + Configs.SERVER_AD + "/user?captcha_code=%s&captcha_key=%s";

    // 请求用户信息（要求登录）
    public static final String URL_GET_USER_INFO = "http://" + Configs.SERVER_AD + "/user/user_info/%s";

    // 发布问题(传入 categoryId，content，label，tokenKey)
    public static final String URL_POST_QUESTION = "http://" + Configs.SERVER_AD + "/user/article?tokenKey=%s";

    // 请求问题列表(传入page，size，tokenKey)
    public static final String URL_GET_QUESTION_LIST = "http://" + Configs.SERVER_AD + "/user/article/list/%s/%s?token_key=%s";



}
