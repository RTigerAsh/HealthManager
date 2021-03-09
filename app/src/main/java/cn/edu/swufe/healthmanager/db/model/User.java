package cn.edu.swufe.healthmanager.db.model;


import android.util.Log;

import androidx.annotation.NonNull;

import org.litepal.LitePal;
import org.litepal.crud.LitePalSupport;
import org.w3c.dom.Comment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
属性remember本可以设计为boolean值，但是LitePal的boolean值update不了，所以只能用Integer代替。
 解决：使用update更新数据
 **/
public class User extends LitePalSupport implements Comparable<User> {

    //个人信息
    private long id;
    private String name;
    private String password;
    private Integer remember;
    private byte[] portrait;
    private String region;
    private String gender;
    private String brithday;

    //预期健康信息
    private  Integer aim_style;//体重管理目标 通过标记选择（0减重 1保持/塑形 2增重/增肌）
    private  float height,weight,aim_weight,aim_time;//预期多长时间达到目标
    private ArrayList<String> Special_disease_List;//慢性病等特殊情况 标记选择(0没有 ...)


    //一对多连接各类表
    private List<DairyBodyMessage> dairybodymessageList = new ArrayList<DairyBodyMessage>();
    private List<DairySportsMessage> dairysportsmessageList = new ArrayList<DairySportsMessage>();
    private List<DairyFoodMessage> dairyfoodmessageList = new ArrayList<DairyFoodMessage>();

    //这里可以放关于在线交流的控件

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", remember=" + remember +
                ", region='" + region + '\'' +
                ", brithday='" + brithday + '\'' +
                ", gender='" + gender + '\'' +
                ", height='" + height + '\'' +
                ", weight='" + weight + '\'' +
                ", aim_weight='" + aim_weight + '\'' +
                ", aim_style='" + aim_style + '\'' +
                ", aim_time='" + aim_time + '\'' +
                ", Special_disease_List='" + Special_disease_List + '\'' +
                '}';
    }

    //check是传入未MD5加密的
    public boolean checkPassword(String str){
        if (password.equals(str)) return true;
        else return false;
    }

    @Override
    public boolean equals(Object o) {
        if (o != null) {
            User UserInfo = (User) o;
            return (getId()==UserInfo.getId());
        } else {
            return false;
        }
    }

    @Override
    public int compareTo(@NonNull User User) {
        return this.getName().compareTo(User.getName());
    }

    public byte[] getPortrait() {
        return portrait;
    }

    public void setPortrait(byte[] portrait) {
        this.portrait = portrait;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBrithday() {
        return brithday;
    }

    public void setBrithday(String brithday) {
        this.brithday = brithday;
    }

    public Integer getRemember() {
        return remember;
    }

    public void setRemember(Integer remember) {
        this.remember = remember;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getAim_style() {
        return aim_style;
    }

    public void setAim_style(Integer aim_style) {
        this.aim_style = aim_style;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public float getAim_weight() {
        return aim_weight;
    }

    public void setAim_weight(float aim_weight) {
        this.aim_weight = aim_weight;
    }

    public float getAim_time() {
        return aim_time;
    }

    public void setAim_time(float aim_time) {
        this.aim_time = aim_time;
    }

    public ArrayList<String> getSpecial_disease_List() {
        return Special_disease_List;
    }

    public void setSpecial_disease_List(ArrayList<String> special_disease_List) {
        Special_disease_List = special_disease_List;
    }
}
