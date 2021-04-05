package cn.edu.swufe.healthmanager.ui.activity;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectChangeListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;

import org.litepal.LitePal;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import cn.edu.swufe.healthmanager.R;
import cn.edu.swufe.healthmanager.db.model.DairyBodyMessage;

import cn.edu.swufe.healthmanager.db.model.User;


public class SetSleepPopupWindow extends Activity implements View.OnClickListener {


    private Button btn_save;
    private TextView tv_cancel,tv_start,tv_stop,tv_zong;
    private LinearLayout layout,layout_start,layout_stop;
    private Double zong;
    private Date start,stop;
    private TimePickerView pvTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setsleep_popwindow);

        btn_save = (Button) this.findViewById(R.id.btn_setsleep_save);
        tv_cancel = (TextView) this.findViewById(R.id.tv_setsleep_cancel);

        layout=(LinearLayout)findViewById(R.id.set_sleep_layout);

        tv_zong=(TextView)findViewById(R.id.tv_setsleep_zong);
        tv_start= (TextView) this.findViewById(R.id.tv_seleep_start);
        tv_stop= (TextView) this.findViewById(R.id.tv_seleep_stop);

        layout_start=(LinearLayout)findViewById(R.id.seleep_start_layout);
        layout_stop=(LinearLayout)findViewById(R.id.seleep_stop_layout);

        start= new Date();
        stop= new Date();

        //添加选择窗口范围监听可以优先获取触点，即不再执行onTouchEvent()函数，点击其他地方时执行onTouchEvent()函数销毁Activity
        layout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Toast.makeText(getApplicationContext(), "提示：点击窗口外部关闭窗口！",
                        Toast.LENGTH_SHORT).show();
            }
        });
        //添加按钮监听
        tv_cancel.setOnClickListener(this);
        btn_save.setOnClickListener(this);

        layout_start.setOnClickListener(this);
        layout_stop.setOnClickListener(this);
    }

    //实现onTouchEvent触屏函数但点击屏幕时销毁本Activity
//    @Override
//    public boolean onTouchEvent(MotionEvent event){
//        //finish();
//        return true;
//    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_setsleep_cancel:
                finish();
                break;
            case R.id.btn_setsleep_save:
                //保存数据
//                DairyBodyMessage message = new DairyBodyMessage();
//                message.setBody_weight(weight);
//                message.setBody_date(new Date().toString());
//                message.save();
//                System.out.println("添加选择的weight数据："+message);
//
//                //多对一连接user
//                User user = LitePal.find(User.class,1);
//                user.getDairybodymessageList().add(message);
//                user.update(1);
                System.out.println("点击保存更新");
                finish();
                break;
            case R.id.seleep_start_layout:
                //点击弹出时间选择器，保存选择的时间

                initTimePicker(tv_start);
                //finish();
                break;
            case R.id.seleep_stop_layout:
                //点击弹出时间选择器，保存选择的时间

                initTimePicker(tv_stop);
                //finish();
                break;

            default:
                break;
        }
        //finish();
    }
    private void initTimePicker(final TextView tv) {//Dialog 模式下，在底部弹出
        //时间选择器当前选择时间，起始选择时间，终止选择时间
        Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();

        //正确设置方式 原因：注意事项有说明
        startDate.set(1990,0,1);
        endDate.set(2022,11,31);
        selectedDate.set(1992,10,17);

        TimePickerView  pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date,View v) {
                //选中事件回调
                if (tv==tv_start){
                    start=date;
                }else {stop=date;}

                tv.setText(getTime(date));
                zong=Double.valueOf(getZongtime(start,stop));
                tv_zong.setText(getZongtime(start,stop));
            }
        })
                .setType(new boolean[]{false, false, false, true, true, false})// 默认显示年月日
                .setCancelText("Cancel")//取消按钮文字
                .setSubmitText("Sure")//确认按钮文字
                .setContentTextSize(18)//滚轮文字大小
                .setTitleSize(20)//标题文字大小
                .setTitleText("Title")//标题文字
                .setOutSideCancelable(false)//点击屏幕，点在控件外部范围时，是否取消显示
                .isCyclic(true)//是否循环滚动
                .setTitleColor(Color.BLACK)//标题文字颜色
//                .setSubmitColor(Color.BLUE)//确定按钮文字颜色
//                .setCancelColor(Color.BLUE)//取消按钮文字颜色
//                .setTitleBgColor(0xFF666666)//标题背景颜色 Night mode
//                .setBgColor(0xFF333333)//滚轮背景颜色 Night mode
                .setDate(selectedDate)// 如果不设置的话，默认是系统时间*/
                .setRangDate(startDate,endDate)//起始终止年月日设定
                .setLabel("年","月","日","时","分","秒")//默认设置为年月日时分秒
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
//                .isDialog(true)//是否显示为对话框样式
                .build();
        pvTime.show();
    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("HH时mm分");//yyyy-MM-dd HH:mm:ss"
        return format.format(date);
    }

    private String getZongtime(Date start,Date stop){
        long time1=start.getTime();
        long time2=stop.getTime();
        Double date=(time2-time1)/(1000*60*60.0);
        java.text.DecimalFormat myformat=new java.text.DecimalFormat("0.0");
        return myformat.format(date);
    }
}