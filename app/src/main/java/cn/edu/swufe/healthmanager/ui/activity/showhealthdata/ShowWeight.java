package cn.edu.swufe.healthmanager.ui.activity.showhealthdata;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import cn.edu.swufe.healthmanager.R;
import cn.edu.swufe.healthmanager.util.echartview.EchartOptionUtil;
import cn.edu.swufe.healthmanager.util.echartview.EchartView;

public class ShowWeight extends AppCompatActivity {
    private EchartView echartView_weight;
    private TextView tv_startweight,tv_startweightdate,tv_newweight,tv_newweightdate,tv_aimweight,tv_aimweightdate;
    private  Object[] x,y;
    private String startweight,startweightdate,newweight,newweightdate,aimweight,aimweightdate;
    private TitleBar titleBar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showweight);
        titleBar=findViewById(R.id.TitleBar_showweight);
        initdata();
        initechart();
        initview();
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

    private void initview() {
        //tv_startweight,tv_startweightdate,tv_newweight,tv_newweightdate,tv_aimweight,tv_aimweightdate;
        tv_startweight=findViewById(R.id.tv_startweight);
        tv_startweightdate=findViewById(R.id.tv_start_jz);
        tv_newweight=findViewById(R.id.tv_newweight);
        tv_newweightdate=findViewById(R.id.tv_new_jz);
        tv_aimweight=findViewById(R.id.tv_aimweight);
        tv_aimweightdate=findViewById(R.id.tv_aim_jz);

        tv_startweight.setText(startweight);
        tv_startweightdate.setText(startweightdate);

        tv_newweight.setText(newweight);
        tv_newweightdate.setText(newweightdate);

        tv_aimweight.setText(aimweight);
        tv_aimweightdate.setText(aimweightdate);
    }

    private void initdata() {
        //在这里获取数据,"3/12","3/13","3/14","3/15","3/17","3/18" ,74.4,74.3,74.5,73.2,73.1,73.3
        x = new Object[]{
                "3/22"
        };
        y = new Object[]{
                74.5
        };
        newweight=y[y.length-1]+"";
        newweightdate=x[x.length-1]+"";
        //从数据库获取最初的体重和记录时间
        startweight="74.5";
        startweightdate="2021年3月22日";

        aimweight="65";
        aimweightdate="2021年7月05日";

        //System.out.println("获取数据newweight:  "+newweight+"newweightdate: "+newweightdate);
    }

    private void initechart() {
        echartView_weight=findViewById(R.id.linechart_showweight);

        echartView_weight.setWebViewClient(new WebViewClient(){


            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                //在h5页面加载完毕后再加载数据，防止html的标签还未加载完成，不能正常显示
                System.out.println("view加载完毕 放入数据view"+view+url);

                echartView_weight.refreshEchartsWithOption(EchartOptionUtil.getweightLineChartOptions(x, y,EchartOptionUtil.getMaxnum(y)+2,EchartOptionUtil.getMinnum(y)-2));
                //System.out.println(EchartOptionUtil.getweightLineChartOptions(x, y,100,50));
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                System.out.println("error ==="+error);
            }
        });

    }


}
