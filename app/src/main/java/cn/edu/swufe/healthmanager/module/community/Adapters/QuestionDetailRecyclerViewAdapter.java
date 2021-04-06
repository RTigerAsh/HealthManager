package cn.edu.swufe.healthmanager.module.community.Adapters;

import android.content.Context;
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
import cn.edu.swufe.healthmanager.model.entities.CommentEntity;
import cn.edu.swufe.healthmanager.util.DateUtil;
import cn.edu.swufe.healthmanager.util.UrlUtil;


public class QuestionDetailRecyclerViewAdapter extends RecyclerView.Adapter<QuestionDetailRecyclerViewAdapter.MyHolder> {
    private List<CommentEntity> commentEntityList;
    private Context context;

    public QuestionDetailRecyclerViewAdapter(Context context, List<CommentEntity> commentEntityList){
        this.commentEntityList  = commentEntityList;
        this.context = context;

    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.question_detail_item, parent, false);

        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        if(commentEntityList != null && commentEntityList.size() > position){
            CommentEntity commentEntity  = commentEntityList.get(position);

            holder.userNameTv.setText(commentEntity.getUserName());
            holder.userAvatarSv.setImageURI(UrlUtil.getImage(commentEntity.getUserAvatar()));
            holder.contentTv.setText(commentEntity.getContent());
            holder.updateTimeTv.setText(DateUtil.calDateDifference(commentEntity.getCreateTime()));
        }


    }

    @Override
    public int getItemCount() {
        return commentEntityList == null || commentEntityList.size()==0 ? Configs.DEFAULT_SIZE : commentEntityList.size();
    }


    class MyHolder extends RecyclerView.ViewHolder {
        private SimpleDraweeView userAvatarSv;
        private TextView userNameTv, updateTimeTv, contentTv;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            userAvatarSv = itemView.findViewById(R.id.question_detail_user_avatar_imv);
            userNameTv = itemView.findViewById(R.id.question_detail_user_name_tv);
            updateTimeTv = itemView.findViewById(R.id.question_detail_user_update_time_tv);
            contentTv = itemView.findViewById(R.id.question_detail_content_tv);

        }
    }
}
