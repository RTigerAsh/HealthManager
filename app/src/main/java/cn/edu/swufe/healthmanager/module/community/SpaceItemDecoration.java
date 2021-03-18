package cn.edu.swufe.healthmanager.module.community;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SpaceItemDecoration extends RecyclerView.ItemDecoration {
    private int itemSpaceVertical;
    private int itemSpaceHorizontal;

    public SpaceItemDecoration(int itemSpaceVertical, int  itemSpaceHorizontal ){
        this.itemSpaceVertical = itemSpaceVertical;
        this.itemSpaceHorizontal = itemSpaceHorizontal;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            outRect.set(itemSpaceHorizontal, itemSpaceVertical, itemSpaceHorizontal, itemSpaceVertical);
    }
}
