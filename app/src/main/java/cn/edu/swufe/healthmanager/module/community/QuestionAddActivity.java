package cn.edu.swufe.healthmanager.module.community;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.edu.swufe.healthmanager.R;
import cn.edu.swufe.healthmanager.model.LoginUser;
import cn.edu.swufe.healthmanager.model.ServerResult;
import cn.edu.swufe.healthmanager.model.entities.Category;
import cn.edu.swufe.healthmanager.module.community.Adapters.CategoryRecyclerViewAdapter;
import cn.edu.swufe.healthmanager.util.ToastUtils;

public class QuestionAddActivity extends AppCompatActivity implements View.OnClickListener {

    private final String TAG = getClass().getSimpleName();
    private static final int itemSpaceHorizontal = 16;

    private QuestionAddViewModel mViewModel;
    private  Button back_imb, commit_imb;
    private EditText content_et, label_et;
    private TextView category_tv;

    private CategoryRecyclerViewAdapter categoryRecyclerViewAdapter;
    private RecyclerView category_rv;
    private LinearLayout add_ly;

    private List<Category> categoryList = new ArrayList<>();
    private Category selectedCategory = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_add);
        mViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(QuestionAddViewModel.class);


        back_imb = findViewById(R.id.question_back_imb);
        commit_imb = findViewById(R.id.question_commit_imb);
        category_tv = findViewById(R.id.question_title_tv);
        content_et = findViewById(R.id.question_content_et);
        label_et  = findViewById(R.id.question_label_et);

        add_ly = findViewById(R.id.community_other_ly);
        category_rv = findViewById(R.id.community_category_rv);

        initRecyclerView();


        LayoutTransition transition = new LayoutTransition();

        ObjectAnimator animator_x = ObjectAnimator.ofFloat(null, "scaleY",   1, 0);
        ObjectAnimator animator_y = ObjectAnimator.ofFloat(null, "scaleY",  0, 1);


        transition.setAnimator(LayoutTransition.DISAPPEARING, animator_x);
        transition.setAnimator(LayoutTransition.APPEARING, animator_y);
        add_ly.setLayoutTransition(transition);


        back_imb.setOnClickListener(this);
        commit_imb.setOnClickListener(this);
        category_tv.setOnClickListener(this);


        mViewModel.getCategory();

        mViewModel.getUploadResult().observe(this, new Observer<ServerResult>() {
            @Override
            public void onChanged(ServerResult serverResult) {
                if(serverResult == null){
                    return;
                }

                if(!serverResult.isSuccess()){
                    Toast.makeText(getApplicationContext(), "上传失败", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(), "上传成功", Toast.LENGTH_SHORT).show();

                    // 返回上一个fragment
                    finish();
                    // 刷新fragment
                }
            }
        });


        mViewModel.getRequestCategoryListResult().observe(this, new Observer<ServerResult<List<Category>>>() {
            @Override
            public void onChanged(ServerResult<List<Category>> listServerResult) {
                if(listServerResult == null){
                    return;
                }

                if(!listServerResult.isSuccess()){
                    new ToastUtils().showShort(getApplicationContext(), TAG + ": 获取信息失败");
                }else{
                    categoryList.addAll(listServerResult.getData());

                    // 更新recyclerView
                    if(categoryList.size()>0) {
                        categoryRecyclerViewAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
        
        
    }

    private void initRecyclerView() {
        categoryRecyclerViewAdapter = new CategoryRecyclerViewAdapter(this, categoryList);

        categoryRecyclerViewAdapter.setClickListener(new IClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                selectedCategory = categoryList.get(position);

                category_tv.setText(selectedCategory.getName());
                category_tv.setBackgroundResource(R.drawable.text_circle_corner);
                category_rv.setVisibility(View.GONE);
            }
        });

        // 设置产生的item的布局方向
        category_rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        category_rv.setItemAnimator(new DefaultItemAnimator());
        category_rv.setAdapter(categoryRecyclerViewAdapter);

        // 增加item之间的线
        category_rv.addItemDecoration (new SpaceItemDecoration(0, itemSpaceHorizontal));
    }




    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.question_back_imb:
                // 由于通过Dialog的方式启动，因而也要使用Dialog方法消除
                finish();
                break;
            case R.id.question_commit_imb:
                // TODO: 保存文章
                // 输入：类别（唯一）、内容、标签
                // tokenKey 可通过单例获取
                if(selectedCategory != null){
                    String tokenKey = LoginUser.getInstance().getUserEntity().getTokenKey();
                    String categoryId = selectedCategory.getId();

                    mViewModel.uploadQuestion(categoryId,
                            content_et.getText().toString(), label_et.getText().toString(), tokenKey);
                }else{
                    new ToastUtils().showShort(getApplicationContext(), "必须选择一个类别");
                }

                break;
            case R.id.question_title_tv:
                category_rv.setVisibility(View.VISIBLE);


        }
    }
}
