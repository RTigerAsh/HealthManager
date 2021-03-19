package cn.edu.swufe.healthmanager.module.community.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import cn.edu.swufe.healthmanager.R;
import cn.edu.swufe.healthmanager.common.Configs;
import cn.edu.swufe.healthmanager.model.entities.QuestionEntity;
import cn.edu.swufe.healthmanager.module.community.IClickListener;
import cn.edu.swufe.healthmanager.util.DateUtil;
import cn.edu.swufe.healthmanager.util.UrlUtil;

public class QuestionRecyclerViewAdapter extends RecyclerView.Adapter<QuestionRecyclerViewAdapter.MyHolder> {
    private IClickListener clickListener;

    Context context;
    private List<QuestionEntity> questionEntities;

    public QuestionRecyclerViewAdapter(Context context, List<QuestionEntity> questionEntities){
        this.context = context;
        this.questionEntities = questionEntities;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 获得指定布局
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.community_question_item, parent, false);
        // 构建自己的Holder
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        if(questionEntities != null && questionEntities.size() > position){
            QuestionEntity questionEntity = questionEntities.get(position);
            holder.tv_userName.setText(questionEntity.getUserName());
            holder.tv_content.setText(questionEntity.getContent());
            holder.tv_tag.setText(questionEntity.getLabels());
            holder.tv_updateTime.setText(DateUtil.toFormString(questionEntity.getUpdateTime()));

            Log.i("IMAGE", UrlUtil.getImage(questionEntity.getUserAvatar()));
            holder.iv_userAvatar.setImageURI(UrlUtil.getImage(questionEntity.getUserAvatar()));
            // 数字需要转成文本，否则会调用寻找id的setText方法
            holder.tv_viewCount.setText(String.valueOf(questionEntity.getViewCount()));
        }
    }

    public void setClickListener(IClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public int getItemCount() {
        return questionEntities == null || questionEntities.size()==0 ? Configs.DEFAULT_SIZE : questionEntities.size();
    }

    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private SimpleDraweeView iv_userAvatar;
        private TextView tv_userName, tv_updateTime, tv_content, tv_tag, tv_viewCount;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            iv_userAvatar = itemView.findViewById(R.id.community_user_avatar_imv);

            tv_userName = itemView.findViewById(R.id.community_user_name_tv);
            tv_updateTime = itemView.findViewById(R.id.community_user_update_time_tv);
            tv_content = itemView.findViewById(R.id.community_content_tv);
            tv_tag = itemView.findViewById(R.id.community_tag_tv);
            tv_viewCount = itemView.findViewById(R.id.community_view_count_tv);

            tv_content.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {
            clickListener.onItemClick(getAdapterPosition(), v);
        }
    }
}
