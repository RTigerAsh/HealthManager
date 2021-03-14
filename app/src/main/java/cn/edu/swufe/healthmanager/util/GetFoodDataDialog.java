package cn.edu.swufe.healthmanager.util;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import cn.edu.swufe.healthmanager.R;

public class GetFoodDataDialog extends Dialog implements View.OnClickListener {

    TextView quxiao,queren,mainname,mainhot,tvnum,tvhot,num1,num2,num3,num4,num5,num6,num7,num8,num9,num0,numx;

    public String foodnum,foodhot,mainname_str,mainhot_str;

    public interface GetFoodDataDialogListener {
        /**
         * 回调函数，用于在Dialog的监听事件触发后向avtivity返回数据
         */
        public void refreshUI(String string);
    }

    private GetFoodDataDialogListener listener;
    //构造方法
    public GetFoodDataDialog(Context context, String NAME, String HOT, GetFoodDataDialogListener listener) {

        super(context, R.style.mdialog);
        //通过LayoutInflater获取布局
        View view = LayoutInflater.from(getContext()).
                inflate(R.layout.dialog_getfooddata_layout, null);


        //设置显示的视图
        setContentView(view);
        this.setMainname_str(NAME);
        this.setMainhot_str(HOT);
        this.listener=listener;
        foodnum="0";
        foodhot="0";
        initview();
    }

    private void initview() {
        quxiao=(TextView)findViewById(R.id.dialog_quxiao_food);
        queren=(TextView)findViewById(R.id.dialog_queren_food);
        quxiao.setOnClickListener(this);
        queren.setOnClickListener(this);

        mainname=(TextView)findViewById(R.id.dialog_foodname_static);
        mainhot=(TextView)findViewById(R.id.dialog_foodHot_static);

        mainname.setText(this.mainname_str);
        mainhot.setText(this.mainhot_str);

        tvnum=(TextView)findViewById(R.id.dialog_foodnum);
        tvhot=(TextView)findViewById(R.id.dialog_foodhot_mavean);



        num1=(TextView)findViewById(R.id.num_1);
        num2=(TextView)findViewById(R.id.num_2);
        num3=(TextView)findViewById(R.id.num_3);
        num4=(TextView)findViewById(R.id.num_4);
        num5=(TextView)findViewById(R.id.num_5);
        num6=(TextView)findViewById(R.id.num_6);
        num7=(TextView)findViewById(R.id.num_7);
        num8=(TextView)findViewById(R.id.num_8);
        num9=(TextView)findViewById(R.id.num_9);
        num0=(TextView)findViewById(R.id.num_0);
        numx=(TextView)findViewById(R.id.num_x);

        num1.setOnClickListener(this);
        num2.setOnClickListener(this);
        num3.setOnClickListener(this);
        num4.setOnClickListener(this);
        num5.setOnClickListener(this);
        num6.setOnClickListener(this);
        num7.setOnClickListener(this);
        num8.setOnClickListener(this);
        num9.setOnClickListener(this);
        num0.setOnClickListener(this);
        numx.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //点击按钮的逻辑
            case R.id.num_1:
                foodnum=foodnum+"1";

                judgetime();
                tvnum.setText(foodnum);
                break;

            case R.id.num_2:
                foodnum=foodnum+"2";
                judgetime();
                tvnum.setText(foodnum);
                break;

            case R.id.num_3:
                foodnum=foodnum+"3";
                judgetime();
                tvnum.setText(foodnum);
                break;

            case R.id.num_4:
                foodnum=foodnum+"4";
                judgetime();
                tvnum.setText(foodnum);
                break;

            case R.id.num_5:
                foodnum=foodnum+"5";
                judgetime();
                tvnum.setText(foodnum);
                break;

            case R.id.num_6:
                foodnum=foodnum+"6";
                judgetime();
                tvnum.setText(foodnum);
                break;

            case R.id.num_7:
                foodnum=foodnum+"7";
                judgetime();
                tvnum.setText(foodnum);
                break;

            case R.id.num_8:
                foodnum=foodnum+"8";
                judgetime();
                tvnum.setText(foodnum);
                break;

            case R.id.num_9:
                foodnum=foodnum+"9";
                judgetime();
                tvnum.setText(foodnum);
                break;

            case R.id.num_0:
                foodnum=foodnum+"0";
                judgetime();
                tvnum.setText(foodnum);
                break;

            case R.id.num_x:
                if (foodnum.length() - 1>0){
                    foodnum= foodnum.substring(0,foodnum.length() - 1);
                }else {
                    foodnum="0";
                }

                judgetime();
                tvnum.setText(foodnum);
                break;
            case R.id.dialog_quxiao_food:
                dismiss();
                break;
            case R.id.dialog_queren_food:
                dismiss();
                listener.refreshUI(foodnum);
                break;
            default:
                break;
        }
    }

    private void judgetime() {
        int foodnum_int= Integer.parseInt(foodnum);
        int a = Integer.parseInt(this.mainhot_str);


        if (foodnum_int>999){
            foodnum=999+"";
            foodhot=(int)((999/60.0)*a)+"";
            tvhot.setText(foodhot+" 千卡");
        }else {
            foodnum=foodnum_int+"";
            foodhot=(int)((foodnum_int/60.0)*a)+"";

            tvhot.setText(foodhot+" 千卡");

        }
    }

    public String getMainname_str() {
        return mainname_str;
    }

    public void setMainname_str(String mainname_str) {
        this.mainname_str = mainname_str;
    }

    public String getMainhot_str() {
        return mainhot_str;
    }

    public void setMainhot_str(String mainhot_str) {
        this.mainhot_str = mainhot_str;
    }
}