package cn.edu.swufe.healthmanager.db.model;

import org.litepal.crud.LitePalSupport;

import java.util.ArrayList;

public class DairySportsMessage extends LitePalSupport{

    //每日运动信息
    private long dairysports_id;
    private String sports_date;//每次运动记录时间
    private ArrayList<String> sports_id;//每次运动种类 通过运动ID检索
    private ArrayList<String> sports_time;//每次运动总时间
    private Double sports_energy;//每次运动消耗能量

    //外键
    private long id;

    //多对一连接User表
    private User user;

    //get&set方法
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getDairysports_id() {
        return dairysports_id;
    }

    public void setDairysports_id(long dairysports_id) {
        this.dairysports_id = dairysports_id;
    }

    public ArrayList<String> getSports_id() {
        return sports_id;
    }

    public void setSports_id(ArrayList<String> sports_id) {
        this.sports_id = sports_id;
    }

    public void setSports_time(ArrayList<String> sports_time) {
        this.sports_time = sports_time;
    }

    public String getSports_date() {
        return sports_date;
    }

    public void setSports_date(String sports_date) {
        this.sports_date = sports_date;
    }

    public Double getSports_energy() {
        return sports_energy;
    }

    public void setSports_energy(Double sports_energy) {
        this.sports_energy = sports_energy;
    }
}
