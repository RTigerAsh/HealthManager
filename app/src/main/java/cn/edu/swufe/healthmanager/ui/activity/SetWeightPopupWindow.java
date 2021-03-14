package cn.edu.swufe.healthmanager.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.litepal.LitePal;
import java.util.Date;
import cn.edu.swufe.healthmanager.R;
import cn.edu.swufe.healthmanager.db.model.DairyBodyMessage;

import cn.edu.swufe.healthmanager.db.model.User;
import cn.edu.swufe.healthmanager.util.slidingruleview.SlidingRuleView_Kg;

public class SetWeightPopupWindow extends Activity implements View.OnClickListener {


    private Button btn_save;
    private TextView btn_cancel;
    private SlidingRuleView_Kg RuleView;
   // private View mMenuView;
    private LinearLayout layout;

    private Double weight;

//    public SetWeightPopupWindow(Activity context) {
//        super(context);
//        LayoutInflater inflater = (LayoutInflater) context
//                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        mMenuView = inflater.inflate(R.layout.setweight_popwindow, null);
//
//        btn_save = (Button) mMenuView.findViewById(R.id.btn_setweight_save);
//        RuleView = (SlidingRuleView_Kg) mMenuView.findViewById(R.id.ruliview_setweight);
//        btn_cancel = (TextView) mMenuView.findViewById(R.id.tv_setweight_cancel);
//        //取消按钮
//        btn_cancel.setOnClickListener(new View.OnClickListener() {
//
//            public void onClick(View v) {
//                //销毁弹出框
//                dismiss();
//            }
//        });
//        //设置按钮监听
//        btn_save.setOnClickListener(new View.OnClickListener() {
//
//            public void onClick(View v) {
//                //保存数据
//                weight=RuleView.getCurrentNumber()/1.0;
//                DairyBodyMessage message = new DairyBodyMessage();
//                message.setBody_weight(weight);
//                message.setBody_date(new Date().toString());
//                message.save();
//                System.out.println("添加选择的运动数据："+message);
//
//                //多对一连接user
//                User user = LitePal.find(User.class,1);
//                user.getDairybodymessageList().add(message);
//                user.update(1);
//                System.out.println("user更新："+user);
//                dismiss();
//            }
//        });
//
//        //设置SelectPicPopupWindow的View
//        this.setContentView(mMenuView);
//        //设置SelectPicPopupWindow弹出窗体的宽
//        this.setWidth(LayoutParams.FILL_PARENT);
//        //设置SelectPicPopupWindow弹出窗体的高
//        this.setHeight(LayoutParams.WRAP_CONTENT);
//        //设置SelectPicPopupWindow弹出窗体可点击
//        this.setFocusable(true);
//        //设置SelectPicPopupWindow弹出窗体动画效果
//        this.setAnimationStyle(R.style.AnimBottom);
//        //实例化一个ColorDrawable颜色为半透明
//        ColorDrawable dw = new ColorDrawable(0xb0000000);
//        //设置SelectPicPopupWindow弹出窗体的背景
//        this.setBackgroundDrawable(dw);
//        //mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
//        mMenuView.setOnTouchListener(new OnTouchListener() {
//
//            public boolean onTouch(View v, MotionEvent event) {
//
//                int height = mMenuView.findViewById(R.id.setweight_layout).getTop();
//                int y=(int) event.getY();
//                if(event.getAction()==MotionEvent.ACTION_UP){
//                    if(y<height){
//                        dismiss();
//                    }
//                }
//                return true;
//            }
//        });
//
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setweight_popwindow);
        btn_save = (Button) this.findViewById(R.id.btn_setweight_save);
        RuleView = (SlidingRuleView_Kg) this.findViewById(R.id.ruliview_setweight);
        btn_cancel = (TextView) this.findViewById(R.id.tv_setweight_cancel);
        layout=(LinearLayout)findViewById(R.id.setweight_layout);

        //添加选择窗口范围监听可以优先获取触点，即不再执行onTouchEvent()函数，点击其他地方时执行onTouchEvent()函数销毁Activity
        layout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Toast.makeText(getApplicationContext(), "提示：点击窗口外部关闭窗口！",
                        Toast.LENGTH_SHORT).show();
            }
        });
        //添加按钮监听
        btn_cancel.setOnClickListener(this);
        btn_save.setOnClickListener(this);
    }

    //实现onTouchEvent触屏函数但点击屏幕时销毁本Activity
    @Override
    public boolean onTouchEvent(MotionEvent event){
        finish();
        return true;
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_setweight_cancel:
                finish();
                break;
            case R.id.btn_setweight_save:
                //保存数据
                weight=RuleView.getCurrentNumber()/1.0;
                DairyBodyMessage message = new DairyBodyMessage();
                message.setBody_weight(weight);
                message.setBody_date(new Date().toString());
                message.save();
                System.out.println("添加选择的weight数据："+message);

                //多对一连接user
                User user = LitePal.find(User.class,1);
                user.getDairybodymessageList().add(message);
                user.update(1);
                System.out.println("user更新："+user);
                finish();
                break;
            default:
                break;
        }
        finish();
    }



}