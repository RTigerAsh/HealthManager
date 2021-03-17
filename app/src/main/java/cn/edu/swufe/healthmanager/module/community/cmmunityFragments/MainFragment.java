package cn.edu.swufe.healthmanager.module.community.cmmunityFragments;

import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import cn.edu.swufe.healthmanager.R;
import cn.edu.swufe.healthmanager.model.LoginUser;
import cn.edu.swufe.healthmanager.model.ServerResult;
import cn.edu.swufe.healthmanager.model.entities.QuestionEntity;
import cn.edu.swufe.healthmanager.module.community.QuestionRecyclerViewAdapter;

public class MainFragment extends Fragment implements View.OnClickListener {
    private final String TAG = getClass().getSimpleName();

    private MainViewModel mViewModel;
    private View view;
    private Context context;
    private FloatingActionButton add_fab;

    // 记录当前用户查看的页数
    private int page = 0;

    private RecyclerView recyclerView;
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

        add_fab = view.findViewById(R.id.community_add_bt);

        add_fab.setOnClickListener(this);

        // 获得数据前，使用默认配置
        initRecyclerView();

        return view;
    }


    private void initRecyclerView() {
        recyclerView = view.findViewById(R.id.community_recycle_view);
        questionRecyclerViewAdapter = new QuestionRecyclerViewAdapter(getActivity(), questionEntityList);

        // 设置产生的item的布局方向
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(questionRecyclerViewAdapter);

        // 增加item之间的线
        recyclerView.addItemDecoration (new DividerItemDecoration(getActivity (),DividerItemDecoration.VERTICAL));
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        Log.i(TAG,"GetLoginTokenKey: " + LoginUser.getInstance().getUserEntity().getTokenKey());

        String tokenKey = LoginUser.getInstance().getUserEntity().getTokenKey();
        mViewModel.getQuestionList(0, 10, tokenKey);

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
                    // 将问题对象存入列表
                    Log.i(TAG, "RefreshData");
                    questionEntityList.addAll(listServerResult.getData());
                    System.out.println(TAG + questionEntityList.get(0).getUserName());
                    // TODO: 更新RecyclerView
                    Log.i(TAG, "notifyDataChanged");
                    questionRecyclerViewAdapter.notifyDataSetChanged();
                }





            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.community_add_bt:
                DialogFragment dialogFragment = new OtherFragment();
                dialogFragment.show(getFragmentManager(), "dialog");

        }

    }
}
