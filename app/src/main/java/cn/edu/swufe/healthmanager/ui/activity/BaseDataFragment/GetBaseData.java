package cn.edu.swufe.healthmanager.ui.activity.BaseDataFragment;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import cn.edu.swufe.healthmanager.R;
import cn.edu.swufe.healthmanager.db.model.User;
import cn.edu.swufe.healthmanager.ui.activity.HealthReport;
import cn.edu.swufe.healthmanager.ui.adapter.BaseDataFragAdapter;
import cn.edu.swufe.healthmanager.util.ToastUtils;

public class GetBaseData extends AppCompatActivity {

    private CustomViewPager viewPager;
    private List<Fragment> fragments ;

    Fragment12Fragment2 fragment12Fragment2;
    Fragment22Fragment3 fragment22Fragment3;
    Fragment32Fragment4 fragment32Fragment4;

    public int judge_data;
    public  Integer gender,aim_style;
    public long id;
    public  float height,weight,aim_weight,aim_time;
    public String birthday;
    public ArrayList<String> List;

    private ToastUtils toastUtils = new ToastUtils();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_basedata);
        judge_data=0;
        id=new User().getId();
        viewPager = (CustomViewPager)findViewById(R.id.viewpager_getbasedata);

        fragments = new ArrayList<>();
        Fragment1_gender_height fragment1 = new Fragment1_gender_height();
        fragments.add(fragment1);

        Fragment2_birthday_weight fragment2 = new Fragment2_birthday_weight();
        fragments.add(fragment2);

        Fragment3_aims fragment3 = new Fragment3_aims();
        fragments.add(fragment3);

        Fragment4_end fragment4End = new Fragment4_end();
        fragments.add(fragment4End);

        BaseDataFragAdapter fragmentAdapter = new BaseDataFragAdapter(getSupportFragmentManager(),this, fragments);
        viewPager.setAdapter(fragmentAdapter);
    }

    public interface Fragment12Fragment2{
        void switchFragment(CustomViewPager viewPager);
    }
    public interface Fragment22Fragment3{
        void switchFragment(CustomViewPager viewPager);
    }
    public interface Fragment32Fragment4{
        void switchFragment(CustomViewPager viewPager);
    }

    public void setFragment12Fragment2(Fragment12Fragment2 fragment12Fragment2) {
        this.fragment12Fragment2 = fragment12Fragment2;
    }

    public void setFragment22Fragment3(Fragment22Fragment3 fragment22Fragment3) {
        this.fragment22Fragment3 = fragment22Fragment3;
    }

    public void setFragment32Fragment4(Fragment32Fragment4 fragment32Fragment4) {
        this.fragment32Fragment4 = fragment32Fragment4;
    }

    public void forSkip(){
        if(fragment12Fragment2 != null){
            System.out.println("从1到2");
            fragment12Fragment2.switchFragment(viewPager);
        }
    }


    public void forSkip23(){
        if(fragment22Fragment3 != null){
            System.out.println("从2到3");
            fragment22Fragment3.switchFragment(viewPager);
        }
    }
    public void forSkip34(){
        if(fragment32Fragment4 != null){
            System.out.println("从3到4");
            fragment32Fragment4.switchFragment(viewPager);
        }
    }

    //完成个人信息档案生成，数据放入数据库，跳转到个人信息健康档案页面
    /*
    * private int judge_data;
    private  Integer gender,aim_style;
    private  float height,weight,aim_weight,aim_time;
    private String birthday;
    private ArrayList<String> List;
    * **/
    public void goHealthInfo(){
        if (judge_data==8){
            User updateUser =LitePal.find(User.class,1);
            updateUser.setGender(this.gender.toString());
            updateUser.setBrithday(this.birthday);
            updateUser.setHeight(this.height);
            updateUser.setWeight(this.weight);
            updateUser.setAim_style(this.aim_style);
            updateUser.setAim_time(this.aim_time);
            updateUser.setAim_weight(this.aim_weight);
            updateUser.setSpecial_disease_List(this.List);
            updateUser.updateAll("id = ? ", ""+1);

            SharedPreferences pref = getSharedPreferences("datafrag1", MODE_PRIVATE);
            if(pref==null){
                SharedPreferences.Editor editor = getSharedPreferences("datafrag1", MODE_PRIVATE).edit();
                editor.putInt("zong",0);
                editor.putInt("mei",0);
                editor.putString("foodhot","暂无数据");
                editor.putString("sporthot","暂无数据");
                editor.putString("sleeptime","暂无数据");
                editor.putString("weightnow","暂无数据");
                editor.putInt("initpo",0);
                editor.putString("foodlist","");
                editor.putString("sportlist","");

                editor.commit();
            }
//            SharedPreferences.Editor editor = getSharedPreferences("datafrag1", MODE_PRIVATE).edit();
//            editor.putInt("zong",0);
//            editor.putInt("mei",0);
//            editor.putString("foodhot","0");
//            editor.putString("sporthot","0");
//            editor.putString("sleeptime","暂无");
//            editor.putString("weightnow",this.weight+"");
//            editor.putInt("initpo",0);
//            editor.putString("foodlist","");
//            editor.putString("sportlist","");
//            editor.commit();

            //跳转到报告展示页面
            Intent intent1 = new Intent(GetBaseData.this, HealthReport.class);
            System.out.println(updateUser.toString());
            startActivity(intent1);
        }else{
            toastUtils.showShort(GetBaseData.this,"数据录入失败，错误代码："+judge_data);
        }
    }


    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        if(this.gender==null){this.judge_data++;}
        this.gender = gender;


    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        if(this.height == 0.0){this.judge_data++;}
        this.height = height;
        System.out.println("主界面运行setHeight===height:"+height);

    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        if(this.weight == 0.0){this.judge_data++;}
        this.weight = weight;

    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        if(this.birthday==null){this.judge_data++;}
        this.birthday = birthday;

    }

    public Integer getAim_style() {
        return aim_style;
    }

    public void setAim_style(Integer aim_style) {
        if(this.aim_style==null){this.judge_data++;}
        this.aim_style = aim_style;

    }

    public float getAim_weight() {
        return aim_weight;
    }

    public void setAim_weight(float aim_weight) {
        if(this.aim_weight == 0.0){this.judge_data++;}
        this.aim_weight = aim_weight;

    }

    public ArrayList<String> getList() {
        return List;
    }

    public void setList(ArrayList<String> list) {
        if(this.List==null){this.judge_data++;}
        this.List = list;

    }

    public float getAim_time() {
        return aim_time;
    }

    public void setAim_time(float aim_time) {
        if(this.aim_time == 0.0){this.judge_data++;}
        this.aim_time = aim_time;

    }
}