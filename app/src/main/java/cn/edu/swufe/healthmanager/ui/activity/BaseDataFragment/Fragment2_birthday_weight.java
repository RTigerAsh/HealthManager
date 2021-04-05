package cn.edu.swufe.healthmanager.ui.activity.BaseDataFragment;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;


import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import cn.edu.swufe.healthmanager.R;

import cn.edu.swufe.healthmanager.util.ToastUtils;
import cn.edu.swufe.healthmanager.util.slidingruleview.SlidingRuleView_Kg;

public class Fragment2_birthday_weight extends Fragment implements View.OnClickListener, DatePicker.OnDateChangedListener {


    private Button bt;
    private DatePicker dp_birthday;
    private String birthday;
    private boolean judge2=true;
    private Float weight;
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
        View view = inflater.inflate(R.layout.fragment_getbasedata_2, container, false);

        bt = (Button)view.findViewById(R.id.button_getbasedata_2);
        srv=(SlidingRuleView_Kg)view.findViewById(R.id.slidView_kg_getbasedata);

        dp_birthday = (DatePicker)view.findViewById(R.id.dp_dickdate);

        dp_birthday.init(1999, 5, 23,this);
        hideDatePickerHeader(dp_birthday);

        bt.setOnClickListener(this);
        dp_birthday.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //点击按钮的逻辑
            case R.id.button_getbasedata_2:
                if (judge2){
                    //将数据传入主activity同时切换到下一个fragment
                    getBaseData.setFragment22Fragment3(new GetBaseData.Fragment22Fragment3() {
                        @Override
                        public void switchFragment(CustomViewPager viewPager) {
                            //fragment传递数据
                            weight=srv.getCurrentNumber();
                            birthday = String.format("%d年%d月%d日",dp_birthday.getYear(),dp_birthday.getMonth()+1,dp_birthday.getDayOfMonth());
                            getBaseData.setWeight(weight);
                            getBaseData.setBirthday(birthday);
                            viewPager.setCurrentItem(2);
                        }
                    });
                    System.out.println("设置viewPager为2，getBaseData为："+getBaseData);
                    getBaseData.forSkip23();
                }else {
                    toastUtils.showShort(getActivity(), "数据需要全部选择才能进行下一步");

                }

                break;


            case R.id.dp_dickdate:
                judge2=true;
                break;

            default:
                break;
        }
    }

    private void hideDatePickerHeader(DatePicker datePicker) {
        ViewGroup rootView = (ViewGroup) datePicker.getChildAt(0);
        if (rootView == null) {
            return;
        }
        View headerView = rootView .getChildAt(0);
        if (headerView == null) {
            return;
        }
        //6.0+
        int headerId = getActivity().getResources().getIdentifier("date_picker_header", "id", "android");
        if (headerId == headerView.getId()) {
            headerView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

    }
}
