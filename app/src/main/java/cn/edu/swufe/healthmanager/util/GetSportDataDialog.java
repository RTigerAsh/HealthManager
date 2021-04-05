package cn.edu.swufe.healthmanager.util;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import cn.edu.swufe.healthmanager.R;

public class GetSportDataDialog extends Dialog implements View.OnClickListener {

    TextView quxiao,queren,mainname,mainhot,tvtime,tvhot,num1,num2,num3,num4,num5,num6,num7,num8,num9,num0,numx;

    public String sporttime,sporthot,mainname_str,mainhot_str;

    public interface GetSportDataDialogListener {
        /**
         * 回调函数，用于在Dialog的监听事件触发后向avtivity返回数据
         */
        public void refreshUI(String string);
    }

    private GetSportDataDialogListener listener;
    //构造方法
    public GetSportDataDialog (Context context,String NAME,String HOT,GetSportDataDialogListener listener) {

        super(context, R.style.mdialog);
        //通过LayoutInflater获取布局
        View view = LayoutInflater.from(getContext()).
                inflate(R.layout.dialog_getsportdata_layout, null);


        //设置显示的视图
        setContentView(view);
        this.setMainname_str(NAME);
        this.setMainhot_str(HOT);
        this.listener=listener;
        sporttime="0";
        sporthot="0";
        initview();
    }

    private void initview() {
        quxiao=(TextView)findViewById(R.id.dialog_quxiao);
        queren=(TextView)findViewById(R.id.dialog_queren);
        quxiao.setOnClickListener(this);
        queren.setOnClickListener(this);

        mainname=(TextView)findViewById(R.id.dialog_sportname_static);
        mainhot=(TextView)findViewById(R.id.dialog_sportHot_static);

        mainname.setText(this.mainname_str);
        mainhot.setText(this.mainhot_str);

        tvtime=(TextView)findViewById(R.id.dialog_sporttime);
        tvhot=(TextView)findViewById(R.id.dialog_sporthot_mavean);



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
                sporttime=sporttime+"1";

                judgetime();
                tvtime.setText(sporttime);
                break;

            case R.id.num_2:
                sporttime=sporttime+"2";
                judgetime();
                tvtime.setText(sporttime);
                break;

            case R.id.num_3:
                sporttime=sporttime+"3";
                judgetime();
                tvtime.setText(sporttime);
                break;

            case R.id.num_4:
                sporttime=sporttime+"4";
                judgetime();
                tvtime.setText(sporttime);
                break;

            case R.id.num_5:
                sporttime=sporttime+"5";
                judgetime();
                tvtime.setText(sporttime);
                break;

            case R.id.num_6:
                sporttime=sporttime+"6";
                judgetime();
                tvtime.setText(sporttime);
                break;

            case R.id.num_7:
                sporttime=sporttime+"7";
                judgetime();
                tvtime.setText(sporttime);
                break;

            case R.id.num_8:
                sporttime=sporttime+"8";
                judgetime();
                tvtime.setText(sporttime);
                break;

            case R.id.num_9:
                sporttime=sporttime+"9";
                judgetime();
                tvtime.setText(sporttime);
                break;

            case R.id.num_0:
                sporttime=sporttime+"0";
                judgetime();
                tvtime.setText(sporttime);
                break;

            case R.id.num_x:
                if (sporttime.length() - 1>0){
                    sporttime= sporttime.substring(0,sporttime.length() - 1);
                }else {
                    sporttime="0";
                }

                judgetime();
                tvtime.setText(sporttime);
                break;
            case R.id.dialog_quxiao:
                dismiss();
                break;
            case R.id.dialog_queren:
                dismiss();
                listener.refreshUI(sporttime);
                break;
            default:
                break;
        }
    }

    private void judgetime() {
        int sporttime_int= Integer.parseInt(sporttime);
        int a = Integer.parseInt(this.mainhot_str);


        if (sporttime_int>999){
            sporttime=999+"";
            sporthot=(int)((999/60.0)*a)+"";
            tvhot.setText(sporthot+" 千卡");
        }else {
            sporttime=sporttime_int+"";
            sporthot=(int)((sporttime_int/60.0)*a)+"";

            tvhot.setText(sporthot+" 千卡");

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