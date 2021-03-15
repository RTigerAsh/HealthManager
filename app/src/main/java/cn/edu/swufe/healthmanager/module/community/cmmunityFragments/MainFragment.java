package cn.edu.swufe.healthmanager.module.community.cmmunityFragments;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.edu.swufe.healthmanager.R;
import cn.edu.swufe.healthmanager.module.community.QuestionRecyclerViewAdapter;

public class MainFragment extends Fragment {

    private MainViewModel mViewModel;
    private View view;

    private RecyclerView recyclerView;
    private QuestionRecyclerViewAdapter questionRecyclerViewAdapter;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.community_main_fragment, container, false);

        initRecyclerView();

        return view;
    }

    private void initRecyclerView() {
        recyclerView = view.findViewById(R.id.community_recycle_view);
        questionRecyclerViewAdapter = new QuestionRecyclerViewAdapter(getActivity());

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
        // TODO: Use the ViewModel
    }

}
