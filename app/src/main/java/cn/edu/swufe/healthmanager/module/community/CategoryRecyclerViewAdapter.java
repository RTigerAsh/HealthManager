package cn.edu.swufe.healthmanager.module.community;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import cn.edu.swufe.healthmanager.R;
import cn.edu.swufe.healthmanager.model.entities.Category;

public class CategoryRecyclerViewAdapter extends RecyclerView.Adapter<CategoryRecyclerViewAdapter.MyViewHolder> {
    private  ClickListener clickListener;
    Context context;
    private List<Category> categoryList;

    public CategoryRecyclerViewAdapter(Context context, List<Category> categoryList){
        this.context = context;
        this.categoryList =categoryList;
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView categoryName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryName = itemView.findViewById(R.id.community_category_tv);
            categoryName.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            clickListener.onItemClick(getAdapterPosition(), v);

        }
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.community_category_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        if(categoryList != null && categoryList.size() > position){
            holder.categoryName.setText(categoryList.get(position).getName());

        }

    }

    @Override
    public int getItemCount() {
        return categoryList == null || categoryList.size()==0 ? 5: categoryList.size();
    }

    public  void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public interface ClickListener{
        void onItemClick(int position, View v);
    }


}
