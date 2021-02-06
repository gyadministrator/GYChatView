package com.android.chatlib.view;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @ProjectName: GYChatView
 * @Package: com.android.chatlib.view
 * @ClassName: SpaceItemDecoration
 * @Author: 1984629668@qq.com
 * @CreateDate: 2021/2/6 15:30
 */
public class SpaceItemDecoration extends RecyclerView.ItemDecoration {
    private int mSpace;

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.left = 0;
        outRect.right = 0;
        outRect.bottom = 0;
        if (parent.getChildAdapterPosition(view) != 0) {
            outRect.top = mSpace;
        } else {
            outRect.top = 0;
        }

    }

    public SpaceItemDecoration(int space) {
        this.mSpace = space;
    }
}
