package cn.edu.swufe.healthmanager.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.litepal.LitePal;

import androidx.appcompat.app.AppCompatActivity;
import cn.edu.swufe.healthmanager.MainActivity;
import cn.edu.swufe.healthmanager.R;
import cn.edu.swufe.healthmanager.db.model.User;
import cn.edu.swufe.healthmanager.util.ActivityCollector;
import cn.edu.swufe.healthmanager.util.BMIView;
import cn.edu.swufe.healthmanager.util.BmiUtil;
import cn.edu.swufe.healthmanager.util.RingView;

import static org.litepal.LitePal.*;


public class HealthReport extends AppCompatActivity implements View.OnClickListener{
    private  Button bt;
    private BMIView view1,view2,view3;
    private User user;
    private RingView ringView;
    private float weight,aim_weight,height,min_weight,max_weight,bmi,bmr;
    private String gender;
    private BmiUtil bmiUtil;
    private TextView textView,tv_tang,tv_dbz,tv_zhifang,tv_bmr,layout_bmi_text,layout_tizhong_text,report_details;
    private  float[] data;
    private LinearLayout layout_bmi,layout_tizhong,layout_risk,layout_health;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);
        setContentView(R.layout.activity_healthreport);

        initData();
        initView();


        //setLeftColor(getResources().getColor(R.color.gray));
        //setTitle("代码设置的标题");

        //圆环数据
        ringView.setData(data);
        ringView.setColors(new int[]{0xa4a6f9,0xffcd9b,0xfb8e89});//碳水化合物，脂肪，蛋白质

        bt = (Button)findViewById(R.id.button_healthreport);
        bt.setOnClickListener(this);
        report_details=findViewById(R.id.report_details);
        report_details.setOnClickListener(this);
    }
    public void initData(){
        user = find(User.class, 1);
        gender=user.getGender();
        height=user.getHeight();
        weight=user.getWeight();
        aim_weight=user.getAim_weight();

        bmi= bmiUtil.getBmi(weight,height/100);
        bmr= bmiUtil.getBMR(weight,height/100,gender,20);
        min_weight=bmiUtil.getMinWeight(height/100,gender);
        max_weight=bmiUtil.getMaxWeight(height/100,gender);

        System.out.println("gender"+gender+"height"+height+"weight"+weight+"aim_weight"+aim_weight+"bmi"+bmi+"bmr"+bmr+"min_weight"+min_weight);


    }
    private void initView(){
        textView=findViewById(R.id.report_text_bmi);
        view1 = findViewById(R.id.report_BMIView1);//体重
        view2= findViewById(R.id.report_BMIView2);//BMI
        view3= findViewById(R.id.report_BMIView3);//目标体重
        ringView =findViewById(R.id.report_RingView);

        tv_tang=findViewById(R.id.report_ts);
        tv_dbz=findViewById(R.id.report_dbz);
        tv_zhifang=findViewById(R.id.report_zf);
        tv_bmr=findViewById(R.id.report_bmr);
        layout_bmi_text=findViewById(R.id.report_bmi_text);
        layout_tizhong_text=findViewById(R.id.report_weight_text);

        layout_bmi=findViewById(R.id.report_bmi);
        layout_tizhong=findViewById(R.id.report_weight);
        layout_risk=findViewById(R.id.report_risk);
        layout_health=findViewById(R.id.report_health);

        layout_bmi.setVisibility(View.GONE);
        layout_tizhong.setVisibility(View.GONE);
        layout_risk.setVisibility(View.GONE);
        showText();

        initBmiview(view1,"我的体重");
        initBmiview(view2,"最新BMI");
        initBmiview(view3,"目标体重");

        // 代码设置各种值
        view1.setMinVal(0);//线的最小值
        view1.setMinNormal(Math.round(min_weight));//正常最小值
        view1.setMaxNormal(Math.round(max_weight));//正常最大值
        view1.setMaxVal(Math.round(max_weight*2));//线的最大值
        view1.setCurrVal(weight);//当前值

        view2.setMinVal(0);//线的最小值
        view2.setMinNormal(Math.round(18.5f));//正常最小值
        view2.setMaxNormal(Math.round(23.9f));//正常最大值
        view2.setMaxVal(50);//线的最大值
        view2.setCurrVal(bmi);//当前值

        view3.setMinVal(0);//线的最小值
        view3.setNormalText("正常体重");
        view3.setMinNormal(Math.round(min_weight));//正常最小值
        view3.setMaxNormal(Math.round(max_weight));//正常最大值
        view3.setMaxVal(Math.round(max_weight*2));//线的最大值
        view3.setCurrVal(aim_weight);//当前值




    }

    private void showText() {
        if (bmi<18.5f){
            layout_bmi.setVisibility(View.VISIBLE);
            layout_tizhong.setVisibility(View.VISIBLE);
            layout_risk.setVisibility(View.VISIBLE);
            layout_health.setVisibility(View.GONE);
            textView.setText("体重过轻！长期体重过轻会导致脱发、厌食症、不孕不育、溃疡、眩晕、月经不调，年老时还会得骨质疏松症，对于思维和推理能力也会有影响。体重过轻既不健康也不会使人美丽。");
            layout_tizhong_text.setText("  体重过轻");
            layout_bmi_text.setText("  BMI"+bmi);
        }else if (18.5f<=bmi && bmi<=23.9f){
            textView.setText("您的身体非常健康，请继续保持！");
        }else if (bmi>23.9f){
            layout_bmi.setVisibility(View.VISIBLE);
            layout_tizhong.setVisibility(View.VISIBLE);
            layout_risk.setVisibility(View.VISIBLE);
            layout_health.setVisibility(View.GONE);
            textView.setText("超重！体重过重不仅影响形体美，而且给生活带来不便，更重要是容易引起多种并发症，加速衰老和死亡。请注意保持身材，健康饮食。");
            layout_tizhong_text.setText("  体重过重");
            layout_bmi_text.setText("  BMI"+bmi);
        }
        //每克碳水化合物有4千卡（大卡）热量,每克蛋白质有4千卡的热量，每克脂肪有9千卡热量
        tv_bmr.setText(Math.round(bmr)+"");
        tv_tang.setText("60%   "+Math.round(bmr*0.6/4)+"g");
        tv_dbz.setText("25%   "+Math.round(bmr*0.25/4)+"g");
        tv_zhifang.setText("15%   "+Math.round(bmr*0.15/9)+"g");

        data=new float[]{bmr*0.6f/4,bmr*0.25f/4,bmr*0.15f/9};
    }

    private void initBmiview(BMIView view, String title) {
        view.setTitle(title);
        view.setNormalText("正常");
        view.setLeftColor(getResources().getColor(R.color.gray5));
        view.setRightColor(getResources().getColor(R.color.red5));
        view.setMidColor(getResources().getColor(R.color.main));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //点击按钮的逻辑
            case R.id.button_healthreport:
                Intent intent1 = new Intent(HealthReport.this, MainActivity.class);
                startActivity(intent1);

                break;
            case R.id.report_details:
                Intent intent2 = new Intent(HealthReport.this, DetailePlan.class);
                startActivity(intent2);

                break;
            default:
                break;
        }
    }


}
