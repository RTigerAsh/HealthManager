package cn.edu.swufe.healthmanager.ui.activity.showhealthdata;

import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;

import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import cn.edu.swufe.healthmanager.R;
import cn.edu.swufe.healthmanager.ui.adapter.ExpandableListviewAdapter;

public class ShowSports extends AppCompatActivity {
    private ExpandableListView expand_list;
    //Model：定义的数据, "2021年3月12日", "2021年3月13日"
    private String[] groups = {"2021年4月5日"};

    //注意，字符数组不要写成{{"A1,A2,A3,A4"}, {"B1,B2,B3,B4，B5"}, {"C1,C2,C3,C4"}}
    //private String[][] childs = {{"跑步 30 370", "游泳 30 268", "跑步 60 740"}, {"跑步 30 370", "跑步 60 740"}, {"游泳 30 268"}};
    private String[][] childs = {{"跑步 30 370", "游泳 30 268", "跑步 60 740"}};

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
}
