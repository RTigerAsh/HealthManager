package cn.edu.swufe.healthmanager.ui.activity.BaseDataFragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import cn.edu.swufe.healthmanager.R;
import cn.edu.swufe.healthmanager.util.ToastUtils;

import cn.edu.swufe.healthmanager.util.slidingruleview.SlidingRuleView_week;




public class Fragment4_end extends Fragment implements View.OnClickListener {

    private TextView textView;
    public Button bt;
    private Float aim_time;
    private boolean judge1=true;
    private SlidingRuleView_week srv;
    public static String title[]=new String[]{"无","便秘","甲减","甲亢","糖尿病","贫血","高血压","高血脂","高尿酸"};


    private ToastUtils toastUtils = new ToastUtils();
    private GetBaseData getBaseData = (GetBaseData) getActivity();

    // 使用listview设置多选
    private java.util.List<Map<String, Object>> mList ;//特殊情况
    private ListView listView;

    public ArrayList<String> List=new ArrayList<String>();


    @Override
    public void onAttach(Context context) {
        getBaseData = (GetBaseData) getActivity();
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_getbasedata_4, container, false);


        bt = (Button)view.findViewById(R.id.button_getbasedata_4);
        srv=(SlidingRuleView_week)view.findViewById(R.id.slidView_week_getbasedata_4);
        listView=(ListView)view.findViewById(R.id.listview_getbasedata_4);
        textView=(TextView)view.findViewById(R.id.tv_getbasedata_4);

        setFalse(bt);

        mList = new ArrayList<Map<String, Object>>();
        for(int i=0;i<title.length;i++){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("info", title[i]);
            mList.add(map);
        }
        //List.add("yige");
        //System.out.println("Mlist中的数据"+mList);
        MyAdapter adapter = new MyAdapter(getActivity());
        listView.setAdapter(adapter);

        bt.setOnClickListener(this);

        return view;
    }
    public void setFalse(Button bt){
        bt.setBackgroundColor(Color.parseColor("#D6D6D6"));
        bt.setEnabled(false);

    }
    public void setTrue(Button bt){
        bt.setBackgroundColor(Color.parseColor("#7457ED"));
        bt.setEnabled(true);
    }
    public void changeText(float f){

        textView.setText("平均每周体重变化"+(getBaseData.getAim_weight()/aim_time)+"kg");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //点击按钮的逻辑
            case R.id.button_getbasedata_4:
                if (judge1 ){
                    //将数据传入主activity跳转档案展示页面
                    aim_time=srv.getCurrentNumber();
                    getBaseData.setAim_time(aim_time);
                    getBaseData.setList(List);
                    getBaseData.goHealthInfo();

                }

                break;

            default:
                break;
        }
    }

    public class MyAdapter extends BaseAdapter {


        private LayoutInflater mInflater;

        public MyAdapter(Context context) {
            this.mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return mList.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return 0;
        }

        //原本getView方法中的int position变量是非final的，现在改为final
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {

                holder=new MyAdapter.ViewHolder();

                //可以理解为从vlist获取view  之后把view返回给ListView

                convertView = mInflater.inflate(R.layout.fragment4_item, null);
                holder.viewBtn = (Button)convertView.findViewById(R.id.button_getbasedata4_item);
                convertView.setTag(holder);
            }else {
                holder = (MyAdapter.ViewHolder)convertView.getTag();
            }


            holder.viewBtn.setText((String)mList.get(position).get("info"));
            holder.viewBtn.setTag(position);
            //给Button添加单击事件  添加Button之后ListView将失去焦点  需要的直接把Button的焦点去掉
            holder.viewBtn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    System.out.println("点击事件激活"+position
                    );
                    v.findViewById(R.id.button_getbasedata4_item).setBackgroundColor(Color.parseColor("#7457ED"));
                    List.add(position+"");
                    System.out.println("list"+List);
                    setTrue(bt);

                }
            });

            //holder.viewBtn.setOnClickListener(MyListener(position));

            return convertView;
        }

        public final class ViewHolder {
            public Button viewBtn;
        }
    }


}
