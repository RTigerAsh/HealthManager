package cn.edu.swufe.healthmanager.ui.activity;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import cn.edu.swufe.healthmanager.R;
import cn.edu.swufe.healthmanager.db.model.DairySportsMessage;
import cn.edu.swufe.healthmanager.db.model.User;
import cn.edu.swufe.healthmanager.util.ActivityCollector;
import cn.edu.swufe.healthmanager.util.GetFoodDataDialog;
import cn.edu.swufe.healthmanager.util.GetSportDataDialog;

import static cn.edu.swufe.healthmanager.ui.activity.MainAvtivity.MainActivity.intTestdata;


public class SetFoodData extends AppCompatActivity implements View.OnClickListener{
    //用来测试的数据，以后改成数据库获取
    public static String foodName[]=new String[]{"米饭","苹果","鸡蛋","酸奶","豆浆","牛奶","馒头","小，大白菜","白粥","牛肉","鱼"};
    public static String foodHot[]=new String[]{"116","53","139","70","31","65","223","18","30","106","108"};
    private ListView listView,listView_show;

    private List<Map<String, Object>> mList ,food_numlist;//用来存数据的list
    private ImageView foodimage1,reddot;
    private TextView reddottv,food_selectedtv_quxiao,food_selectedtv;
    private LinearLayout layout;
    private Button button;
    private TitleBar mTitleBar;
    private int finalnum;

