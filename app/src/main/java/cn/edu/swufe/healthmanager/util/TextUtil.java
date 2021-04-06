package cn.edu.swufe.healthmanager.util;

public class TextUtil {
    public static String TextFormat(String oriStr){
        return oriStr.replaceAll("\n", "\\\\n");
    }
}
