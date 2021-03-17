package cn.edu.swufe.healthmanager.util;

import java.text.SimpleDateFormat;
import java.util.Date;
public class DateUtil {
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public static String toFormString(Date date){
        return simpleDateFormat.format(date);
    }
}