    private ArrayList<String> Listid=new ArrayList<String>();
    private ArrayList<String> Listnum=new ArrayList<String>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);
        setContentView(R.layout.activity_setfooddata);
        //获取测试数据
        intTestdata();

        listView=findViewById(R.id.listview_setfooddata);
        listView_show=findViewById(R.id.food_selectedlist);

        reddot=findViewById(R.id.food_reddot);
        reddottv=findViewById(R.id.food_reddot_text);

        food_selectedtv_quxiao=findViewById(R.id.food_selectedtv_quxiao);
        food_selectedtv=findViewById(R.id.food_selectedtv);
        layout=findViewById(R.id.show_selectedfood);
        layout.setVisibility(View.GONE);
        foodimage1=findViewById(R.id.food_image_1);
        foodimage1.setVisibility(View.GONE);

        button=findViewById(R.id.button_finish_getfooddata);
        mTitleBar=findViewById(R.id.food_TitleBar);

        food_numlist= new ArrayList<Map<String, Object>>();
        mList = new ArrayList<Map<String, Object>>();
        for(int i=0;i<foodName.length;i++){
            Map<String, Object> map = new HashMap<String, Object>();
            //map.put("sportimage",)
            map.put("foodname", foodName[i]);
            map.put("foodhot", foodHot[i]);
            mList.add(map);
        }
        //List.add("yige");
        //System.out.println("Mlist中的数据"+mList);
        MyAdapter adapter = new MyAdapter(this);
        listView.setAdapter(adapter);
        reinitview();

        //列表有数据之后对彩色图片设置点击事件
        foodimage1.setOnClickListener(this);
        food_selectedtv_quxiao.setOnClickListener(this);

        //对actionbar、按钮设置点击事件 保存至数据库
        button.setOnClickListener(this);
        mTitleBar.setOnTitleBarListener(new OnTitleBarListener() {

            @Override
            public void onLeftClick(View v) {
                //返回上个页面
                finish();

            }

            @Override
            public void onTitleClick(View v) {
                //ToastUtils.show("中间View被点击");
            }

            @Override
            public void onRightClick(View v) {
                //ToastUtils.show("右项View被点击");
            }
        });
    }
    //刷新展示列表
    private void reinitview() {
        MysowlistAdapter mysowlistAdapter=new MysowlistAdapter(this);
        listView_show.setAdapter(mysowlistAdapter);

        //根据列表数据获取总千卡
        if (food_numlist.size()!=0){
            finalnum=0;
            for (int i=0;i<food_numlist.size();i++){
                int t=Integer.parseInt((String)food_numlist.get(i).get("foodnum"));
                int ka=Integer.parseInt((String)food_numlist.get(i).get("foodhot"));
                finalnum=finalnum+(int)((t/100.0)*ka);
                Listid.add(food_numlist.get(i).get("foodpostion")+"");
                Listnum.add(food_numlist.get(i).get("foodnum")+"");
            }
            food_selectedtv.setText("总计 "+finalnum+" 千卡");

        }
        mysowlistAdapter.notifyDataSetChanged();
    }

    //数据选择listview
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
                convertView = mInflater.inflate(R.layout.layout_food_item, null);
                holder.layout = (LinearLayout) convertView.findViewById(R.id.food_click);
                holder.imageView = (ImageView)convertView.findViewById(R.id.image_food);
                holder.textView1 = (TextView)convertView.findViewById(R.id.foodName);
                holder.textView2 = (TextView)convertView.findViewById(R.id.foodHot);
                convertView.setTag(holder);
            }else {
                holder = (MyAdapter.ViewHolder)convertView.getTag();
            }

            //holder.imageView.setImageResource(R.mipmap.add_main_bd);
            holder.textView1.setText((String)mList.get(position).get("foodname"));
            holder.textView2.setText((String)mList.get(position).get("foodhot"));
            holder.layout.setTag(position);
            //添加单击事件
            holder.layout.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    System.out.println("食物数据添加页面点击事件激活"+v.getTag());
                    final String name=(String)mList.get(position).get("foodname");
                    final String hot=(String)mList.get(position).get("foodhot");
                    final int listpostion=position;
                    //弹出dialog用来添加数据，同时通过回调方法获取点击确认之后回传的数据
                    final GetFoodDataDialog Dialog = new GetFoodDataDialog(v.getContext(),name,hot,new GetFoodDataDialog.GetFoodDataDialogListener(){

                        @Override
                        public void refreshUI(String string) {
                            //获取选择的运动时间放入添加列表
                            Map<String, Object> mymap = new HashMap<String, Object>();
                            mymap.put("foodpostion",listpostion);
                            mymap.put("foodhot", hot);
                            mymap.put("foodname", name);
                            if(string.equals("0"))string="300";
                            mymap.put("foodnum", string);
                            food_numlist.add(mymap);
                            System.out.println("回调函数处理得到列表："+food_numlist);
                            judegImage();

                        }
                    });

                    Dialog.show();
                }
            });


            return convertView;
        }

        public final class ViewHolder {
            public LinearLayout layout;
            public ImageView imageView;
            public TextView textView1;
            public TextView textView2;
        }
    }

    private void judegImage() {
        if (food_numlist.size()!=0){
            //点击之后list不为空，重置展示列表，修改图标为彩色
            reinitview();
            foodimage1.setVisibility(View.VISIBLE);
            reddot.setVisibility(View.VISIBLE);
            reddottv.setVisibility(View.VISIBLE);
            reddottv.setText(food_numlist.size()+"");
        }else {
            //点击之后list为空，隐藏展示历史记录列表，修改图表为灰色

            foodimage1.setVisibility(View.INVISIBLE);
            reddot.setVisibility(View.INVISIBLE);
            reddottv.setVisibility(View.INVISIBLE);
            layout.setVisibility(View.GONE);
            reddottv.setText("");
        }
    }
    //    用来展示的listview
    public class MysowlistAdapter extends BaseAdapter {


        private LayoutInflater mInflater2;

        public MysowlistAdapter(Context context) {
            this.mInflater2 = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return food_numlist.size();
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

                holder=new MysowlistAdapter.ViewHolder();

                //可以理解为从vlist获取view  之后把view返回给ListView
                convertView = mInflater2.inflate(R.layout.layout_food_item_selected, null);
                holder.imageView_dele = (ImageView)convertView.findViewById(R.id.image_sport_dele);
                holder.textView1_show = (TextView)convertView.findViewById(R.id.foodName_selected);
                holder.textView2_show = (TextView)convertView.findViewById(R.id.foodHot_selected);
                convertView.setTag(holder);
            }else {
                holder = (MysowlistAdapter.ViewHolder)convertView.getTag();
            }

            holder.textView1_show.setText((String)food_numlist.get(position).get("foodname"));
            holder.textView2_show.setText((String)food_numlist.get(position).get("foodnum")+"g  "+(String)food_numlist.get(position).get("foodhot")+"千卡");
            holder.imageView_dele.setTag(position);

            //添加单击事件
            holder.imageView_dele.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //System.out.println("运动展示页面点击事件激活"+v.getTag());
                    if (food_numlist.size()!=0){
                        food_numlist.remove(position);
                        judegImage();
                    }else {
                        judegImage();
                    }

                }
            });
            return convertView;
        }

        public final class ViewHolder {
            public ImageView imageView_dele;
            public TextView textView1_show;
            public TextView textView2_show;
        }
    }

    //点击响应
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //点击按钮的逻辑
            case R.id.food_selectedtv_quxiao:
                //点击取消隐藏展示用list view
                layout.setVisibility(View.GONE);
                break;
            case R.id.food_image_1:
                //点击图片展示
                layout.setVisibility(View.VISIBLE);
                break;

            case R.id.button_finish_getfooddata:
                //点击按钮返回主页同时数据保存到数据库
                reinitview();

                SharedPreferences pref = getSharedPreferences("datafrag1", MODE_PRIVATE);
                if(pref.getString("foodhot","0").equals("暂无数据")){
                    SharedPreferences.Editor editor = getSharedPreferences("datafrag1", MODE_PRIVATE).edit();
                    int num=Integer.parseInt(pref.getString("foodhot","0"))+finalnum;
                    editor.putString("foodhot",""+num);

                    // 获取的食物列表进行格式化 private String[][] childs = {{"馒头 500 1850", "豆浆 100 50", "鸡蛋 100 80"}};
                    String splist="";
                    for (int j = 0; j<food_numlist.size(); j++){
                        int hot=Integer.parseInt((String) food_numlist.get(j).get("foodhot"))*Integer.parseInt((String) food_numlist.get(j).get("foodnum"))/100;
                        String list= (String) food_numlist.get(j).get("foodname")+" "+(String) food_numlist.get(j).get("foodnum")+" "+hot+"/";
                        splist=splist+list;
                    }

                    editor.putString("foodlist",""+splist);
                    System.out.println("摄入食物列表放入SP"+splist);

                    editor.commit();
                }else {
                    SharedPreferences.Editor editor = getSharedPreferences("datafrag1", MODE_PRIVATE).edit();
                    int num=Integer.parseInt(pref.getString("foodhot","0"))+finalnum;
                    editor.putString("foodhot",""+num);

                    // 获取的食物列表进行格式化 private String[][] childs = {{"馒头 500 1850", "豆浆 100 50", "鸡蛋 100 80"}};
                    String splist=pref.getString("foodlist","");
                    for (int j = 0; j<food_numlist.size(); j++){
                        int hot=Integer.parseInt((String) food_numlist.get(j).get("foodhot"))*Integer.parseInt((String) food_numlist.get(j).get("foodnum"))/100;
                        String list= (String) food_numlist.get(j).get("foodname")+" "+(String) food_numlist.get(j).get("foodnum")+" "+hot+"/";
                        splist=splist+list;
                    }

                    editor.putString("foodlist",""+splist);
                    System.out.println("摄入食物列表放入SP"+splist);

                    editor.commit();

                }


                //遍历获取数据
                if (Listid.size()!=0){

//                    DairySportsMessage message = new DairySportsMessage();
//                    message.setSports_id(Listid);
//                    message.setSports_time(Listtime);
//                    message.setSports_energy(finalnum/1.0);
//                    message.setSports_date(new Date().toString());
//                    message.save();
//                    System.out.println("添加选择的运动数据："+message);
//                    System.out.println("添加选择的运动id："+Listid);
//                    //多对一连接user
//                    User user = LitePal.find(User.class,1);
//                    user.getDairysportsmessageList().add(message);
//                    user.update(1);
//                    System.out.println("user更新："+user);
                    }

                finish();
                break;
            default:
                break;
        }



    }
}

