package cn.edu.swufe.healthmanager.db.model;

import android.util.Log;

import androidx.annotation.NonNull;

import org.litepal.LitePal;
import org.litepal.crud.LitePalSupport;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DairyBodyMessage extends LitePalSupport{


    //基本信息 每天填一次，第二次填写对数据库修改
    private long body_id;
    private String body_date;
    private Double body_weight; //体重
    private Double body_waistline;  //腰围 有一说一可以不计
    //睡眠信息
    private String body_timeoffasleep;
    private String body_timeofwake;
    private String body_timeofsleep;
    //便便   有一说一可以不计 （形状、颜色、量、感受、时长）

    //外键
    private long id;

    //多对一连接User表
    private User user;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getBody_id() {
        return body_id;
    }

    public void setBody_id(long body_id) {
        this.body_id = body_id;
    }

    public String getBody_date() {
        return body_date;
    }

    public void setBody_date(String body_date) {
        this.body_date = body_date;
    }

    public Double getBody_weight() {
        return body_weight;
    }

    public void setBody_weight(Double body_weight) {
        this.body_weight = body_weight;
    }

    public Double getBody_waistline() {
        return body_waistline;
    }

    public void setBody_waistline(Double body_waistline) {
        this.body_waistline = body_waistline;
    }

    public String getBody_timeoffasleep() {
        return body_timeoffasleep;
    }

    public void setBody_timeoffasleep(String body_timeoffasleep) {
        this.body_timeoffasleep = body_timeoffasleep;
    }

    public String getBody_timeofwake() {
        return body_timeofwake;
    }

    public void setBody_timeofwake(String body_timeofwake) {
        this.body_timeofwake = body_timeofwake;
    }

    public String getBody_timeofsleep() {
        return body_timeofsleep;
    }

    public void setBody_timeofsleep(String body_timeofsleep) {
        this.body_timeofsleep = body_timeofsleep;
    }
}
