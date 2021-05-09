package cn.edu.swufe.healthmanager.module.community.cmmunityFragments;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import cn.edu.swufe.healthmanager.R;
import cn.edu.swufe.healthmanager.common.Configs;
import cn.edu.swufe.healthmanager.model.LoginUser;
import cn.edu.swufe.healthmanager.model.ServerResult;
import cn.edu.swufe.healthmanager.model.entities.QuestionEntity;
import cn.edu.swufe.healthmanager.module.community.IClickListener;
import cn.edu.swufe.healthmanager.module.community.QuestionAddActivity;
import cn.edu.swufe.healthmanager.module.community.QuestionDetailActivity;
import cn.edu.swufe.healthmanager.module.community.Adapters.QuestionRecyclerViewAdapter;
import cn.edu.swufe.healthmanager.module.community.SpaceItemDecoration;

public class MainFragment extends Fragment implements View.OnClickListener {
    private final String TAG = getClass().getSimpleName();

    private static final int itemSpaceVertical  = 16;

    private MainViewModel mViewModel;
    private View view;
    private Context context;
    private FloatingActionButton add_fab;

    private RecyclerView recyclerView;
    private RefreshLayout refreshLayout;


    // 记录当前用户查看的页数
    private volatile int page = 0;


    private List<QuestionEntity> questionEntityList = new ArrayList<>();


    private QuestionRecyclerViewAdapter questionRecyclerViewAdapter;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.community_main_fragment, container, false);
        context = getActivity();

        // 初始化 floatButton
        add_fab = view.findViewById(R.id.community_add_bt);
        add_fab.setOnClickListener(this);

        // 初始化 refreshLayout
        initRefreshLayout();

        // 获得数据前，使用默认配置
        initRecyclerView();

        return view;
    }

    private void initRefreshLayout() {
        refreshLayout = view.findViewById(R.id.community_refresh_ly);
        refreshLayout.setRefreshHeader(new ClassicsHeader(context));
        refreshLayout.setRefreshFooter(new ClassicsFooter(context));

        refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mViewModel.getQuestionList(++page, Configs.PAGE_SIZE);


            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 0;
                mViewModel.getQuestionList(page, Configs.PAGE_SIZE);
            }
        });
    }


    private void initRecyclerView() {
        recyclerView = view.findViewById(R.id.community_recycle_view);
        questionRecyclerViewAdapter = new QuestionRecyclerViewAdapter(getActivity(), questionEntityList);
        questionRecyclerViewAdapter.setClickListener(new IClickListener() {


            @Override
            public void onItemClick(int position, View v) {
                QuestionEntity currentQuestion = questionEntityList.get(position);

                // 启动新的activity
                Intent intent = new Intent(getActivity(), QuestionDetailActivity.class);

                intent.putExtra("questionId", currentQuestion.getId());
                intent.putExtra("userAvatar", currentQuestion.getUserAvatar());
                intent.putExtra("userName", currentQuestion.getUserName());
                intent.putExtra("questionContent", currentQuestion.getContent());
                intent.putExtra("questionCreateTime", currentQuestion.getCreateTime().getTime());
                startActivity(intent);

            }
        });

        // 设置产生的item的布局方向
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(questionRecyclerViewAdapter);

        // 增加item之间的间隔
        recyclerView.addItemDecoration (new SpaceItemDecoration(itemSpaceVertical, 0));
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);

//        Log.i(TAG,"GetLoginTokenKey: " + LoginUser.getInstance().getUserEntity().getTokenKey());

        mViewModel.getQuestionList(page, Configs.PAGE_SIZE);

        mViewModel.getRequestResult().observe(this, new Observer<ServerResult<List<QuestionEntity>>>() {
            @Override
            public void onChanged(ServerResult<List<QuestionEntity>> listServerResult) {
                if(listServerResult == null){
                    return;
                }

                Log.i(TAG, "isSuccess: " + listServerResult.isSuccess());
                if(!listServerResult.isSuccess()){
                    Toast.makeText(context, listServerResult.getMsg(), Toast.LENGTH_SHORT).show();
                }else{

                    // 上拉刷新时，清空list
                    if(refreshLayout.isRefreshing()){
                        Log.i(TAG, "RefreshData");
                        questionEntityList.clear();

                    }

                    questionEntityList.addAll(listServerResult.getData());

                    Log.i(TAG, "notifyDataChanged： " + questionEntityList.size());
                    questionRecyclerViewAdapter.notifyDataSetChanged();
                }

                if(refreshLayout.isRefreshing()){
                    refreshLayout.finishRefresh();
                }

                if(refreshLayout.isLoading()){
                    refreshLayout.finishLoadMore();
                }


            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, " onResume");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.community_add_bt:

                Intent intent = new Intent(getActivity(), QuestionAddActivity.class);
                startActivity(intent);

        }

    }
}
