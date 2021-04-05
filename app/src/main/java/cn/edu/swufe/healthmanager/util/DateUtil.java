package cn.edu.swufe.healthmanager.util;

import java.text.SimpleDateFormat;
import java.util.Date;
public class DateUtil {
    private static final String JUST_NOW = "刚刚";
    private static final String ERROR = "系统时间错误";

    private static final long SECOND = 1000;

    private static final long MIN = 60 * 1000;
    private static final long HOUR = 60 * 60 * 1000;
    private static final long DAY = 24 * 60 * 60 * 1000;
    private static final long MONTH = 30 * 24 * 60 * 60 * 1000;
    private static final long YEAR = 365 * 30 * 24 * 60 * 60 * 1000;


    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public static String toFormString(Date date){
        return simpleDateFormat.format(date);
    }

    //TODO: 按照常量转化对应的时间格式
//    public static String toFormString(Date date, String form){
//        String formedDate;
//
//        switch (form){
//            case DateUtil.HOUR_FORM:
//                formedDate =
//
//
//        }
//        return formedDate;
//    }

    // 转化为，距离现在时间多少分钟之前
    public static String calDateDifference(Date date){
        long difference = new Date().getTime() - date.getTime();

        if(date == null || difference<0){
            return ERROR;
        }

        if(difference < SECOND){
            return JUST_NOW;
        }else if(difference < MIN){
            return (difference / SECOND) + "秒之前";

        }else if(difference < HOUR){
            return (difference / MIN) + "分钟之前";

        }else if(difference < DAY){
            return (difference / HOUR) + "小时之前";

        }else if(difference < MONTH){
            return(difference / DAY) + "天之前";

        }else if(difference < YEAR){
            return (difference / MONTH) + "个月之前";
        }else{
            return (difference / YEAR) + "年之前";
        }
    }


}
