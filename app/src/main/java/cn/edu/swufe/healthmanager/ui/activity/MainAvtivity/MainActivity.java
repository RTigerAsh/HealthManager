package cn.edu.swufe.healthmanager.ui.activity.MainAvtivity;


import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.litepal.LitePal;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import cn.edu.swufe.healthmanager.db.LoginUser;
import cn.edu.swufe.healthmanager.db.model.User;
import cn.edu.swufe.healthmanager.ui.activity.BaseDataFragment.CustomViewPager;
import cn.edu.swufe.healthmanager.ui.adapter.MyFragAdapter;
import cn.edu.swufe.healthmanager.R;
import cn.edu.swufe.healthmanager.util.ActivityCollector;
import cn.edu.swufe.healthmanager.util.PhotoUtils;
import cn.edu.swufe.healthmanager.util.PopupMenuUtil;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;

    private static final String TAG = "MainActivity";
    CustomViewPager viewPager;
    BottomNavigationView navigation;//底部导航栏对象
    List<Fragment> listFragment;//存储页面对象
    private ImageView ivImg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);
        setContentView(R.layout.activity_main);
        intTestdata();
        initView();//初始化
        ivImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenuUtil.getInstance()._show(MainActivity.this, ivImg);
            }
        });
    }

    public static void intTestdata() {
        User testUser = new User();
        testUser.setName("测试账号");
        testUser.setId(1);
        testUser.setGender("0");
        testUser.setBrithday("1999年6月5日");
        LoginUser.getInstance().login(testUser);

        //User updateUser =LitePal.findFirst(User.class);
        //testUser.setPortrait(PhotoUtils.file2byte());
        testUser.setHeight(170);
        testUser.setWeight(63);
        testUser.setAim_style(0);
        testUser.setAim_time(20);
        testUser.setAim_weight(60);
        testUser.save();
        System.out.println("testUser:    "+testUser);
    }

    private void initView() {
        viewPager = (CustomViewPager) findViewById(R.id.view_pager);
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        ivImg = (ImageView)findViewById(R.id.iv_img);

        //向ViewPager添加各页面
        listFragment = new ArrayList<>();
        listFragment.add(new Fragment1());
        listFragment.add(new Fragment2());
        listFragment.add(new Fragment3());
        listFragment.add(new Fragment4());

        MyFragAdapter myAdapter = new MyFragAdapter(getSupportFragmentManager(), this, listFragment);
        viewPager.setAdapter(myAdapter);
        //导航栏点击事件和ViewPager滑动事件,让两个控件相互关联
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                //当点击到某子项，ViewPager就滑动到对应位置
                switch (item.getItemId()) {
                    case R.id.navigation_mainpage:
                        viewPager.setCurrentItem(0);
                        return true;
                    case R.id.navigation_message:
                        viewPager.setCurrentItem(1);
                        return true;
                    case R.id.navigation_shop:
                        viewPager.setCurrentItem(2);
                        return true;
                    case R.id.navigation_setting:
                        viewPager.setCurrentItem(3);
                        return true;
                    default:
                        break;
                }
                return false;
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //这个方法滑动时会调用多次
                //position:当前所处页面索引,滑动调用的最后一次绝对是滑动停止所在页面
                //positionOffset:表示从位置的页面偏移的[0,1]的值。
                //positionOffsetPixels:以像素为单位的值，表示与位置的偏移
            }

            @Override
            public void onPageSelected(int position) {
                //只在滑动停止时调用，position是滑动停止所在页面位置
//                当滑动到某一位置，导航栏对应位置被按下
                navigation.getMenu().getItem(position).setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
//                  这个方法在滑动时调用三次，分别对应下面三种状态
//                  这个方法对于发现用户何时开始拖动，
//                  何时寻呼机自动调整到当前页面，或何时完全停止/空闲非常有用。
//                  state表示新的滑动状态，有三个值：
//                  SCROLL_STATE_IDLE：开始滑动（空闲状态->滑动），实际值为0
//                  SCROLL_STATE_DRAGGING：正在被拖动，实际值为1
//                  SCROLL_STATE_SETTLING：拖动结束,实际值为2
            }
        });
    }

    //在主页退出时直接退出所有活动
    @Override
    protected void onDestroy(){
        super.onDestroy();
        ActivityCollector.finishAll();
    }

    @Override
    public void onBackPressed() {
// 当popupWindow 正在展示的时候 按下返回键 关闭popupWindow 否则关闭activity
        if (PopupMenuUtil.getInstance()._isShowing()) {
            PopupMenuUtil.getInstance()._rlClickAction();
        } else {
            super.onBackPressed();
        }
    }

    public void showAnimation() {
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View vPopupWindow = inflater.inflate(R.layout.setweight_popwindow, null, false);//引入弹窗布局
        PopupWindow popupWindow = new PopupWindow(vPopupWindow, ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT, true);
        //设置背景透明
        addBackground(popupWindow);

        //设置进出动画
        popupWindow.setAnimationStyle(R.style.AnimBottom);

        //引入依附的布局
        View parentView = LayoutInflater.from(MainActivity.this).inflate(R.layout.setweight_popwindow, null);
        //相对于父控件的位置（例如正中央Gravity.CENTER，下方Gravity.BOTTOM等），可以设置偏移或无偏移
        popupWindow.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);
    }

    private void addBackground(PopupWindow popupWindow) {
        // 设置背景颜色变暗
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.7f;//调节透明度
        getWindow().setAttributes(lp);
        //dismiss时恢复原样
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1f;
                getWindow().setAttributes(lp);
            }
        });
    }
}
