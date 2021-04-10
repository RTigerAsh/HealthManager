package cn.edu.swufe.healthmanager.util;

import cn.edu.swufe.healthmanager.common.Configs;
import cn.edu.swufe.healthmanager.model.entities.CommentEntity;
import okhttp3.HttpUrl;

public class UrlUtil {

//    // 用户内容Api
//    private static final HttpUrl.Builder user_api_builder = new HttpUrl.Builder()
//            .scheme("http")
//            .host(Configs.SERVER_IP)
//            .port(Integer.parseInt(Configs.SERVER_PORT))
//            .addPathSegment("user")
//            .addPathSegment("article");
//
//    // 后台API
//    private static final HttpUrl.Builder admin_api_builder = new HttpUrl.Builder()
//            .scheme("http")
//            .host(Configs.SERVER_IP)
//            .port(Integer.parseInt(Configs.SERVER_PORT))
//            .addPathSegment("admin")
//            .addPathSegment("category");

    //TODO：将基础无参数的api分离成 getBuilder方法
    public static HttpUrl.Builder userApi(){
        return new HttpUrl.Builder()
                .scheme("http")
                .host(Configs.SERVER_IP)
                .port(Integer.parseInt(Configs.SERVER_PORT))
                .addPathSegment("user");
    }


    public static HttpUrl.Builder userArticleApi(){
        return userApi()
                .addPathSegment("article");
    }



    public static HttpUrl getPostArticleUrl(String tokenKey){
        HttpUrl url = userArticleApi()
                .addQueryParameter("tokenKey", tokenKey)
                .build();
        return url;

    }

    public static HttpUrl getCategoryListUrl(){
        HttpUrl url = new HttpUrl.Builder()
                .scheme("http")
                .host(Configs.SERVER_IP)
                .port(Integer.parseInt(Configs.SERVER_PORT))
                .addPathSegments("admin/category/list/0/20").build();


        return url;


    }

    public static String getImage(String userAvatar) {
        HttpUrl url = new HttpUrl.Builder()
                .scheme("http")
                .host(Configs.SERVER_IP)
                .port(Integer.parseInt(Configs.SERVER_PORT))
                .addPathSegments("user/image")
                .addPathSegment(userAvatar)
                .build();
        return url.toString();
    }

    public static String uploadCommentURL(String tokenKey){
        HttpUrl url = new HttpUrl.Builder()
                .scheme("http")
                .host(Configs.SERVER_IP)
                .port(Integer.parseInt(Configs.SERVER_PORT))
                .addPathSegments("user/comment")
                .setQueryParameter("token_key", tokenKey)
                .build();
        return url.toString();

    }

    public static String getCommentsListURL(int page, int size, String questionId){
        HttpUrl url = new HttpUrl.Builder()
                .scheme("http")
                .host(Configs.SERVER_IP)
                .port(Integer.parseInt(Configs.SERVER_PORT))
                .addPathSegments("user/comment/list")
                .addPathSegment(String.valueOf(page))
                .addPathSegment(String.valueOf(size))
                .addQueryParameter("article_id", questionId)
                .build();
        return url.toString();
    }
}
