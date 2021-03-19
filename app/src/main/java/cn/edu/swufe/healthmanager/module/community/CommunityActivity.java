package cn.edu.swufe.healthmanager.module.community;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import cn.edu.swufe.healthmanager.R;
import cn.edu.swufe.healthmanager.module.community.Adapters.CommunityPagerAdapter;
import cn.edu.swufe.healthmanager.module.community.cmmunityFragments.MainFragment;
import cn.edu.swufe.healthmanager.module.community.cmmunityFragments.MineFragment;

public class CommunityActivity extends AppCompatActivity{

    private ViewPager viewPager;

    private TabLayout tabLayout;
    private List<Fragment> fragments;
    private static final String [] tabTitles = {"社区", "我的"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community);

        viewPager = findViewById(R.id.community_vp);
        initViewPager();

        tabLayout = findViewById(R.id.community_tb);
        tabLayout.setupWithViewPager(viewPager);
        Fresco.initialize(this);

    }

    private void initViewPager() {
        // 创建 Fragment list
        fragments = new ArrayList<>();
        fragments.add(new MainFragment());
        fragments.add(new MineFragment());

        // 设置adapter
        viewPager.setAdapter(new CommunityPagerAdapter(getSupportFragmentManager(), fragments, tabTitles));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setCurrentItem(0);
    }

}
