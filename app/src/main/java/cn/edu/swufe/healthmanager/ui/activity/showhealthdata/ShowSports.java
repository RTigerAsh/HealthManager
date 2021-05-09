package cn.edu.swufe.healthmanager.ui.activity.showhealthdata;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;

import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;

import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import cn.edu.swufe.healthmanager.R;
import cn.edu.swufe.healthmanager.ui.adapter.ExpandableListviewAdapter;

public class ShowSports extends AppCompatActivity {
    private ExpandableListView expand_list;
    //Model：定义的数据, "2021年3月12日", "2021年3月13日"
    SimpleDateFormat formatdata = new SimpleDateFormat("yyyy年MM月dd日");//yyyy-MM-dd HH:mm:ss"
    Date date=new Date();
    String strdata=formatdata.format(date);
    private String[] groups = strdata.split("//,");//{"2021年5月8日"};


    //注意，字符数组不要写成{{"A1,A2,A3,A4"}, {"B1,B2,B3,B4，B5"}, {"C1,C2,C3,C4"}}
    //private String[][] childs = {{"跑步 30 370", "游泳 30 268", "跑步 60 740"}, {"跑步 30 370", "跑步 60 740"}, {"游泳 30 268"}};//= {{"跑步 30 370", "游泳 30 268", "跑步 60 740"}}
    private String[][] childs ;

    private TitleBar titleBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showsports);
        
        initView();
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

    private void initView() {
        titleBar=findViewById(R.id.TitleBar_showsport);
        expand_list=findViewById(R.id.expand_list_showsport);
        final SharedPreferences pref = getSharedPreferences("datafrag1", MODE_PRIVATE);
        childs=getShuzu(pref);
        ExpandableListviewAdapter adapter=new ExpandableListviewAdapter(this,groups,childs);
        adapter.setJliang("分钟");
        expand_list.setAdapter(adapter);
        //默认展开全部
        int groupCount = expand_list.getCount();
        for (int i=0; i<groupCount; i++)
        {
            expand_list.expandGroup(i);
        }

    }

    private String[][] getShuzu(SharedPreferences pref) {


        String s=pref.getString("sportlist","");
        //先从分号中分割
        String[] first=s.split(";");

        //分配的二位数组
        String[][] childs = new String[first.length][];
        for(int i=0;i<first.length;i++){
            String[] sSecond=first[i].split("/");
            childs[i]=new String[sSecond.length];
            for(int j=0;j<sSecond.length;j++){
                childs[i][j]=sSecond[j];
            }
        }

        return childs;
    }
}
