package cn.edu.swufe.healthmanager.ui.activity.MainAvtivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.annotation.Nullable;
import cn.edu.swufe.healthmanager.R;
import cn.edu.swufe.healthmanager.db.LoginUser;
import cn.edu.swufe.healthmanager.ui.activity.BaseDataFragment.GetBaseData;
import cn.edu.swufe.healthmanager.ui.activity.Login;
import cn.edu.swufe.healthmanager.ui.activity.PersonInfo;
import cn.edu.swufe.healthmanager.ui.activity.showhealthdata.ShowFood;
import cn.edu.swufe.healthmanager.ui.activity.showhealthdata.ShowSleep;
import cn.edu.swufe.healthmanager.ui.activity.showhealthdata.ShowSports;
import cn.edu.swufe.healthmanager.ui.activity.showhealthdata.ShowWeight;
import cn.edu.swufe.healthmanager.util.PhotoUtils;
import cn.edu.swufe.healthmanager.util.ToastUtils;
import cn.edu.swufe.healthmanager.util.echartview.EchartOptionUtil;
import cn.edu.swufe.healthmanager.util.echartview.EchartView;
import cn.edu.swufe.healthmanager.util.stepview.ArcView;
import kotlin.text.StringsKt;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DecimalFormat;

import static android.content.Context.MODE_PRIVATE;

public class Fragment1 extends Fragment implements View.OnClickListener{
    private int zong,mei,dakatag=0;
    private ArcView arcView;
    private EchartView view_bar_food,view_bar_sport,view_line_3,view_line_4;
    private Button btn_daka;
    private TextView tv_food,tv_sport,tv_sleep,tv_weight;
    private MainActivity mainActivity=(MainActivity) getActivity();
    private LinearLayout layout_food_mainfrag1,layout_sport_mainfrag1,layout_sleep_mainfrag1,layout_weight_mainfrag1;
    private LoginUser loginUser = LoginUser.getInstance();
    private PhotoUtils photoUtils = new PhotoUtils();
    private ToastUtils toastUtils;

    @Override
    public void onAttach(Context context) {
        mainActivity = (MainActivity) getActivity();
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_showdata_page1, container, false);
        arcView=view.findViewById(R.id.arcView);

        SharedPreferences.Editor editordetildata = mainActivity.getSharedPreferences("datadetil", MODE_PRIVATE).edit();
        editordetildata.putString("fooddata","暂无数据");
        editordetildata.putString("sportdata","暂无数据");
        editordetildata.putString("sleepdata","暂无数据");
        editordetildata.putString("weightdata","暂无数据");
        editordetildata.commit();

        btn_daka=view.findViewById(R.id.btn_daka_mainfrag1);//打卡0/1
        tv_food=view.findViewById(R.id.tv_food_mainfrag1);//已经摄入1200千卡
        tv_sport=view.findViewById(R.id.tv_sport_mainfrag1);//已经消耗1200千卡
        tv_sleep=view.findViewById(R.id.tv_sleep_mainfrag1);//时长稍短
        tv_weight=view.findViewById(R.id.tv_weight_mainfrag1);//72.5公斤
        setdata();
        layout_food_mainfrag1=view.findViewById(R.id.layout_food_mainfrag1);
        layout_sport_mainfrag1=view.findViewById(R.id.layout_sport_mainfrag1);
        layout_sleep_mainfrag1=view.findViewById(R.id.layout_sleep_mainfrag1);
        layout_weight_mainfrag1=view.findViewById(R.id.layout_weight_mainfrag1);
        initechart(view);

        btn_daka.setOnClickListener(this);
        layout_food_mainfrag1.setOnClickListener(this);
        layout_sport_mainfrag1.setOnClickListener(this);
        layout_sleep_mainfrag1.setOnClickListener(this);
        layout_weight_mainfrag1.setOnClickListener(this);

