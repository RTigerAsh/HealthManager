package cn.edu.swufe.healthmanager.ui.activity;


import android.content.Context;

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
import cn.edu.swufe.healthmanager.util.GetSportDataDialog;

import static cn.edu.swufe.healthmanager.ui.activity.MainAvtivity.MainActivity.intTestdata;

public class SetSportsData extends AppCompatActivity implements View.OnClickListener{
    //用来测试的数据，以后改成数据库获取
    public static String sportName[]=new String[]{"快跑","慢跑","散步","爬楼梯","跳绳","游泳","自行车","舞蹈，健美操","瑜伽","篮球","足球"};
    public static String sportHot[]=new String[]{"740","536","127","526","565","536","565","487","195","536","585"};
    private ListView listView,listView_show;

    private List<Map<String, Object>> mList ,sport_timelist;//用来存数据的list
    private ImageView sportimage1,reddot;
    private TextView reddottv,sport_selectedtv_quxiao,sport_selectedtv;
    private LinearLayout layout;
    private Button button;
    private TitleBar mTitleBar;
    private int finalnum;

    private ArrayList<String> Listid=new ArrayList<String>();
    private ArrayList<String> Listtime=new ArrayList<String>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);
        setContentView(R.layout.activity_setsportsdata);
        //获取测试数据
        intTestdata();

        listView=findViewById(R.id.listview_setsportsdata);
        listView_show=findViewById(R.id.sport_selectedlist);

        reddot=findViewById(R.id.sport_reddot);
        reddottv=findViewById(R.id.sport_reddot_text);

        sport_selectedtv_quxiao=findViewById(R.id.sport_selectedtv_quxiao);
        sport_selectedtv=findViewById(R.id.sport_selectedtv);
        layout=findViewById(R.id.show_selectedsport);
        layout.setVisibility(View.GONE);
        sportimage1=findViewById(R.id.sport_image_1);
        sportimage1.setVisibility(View.GONE);

        button=findViewById(R.id.button_finish_getsportdata);
        mTitleBar=findViewById(R.id.sport_TitleBar);

        sport_timelist= new ArrayList<Map<String, Object>>();
        mList = new ArrayList<Map<String, Object>>();
        for(int i=0;i<sportName.length;i++){
            Map<String, Object> map = new HashMap<String, Object>();
            //map.put("sportimage",)
            map.put("sportname", sportName[i]);
            map.put("sporthot", sportHot[i]);
            mList.add(map);
        }
        //List.add("yige");
        //System.out.println("Mlist中的数据"+mList);
        MyAdapter adapter = new MyAdapter(this);
        listView.setAdapter(adapter);
        reinitview();

        //列表有数据之后对彩色图片设置点击事件
        sportimage1.setOnClickListener(this);
        sport_selectedtv_quxiao.setOnClickListener(this);

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

    private void reinitview() {
        MysowlistAdapter mysowlistAdapter=new MysowlistAdapter(this);
        listView_show.setAdapter(mysowlistAdapter);

        //根据列表数据获取总千卡
        if (sport_timelist.size()!=0){
            finalnum=0;
            for (int i=0;i<sport_timelist.size();i++){
                int t=Integer.parseInt((String)sport_timelist.get(i).get("sporttime"));
                int ka=Integer.parseInt((String)sport_timelist.get(i).get("sporthot"));
                finalnum=finalnum+(int)((t/60.0)*ka);
                Listid.add(sport_timelist.get(i).get("sportpostion")+"");
                Listtime.add(sport_timelist.get(i).get("sporttime")+"");
            }
            sport_selectedtv.setText("总计 "+finalnum+" 千卡");

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
                convertView = mInflater.inflate(R.layout.layout_sport_item, null);
                holder.layout = (LinearLayout) convertView.findViewById(R.id.sport_click);
                holder.imageView = (ImageView)convertView.findViewById(R.id.image_sport);
                holder.textView1 = (TextView)convertView.findViewById(R.id.sportName);
                holder.textView2 = (TextView)convertView.findViewById(R.id.sportHot);
                convertView.setTag(holder);
            }else {
                holder = (MyAdapter.ViewHolder)convertView.getTag();
            }

            //holder.imageView.setImageResource(R.mipmap.add_main_bd);
            holder.textView1.setText((String)mList.get(position).get("sportname"));
            holder.textView2.setText((String)mList.get(position).get("sporthot"));
            holder.layout.setTag(position);
            //添加单击事件
            holder.layout.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    System.out.println("运动数据添加页面点击事件激活"+v.getTag());
                    final String name=(String)mList.get(position).get("sportname");
                    final String hot=(String)mList.get(position).get("sporthot");
                    final int listpostion=position;
                    //弹出dialog用来添加数据，同时通过回调方法获取点击确认之后回传的数据
                    final GetSportDataDialog Dialog = new GetSportDataDialog(v.getContext(),name,hot,new GetSportDataDialog.GetSportDataDialogListener(){

                        @Override
                        public void refreshUI(String string) {
                            //获取选择的运动时间放入添加列表
                            Map<String, Object> mymap = new HashMap<String, Object>();
                            mymap.put("sportpostion",listpostion);
                            mymap.put("sporthot", hot);
                            mymap.put("sportname", name);
                            mymap.put("sporttime", string);
                            sport_timelist.add(mymap);
                            System.out.println("回调函数处理得到列表："+sport_timelist);
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
        if (sport_timelist.size()!=0){
            //点击之后list不为空，重置展示列表，修改图标为彩色
            reinitview();
            sportimage1.setVisibility(View.VISIBLE);
            reddot.setVisibility(View.VISIBLE);
            reddottv.setVisibility(View.VISIBLE);
            reddottv.setText(sport_timelist.size()+"");
        }else {
            //点击之后list为空，隐藏展示历史记录列表，修改图表为灰色

            sportimage1.setVisibility(View.INVISIBLE);
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
            return sport_timelist.size();
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
                convertView = mInflater2.inflate(R.layout.layout_sport_item_selected, null);
                holder.imageView_dele = (ImageView)convertView.findViewById(R.id.image_sport_dele);
                holder.textView1_show = (TextView)convertView.findViewById(R.id.sportName_selected);
                holder.textView2_show = (TextView)convertView.findViewById(R.id.sportHot_selected);
                convertView.setTag(holder);
            }else {
                holder = (MysowlistAdapter.ViewHolder)convertView.getTag();
            }

            holder.textView1_show.setText((String)sport_timelist.get(position).get("sportname"));
            holder.textView2_show.setText((String)sport_timelist.get(position).get("sporttime")+"分钟  "+(String)sport_timelist.get(position).get("sporthot")+"千卡");
            holder.imageView_dele.setTag(position);

            //添加单击事件
            holder.imageView_dele.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //System.out.println("运动展示页面点击事件激活"+v.getTag());
                    if (sport_timelist.size()!=0){
                        sport_timelist.remove(position);
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
            case R.id.sport_selectedtv_quxiao:
                //点击取消隐藏展示用list view
                layout.setVisibility(View.GONE);
                break;
            case R.id.sport_image_1:
                //点击图片展示
                layout.setVisibility(View.VISIBLE);
                break;

            case R.id.button_finish_getsportdata:
                //点击按钮返回主页同时数据保存到数据库

                //遍历获取数据
                if (Listid.size()!=0){

                    DairySportsMessage message = new DairySportsMessage();
                    message.setSports_id(Listid);
                    message.setSports_time(Listtime);
                    message.setSports_energy(finalnum/1.0);
                    message.setSports_date(new Date().toString());
                    message.save();
                    System.out.println("添加选择的运动数据："+message);
                    System.out.println("添加选择的运动id："+Listid);
                    //多对一连接user
                    User user = LitePal.find(User.class,1);
                    user.getDairysportsmessageList().add(message);
                    user.update(1);
                    System.out.println("user更新："+user);
                    }

                finish();
                break;
            default:
                break;
        }



    }
}

