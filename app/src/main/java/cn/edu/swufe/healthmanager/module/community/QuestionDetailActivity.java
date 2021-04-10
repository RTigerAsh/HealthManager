package cn.edu.swufe.healthmanager.module.community;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import org.w3c.dom.Comment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.edu.swufe.healthmanager.R;
import cn.edu.swufe.healthmanager.common.Configs;
import cn.edu.swufe.healthmanager.model.ServerResult;
import cn.edu.swufe.healthmanager.model.entities.CommentEntity;
import cn.edu.swufe.healthmanager.module.community.Adapters.QuestionDetailRecyclerViewAdapter;
import cn.edu.swufe.healthmanager.util.TextUtil;
import cn.edu.swufe.healthmanager.util.ToastUtils;

public class QuestionDetailActivity extends AppCompatActivity implements View.OnClickListener {
    private QuestionDetailViewModel mViewModel;
    private int itemSpaceVertical = 16, page = 0;
    private String questionId;
    private CommentEntity oriComment = new CommentEntity();

    private List<CommentEntity> commentEntityList = new ArrayList<>();

    private RecyclerView comments_rv;
    private ImageButton question_detail_back_imb;
    private QuestionDetailRecyclerViewAdapter questionDetailRecyclerViewAdapter;
    private Button btn_send;
    private EditText et_comment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_detail);

        mViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(QuestionDetailViewModel.class);
        questionId = getIntent().getStringExtra("questionId");

        question_detail_back_imb = findViewById(R.id.question_detail_back_imb);
        btn_send = findViewById(R.id.question_detail_send);
        et_comment = findViewById(R.id.question_comment_et);

        initRecyclerView();


        question_detail_back_imb.setOnClickListener(this);
        btn_send.setOnClickListener(this);

        mViewModel.getCommentRlt().observe(this, new Observer<ServerResult>() {
            @Override
            public void onChanged(ServerResult serverResult) {
                if(serverResult == null) return;

                // 1. 显示请求结果
                Toast.makeText(getApplication(), serverResult.getMsg(), Toast.LENGTH_SHORT).show();

                // 2. 处理成功的请求
                if(serverResult.isSuccess()){
                    // 3. 请求新的数据
                    page = 0;
                    mViewModel.getComments(page, Configs.PAGE_SIZE, questionId);
                    // 4. 置空输入框
                    et_comment.setText("");

                }
            }
        });

        mViewModel.getCommentListRlt().observe(this, new Observer<ServerResult<List<CommentEntity>>>() {
            @Override
            public void onChanged(ServerResult<List<CommentEntity>> listServerResult) {
                if(listServerResult == null) return;

                // 1. 显示请求结果
                Toast.makeText(getApplication(), listServerResult.getMsg(), Toast.LENGTH_SHORT).show();

                // 2. 处理成功的请求
                if(listServerResult.isSuccess()){
                    commentEntityList.clear();
                    commentEntityList.add(oriComment);
                    commentEntityList.addAll(listServerResult.getData());
                    questionDetailRecyclerViewAdapter.notifyDataSetChanged();
                }

            }
        });


    }

    private void initRecyclerView() {
        comments_rv = findViewById(R.id.community_question_detail_recycle_view);
        setOriComment();
        questionDetailRecyclerViewAdapter = new QuestionDetailRecyclerViewAdapter(this, commentEntityList);

        // 设置产生的item的布局方向
        comments_rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        comments_rv.setItemAnimator(new DefaultItemAnimator());
        comments_rv.setAdapter(questionDetailRecyclerViewAdapter);

        // 增加item之间的线
        comments_rv.addItemDecoration (new SpaceItemDecoration(itemSpaceVertical, 0));
        mViewModel.getComments(page, Configs.PAGE_SIZE, questionId);
    }

    private void setOriComment() {
        // 解析问题页面的传入数据
        oriComment.setUserName(getIntent().getStringExtra("userName"));
        oriComment.setUserAvatar(getIntent().getStringExtra("userAvatar"));
        oriComment.setContent(getIntent().getStringExtra("questionContent"));
        oriComment.setCreateTime(new Date(getIntent().getLongExtra("questionCreateTime", 0)));
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.question_detail_back_imb:
                finish();
                break;
            case R.id.question_detail_send:
                // TODO: 输入判空
                if(TextUtil.isEmpty(et_comment.getText().toString())){
                    new ToastUtils().showShort(this, "输入不能为空");
                }else{
                    mViewModel.sendComment(et_comment.getText().toString(), questionId);
                }

        }

    }
}