        toastUtils=new ToastUtils();
        loginUser.setPortrait(photoUtils.bitmap2byte(photoUtils.byte2bitmap((new PhotoUtils()).file2byte(mainActivity ,"default_portrait.jpg"))));
        return view;
    }

    @Override
    public void onResume() {setdata();

        super.onResume();
    }

    private void setdata() {

        SharedPreferences pref = mainActivity.getSharedPreferences("datafrag1", MODE_PRIVATE);
        if(pref==null){
            SharedPreferences.Editor editor = mainActivity.getSharedPreferences("datafrag1", MODE_PRIVATE).edit();
            editor.putInt("zong",0);
            editor.putInt("mei",0);
            editor.putString("foodhot","暂无数据");
            editor.putString("sporthot","暂无数据");
            editor.putString("sleeptime","暂无数据");
            editor.putString("weightnow","暂无数据");
            editor.putInt("initpo",0);
            editor.putInt("initpo2",0);
            editor.putString("foodlist","");
            editor.putString("sportlist","");

            editor.commit();
        }

        zong=pref.getInt("zong",1270);

        if(pref.getString("sporthot","0").equals("0") && pref.getString("foodhot","0").equals("0")){
            System.out.println("main---------两种热量无数据");
            mei=0;
        }else if (pref.getString("sporthot","0").equals("0")){
            mei=Integer.parseInt(pref.getString("foodhot","0"));
            System.out.println("main---------运动热量无数据");
        }else if (pref.getString("foodhot","0").equals("0")){
            mei=0-Integer.parseInt(pref.getString("sporthot","0"));
            System.out.println("main---------食物热量无数据");
        }else{
            mei=Integer.parseInt(pref.getString("foodhot","0"))-Integer.parseInt(pref.getString("sporthot","0"));
        }
        if(mei<0) mei=0;

        arcView.setDairlyHotNum(mei);
        arcView.setTargetHotNum(zong);
        tv_food.setText(pref.getString("foodhot","0"));
        tv_sport.setText(pref.getString("sporthot","0"));
        tv_sleep.setText(pref.getString("sleeptime","暂无"));
        Double sKt=Double.parseDouble(pref.getString("weightnow", "74.5"));
        DecimalFormat df = new DecimalFormat("#.0");
        tv_weight.setText(df.format(sKt));
    }

    private void initechart(View view) {
        view_bar_food=view.findViewById(R.id.barChart_food_mainfrag1);//可以改成横着的双bar
        view_bar_food.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                //在h5页面加载完毕后再加载数据，防止html的标签还未加载完成，不能正常显示
                Object[] x = new Object[]{
                        350
                };
                Object[] y = new Object[]{
                        "早"
                };
                view_bar_food.refreshEchartsWithOption(EchartOptionUtil.getBarChartOptions(x, y));
            }
        });


        view_bar_sport=view.findViewById(R.id.barChart_sport_mainfrag1);
        view_bar_sport.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                //在h5页面加载完毕后再加载数据，防止html的标签还未加载完成，不能正常显示
                Object[] x = new Object[]{
                        350
                };
                Object[] y = new Object[]{
                        ""
                };
                view_bar_sport.refreshEchartsWithOption(EchartOptionUtil.getBarChartOptions(x, y));
            }
        });

        view_line_3=view.findViewById(R.id.lineChart_sleep_mainfrag1);
        view_line_3.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                //在h5页面加载完毕后再加载数据，防止html的标签还未加载完成，不能正常显示
                Object[] x = new Object[]{
                        "", "", "", "","", "",""
                };
                Object[] y = new Object[]{
                        6.1,4.1,5.1,6.8, 3.1, 6.1, 7.1
                };
                view_line_3.refreshEchartsWithOption(EchartOptionUtil.getNoxyBarChartOptions(x, y));
            }
        });

        view_line_4=view.findViewById(R.id.lineChart_weight_mainfrag1);
        view_line_4.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                //在h5页面加载完毕后再加载数据，防止html的标签还未加载完成，不能正常显示
                Object[] x = new Object[]{
                        "", "", "", "","", "",""
                };
                Object[] y = new Object[]{
                        74.5,73.5,73.6,75.8, 74.7, 72.6, 71.7,
                };
                view_line_4.refreshEchartsWithOption(EchartOptionUtil.getNoxyLineChartOptions(x, y,100,50));
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //点击按钮的逻辑
            case R.id.btn_daka_mainfrag1:
                if(dakatag==0){
                    btn_daka.setText("打卡1/1");
                    dakatag=1;
                    toastUtils.showShort(mainActivity, "打卡成功");
                }else{
                    btn_daka.setText("打卡0/1");
                    dakatag=0;
                    toastUtils.showShort(mainActivity, "取消打卡");
                }

                break;
            case R.id.layout_food_mainfrag1:
                if(!tv_food.getText().equals("0")){
                    //跳转展示饮食
                    Intent intent1 = new Intent(mainActivity, ShowFood.class);
                    startActivity(intent1);
                }else {
                    toastUtils.showShort(mainActivity, "暂无数据");
                }

                break;
            case R.id.layout_sport_mainfrag1:
                if(!tv_sport.getText().equals("0")){
                    //跳转展示运动
                    Intent intent2 = new Intent(mainActivity, ShowSports.class);
                    startActivity(intent2);
                }else {
                    toastUtils.showShort(mainActivity, "暂无数据");
                }

                break;
            case R.id.layout_sleep_mainfrag1:
                if(!tv_sleep.getText().equals("暂无")){
                    //跳转展示睡眠
                    Intent intent3 = new Intent(mainActivity, ShowSleep.class);
                    startActivity(intent3);
                }else {
                    toastUtils.showShort(mainActivity, "暂无数据");
                }

                break;
            case R.id.layout_weight_mainfrag1:
                if(!tv_weight.getText().equals("74.5")){
                    //跳转展示体重
                    Intent intent4 = new Intent(mainActivity, ShowWeight.class);
                    startActivity(intent4);
                }else {
                    toastUtils.showShort(mainActivity, "暂无数据");
                }

                break;

            default:
                break;
        }
    }
}
