package cn.edu.swufe.healthmanager.ui.activity.BaseDataFragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;


import java.sql.SQLOutput;

import androidx.viewpager.widget.ViewPager;

import cn.edu.swufe.healthmanager.util.ToastUtils;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import cn.edu.swufe.healthmanager.R;
import cn.edu.swufe.healthmanager.util.slidingruleview.SlidingRuleView_cm;

public class Fragment1_gender_height extends Fragment implements RadioGroup.OnCheckedChangeListener,View.OnClickListener{
    private RadioGroup radioGroup;
    private Button bt;
    private Integer gender;
    private boolean judge1;
    private Float height;
    private SlidingRuleView_cm srv;
    private ToastUtils toastUtils = new ToastUtils();

    private GetBaseData getBaseData = (GetBaseData) getActivity();

    @Override
    public void onAttach(Context context) {
        getBaseData = (GetBaseData) getActivity();

        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_getbasedata_1, container, false);
        radioGroup = (RadioGroup)view.findViewById(R.id.rg_getbasedata_1);
        bt = (Button)view.findViewById(R.id.button_getbasedata_1);
        srv=(SlidingRuleView_cm)view.findViewById(R.id.slidView_cm_getbasedata);

        radioGroup.setOnCheckedChangeListener(this);
        srv.setOnClickListener(this);
        bt.setOnClickListener(this);



        return view;
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        //根据不同ID 获取数据
        switch (group.getCheckedRadioButtonId()){
            case  R.id.btn_m_getbasedata_1:
                gender=0;
                judge1=true;
                break;
            case  R.id.btn_f_getbasedata_1:
                gender=1;
                judge1=true;
                break;

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //点击按钮的逻辑
            case R.id.button_getbasedata_1:
                if (judge1 ){

                    //将数据传入主activity同时切换到下一个fragment
                    getBaseData.setFragment12Fragment2(new GetBaseData.Fragment12Fragment2() {
                        @Override
                        public void switchFragment(CustomViewPager viewPager) {
                            //fragment传递数据
                            height=srv.getCurrentNumber();
                            getBaseData.setHeight(height);
                            getBaseData.setGender(gender);
                            viewPager.setCurrentItem(1);
                        }
                    });
                    getBaseData.forSkip();
                }else {

                    System.out.println("单选judge1:"+judge1+"获取的活动数据："+height);
                    toastUtils.showShort(getActivity(), "数据需要全部选择才能进行下一步");

                }

                break;

            default:
                break;
        }
    }



}
