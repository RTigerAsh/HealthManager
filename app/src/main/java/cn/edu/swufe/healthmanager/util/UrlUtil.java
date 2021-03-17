package cn.edu.swufe.healthmanager.util;

import cn.edu.swufe.healthmanager.common.Configs;
import okhttp3.HttpUrl;

public class UrlUtil {

    // 用户内容Api
    private static HttpUrl.Builder builder = new HttpUrl.Builder()
            .scheme("http")
            .host(Configs.SERVER_IP)
            .port(Integer.parseInt(Configs.SERVER_PORT))
            .addPathSegment("user")
            .addPathSegment("article");

    public static HttpUrl getPostArticleUrl(String tokenKey){
        builder.addQueryParameter("tokenKey", tokenKey);
        return builder.build();
    }
}
