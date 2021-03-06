package cn.edu.swufe.healthmanager.ui.activity.BaseDataFragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import cn.edu.swufe.healthmanager.R;
import cn.edu.swufe.healthmanager.util.ToastUtils;
import cn.edu.swufe.healthmanager.util.slidingruleview.SlidingRuleView_Kg;
import cn.edu.swufe.healthmanager.util.slidingruleview.SlidingRuleView_cm;

public class Fragment3_aims extends Fragment implements RadioGroup.OnCheckedChangeListener,View.OnClickListener {

    private RadioGroup radioGroup;
    private Button bt;
    private Integer aim_style;
    private Float aim_weight;
    private boolean judge1;
    private SlidingRuleView_Kg srv;

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
        View view = inflater.inflate(R.layout.fragment_getbasedata_3, container, false);

        radioGroup = (RadioGroup)view.findViewById(R.id.rg_getbasedata_3);
        bt = (Button)view.findViewById(R.id.button_getbasedata_3);
        srv=(SlidingRuleView_Kg)view.findViewById(R.id.slidView_kg_getbasedata_3);

        radioGroup.setOnCheckedChangeListener(this);
        bt.setOnClickListener(this);

        return view;
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        //根据不同ID 获取数据
        switch (group.getCheckedRadioButtonId()){
            case  R.id.btn_jz_getbasedata_3:
                aim_style=0;
                judge1=true;
                break;
            case  R.id.btn_bc_getbasedata_3:
                aim_style=1;
                judge1=true;
                break;
            case  R.id.btn_zz_getbasedata_3:
                aim_style=2;
                judge1=true;
                break;
            default:
                aim_style=0;
                judge1=true;
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //点击按钮的逻辑
            case R.id.button_getbasedata_3:
                if (judge1 ){
                    //将数据传入主activity同时切换到下一个fragment
                    getBaseData.setFragment32Fragment4(new GetBaseData.Fragment32Fragment4() {
                        @Override
                        public void switchFragment(CustomViewPager viewPager) {
                            //fragment传递数据
                            aim_weight=srv.getCurrentNumber();
                            getBaseData.setAim_style(aim_style);
                            getBaseData.setAim_weight(aim_weight);
                            viewPager.setCurrentItem(3);
                        }
                    });
                    getBaseData.forSkip34();
                }else {
                    toastUtils.showShort(getActivity(), "数据需要全部选择才能进行下一步");

                }

                break;

            default:
                break;
        }
    }



}
