package cn.edu.swufe.healthmanager.util;

public class PasswordUtil {
    private final String SALT = "password"; // 设置盐

    public static String encodePassword(String oriPassword){
        String encodePassword = "";
        // 选择一种或多种加密方式，进行加密

        encodePassword = oriPassword;

        return encodePassword;

    }


    public static void main(String[] args) {
        String str = "abc";
        PasswordUtil.encodePassword(str);
    }
}
