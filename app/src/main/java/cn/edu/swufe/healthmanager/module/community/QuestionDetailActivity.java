package cn.edu.swufe.healthmanager.module.community;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import org.w3c.dom.Comment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.edu.swufe.healthmanager.R;
import cn.edu.swufe.healthmanager.model.entities.CommentEntity;
import cn.edu.swufe.healthmanager.module.community.Adapters.QuestionDetailRecyclerViewAdapter;

public class QuestionDetailActivity extends AppCompatActivity implements View.OnClickListener {
    private ViewModel mViewModel;
    private int itemSpaceVertical = 16;
    private String questionId;

    private List<CommentEntity> commentEntityList = new ArrayList<>();

    private RecyclerView comments_rv;
    private ImageButton question_detail_back_imb;
    private QuestionDetailRecyclerViewAdapter questionDetailRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_detail);

        mViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(QuestionDetailViewModel.class);
        questionId = getIntent().getStringExtra("questionId");

        question_detail_back_imb = findViewById(R.id.question_detail_back_imb);

        initRecyclerView();


        question_detail_back_imb.setOnClickListener(this);


    }

    private void initRecyclerView() {
        comments_rv = findViewById(R.id.community_question_detail_recycle_view);

        commentEntityList.add(getOriComment());

        questionDetailRecyclerViewAdapter = new QuestionDetailRecyclerViewAdapter(this, commentEntityList);

        // 设置产生的item的布局方向
        comments_rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        comments_rv.setItemAnimator(new DefaultItemAnimator());
        comments_rv.setAdapter(questionDetailRecyclerViewAdapter);

        // 增加item之间的线
        comments_rv.addItemDecoration (new SpaceItemDecoration(itemSpaceVertical, 0));
    }

    private CommentEntity getOriComment() {
        // 解析传入对象
        CommentEntity oriComment = new CommentEntity();
        oriComment.setUserName(getIntent().getStringExtra("userName"));
        oriComment.setUserAvatar(getIntent().getStringExtra("userAvatar"));
        oriComment.setContent(getIntent().getStringExtra("questionContent"));
        oriComment.setCreateTime(new Date(getIntent().getLongExtra("questionCreateTime", 0)));
        return oriComment;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.question_detail_back_imb:
                finish();
        }

    }
}
