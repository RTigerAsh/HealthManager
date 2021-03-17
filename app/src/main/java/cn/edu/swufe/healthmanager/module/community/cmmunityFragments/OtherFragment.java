package cn.edu.swufe.healthmanager.module.community.cmmunityFragments;

import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

import cn.edu.swufe.healthmanager.R;
import cn.edu.swufe.healthmanager.model.ServerResult;
import cn.edu.swufe.healthmanager.module.community.CategoryRecyclerViewAdapter;
import cn.edu.swufe.healthmanager.util.ToastUtils;

public class OtherFragment extends DialogFragment implements View.OnClickListener {
    private final String TAG = this.getTag();

    private OtherViewModel mViewModel;
    private ImageButton back_imb, commit_imb;
    private EditText content_et, label_et;
    private TextView category_tv;

    private CategoryRecyclerViewAdapter categoryRecyclerViewAdapter;
    private RecyclerView category_rv;
    private List<String> categoryList = new ArrayList<>();

    public static OtherFragment newInstance() {
        return new OtherFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.community_other_fragment, container, false);
        back_imb = view.findViewById(R.id.question_back_imb);
        commit_imb = view.findViewById(R.id.question_commit_imb);
        category_tv = view.findViewById(R.id.question_title_tv);
        content_et = view.findViewById(R.id.question_content_et);
        label_et  = view.findViewById(R.id.question_label_et);

        category_rv = view.findViewById(R.id.community_category_rv);
        initRecyclerView();



        back_imb.setOnClickListener(this);
        commit_imb.setOnClickListener(this);
        category_tv.setOnClickListener(this);


        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(OtherViewModel.class);

        mViewModel.getUploadResult().observe(this, new Observer<ServerResult>() {
            @Override
            public void onChanged(ServerResult serverResult) {
                if(serverResult == null){
                    return;
                }

                if(!serverResult.isSuccess()){
                    Toast.makeText(getActivity(), "上传失败", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getActivity(), "上传成功", Toast.LENGTH_SHORT).show();
                    // 返回上一个fragment
                    // 刷新fragment
                }
            }
        });

    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {


        setStyle(STYLE_NORMAL, R.style.Theme_AppCompat_DayNight_DialogWhenLarge);
        return super.onCreateDialog(savedInstanceState);
    }

    private void initRecyclerView() {
        categoryRecyclerViewAdapter = new CategoryRecyclerViewAdapter(getActivity(), categoryList);
        categoryRecyclerViewAdapter.setClickListener(new CategoryRecyclerViewAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                category_tv.setText("运动");
                category_rv.setVisibility(View.GONE);
            }
        });

        // 设置产生的item的布局方向
        category_rv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        category_rv.setItemAnimator(new DefaultItemAnimator());
        category_rv.setAdapter(categoryRecyclerViewAdapter);

        // 增加item之间的线
        category_rv.addItemDecoration (new DividerItemDecoration(getActivity (),DividerItemDecoration.VERTICAL));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.question_back_imb:
                // 由于通过Dialog的方式启动，因而也要使用Dialog方法消除
                getDialog().dismiss();
                break;
            case R.id.question_commit_imb:
                // TODO: 保存文章
                // 输入：类别（唯一）、内容、标签
                // tokenKey 可通过单例获取
                String tokenKey = "397e91710f508fac683d14cfdba902bc";
                String categoryId = "126743189888434176";

                mViewModel.uploadQuestion(categoryId,
                        content_et.getText().toString(), label_et.getText().toString(), tokenKey);
                break;
            case R.id.question_title_tv:
                // TODO：打开一个下拉菜单
                category_rv.setVisibility(View.VISIBLE);


        }
    }

}
