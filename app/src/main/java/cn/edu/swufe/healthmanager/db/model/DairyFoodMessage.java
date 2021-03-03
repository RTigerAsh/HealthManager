package cn.edu.swufe.healthmanager.db.model;

import org.litepal.crud.LitePalSupport;

public class DairyFoodMessage extends LitePalSupport{

    //每日饮食信息
    private long DairyFoodMessage_id;
    private String food_date;//每次饮食记录时间
    private Integer food_id;//每次饮食内容 通过饮食ID检索
    private Double food_energy;//每次饮食摄入能量

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

    public long getDairyFoodMessage_id() {
        return DairyFoodMessage_id;
    }

    public void setDairyFoodMessage_id(long dairyFoodMessage_id) {
        DairyFoodMessage_id = dairyFoodMessage_id;
    }

    public String getFood_date() {
        return food_date;
    }

    public void setFood_date(String food_date) {
        this.food_date = food_date;
    }

    public Integer getFood_id() {
        return food_id;
    }

    public void setFood_id(Integer food_id) {
        this.food_id = food_id;
    }

    public Double getFood_energy() {
        return food_energy;
    }

    public void setFood_energy(Double food_energy) {
        this.food_energy = food_energy;
    }
}
