package cn.edu.swufe.healthmanager.ui.activity.showhealthdata;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;

import java.util.Date;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import cn.edu.swufe.healthmanager.R;
import cn.edu.swufe.healthmanager.util.echartview.EchartOptionUtil;
import cn.edu.swufe.healthmanager.util.echartview.EchartView;

public class ShowSleep extends AppCompatActivity {
    private EchartView echartView1,echartView2;
    private TitleBar titleBar;
    private Object[] x2,y2;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showsleep);
        titleBar=findViewById(R.id.TitleBar_showsleep);
        initdata();
        initechart();
        titleBar.setOnTitleBarListener(new OnTitleBarListener() {

            @Override
            public void onLeftClick(View v) {
                //返回上个页面
                finish();

            }

            @Override
            public void onTitleClick(View v) {
                //ToastUtils.show("中间View被点击");
            }

            @Override
            public void onRightClick(View v) {
                //ToastUtils.show("右项View被点击");
            }
        });
    }

    private void initdata() {

//        x1 = new Object[]{
//
//                [[21.5,1],[23.5,2],[24.0,3],[25.5,4],[25.0,5],[23.5,6],[22.0,7]]
//        };
//"3/11","3/12","3/13","3/14","3/15","3/17","3/18"   8,7,8,7,9,7,8
        x2 = new Object[]{
                "4/5"
        };
        y2 = new Object[]{
                8
        };


    }

    private void initechart() {

//        echartView1=findViewById(R.id.linechart_showsleep1);
//        echartView1.setWebViewClient(new WebViewClient(){
//            @Override
//            public void onPageFinished(WebView view, String url) {
//                super.onPageFinished(view, url);
//                //在h5页面加载完毕后再加载数据，防止html的标签还未加载完成，不能正常显示
//                echartView1.refreshEchartsWithOption(EchartOptionUtil.getsleepBarChartOptions(x1, y1,"入睡时间"));
//            }
//        });

        echartView2=findViewById(R.id.linechart_showsleep2);
        echartView2.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                //在h5页面加载完毕后再加载数据，防止html的标签还未加载完成，不能正常显示
                echartView2.refreshEchartsWithOption(EchartOptionUtil.getsleepBarChartOptions(x2, y2,"睡眠时长"));
            }
        });
    }


}
