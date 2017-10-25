package com.example.mina.githubrepos.ui.decoration;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Mina on 5/7/2017.
 */

public class RepoDecoration extends RecyclerView.ItemDecoration {
    private int space;

    public RepoDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent, RecyclerView.State state) {
        outRect.left = space;
        outRect.right = space;
        outRect.bottom = space;
        outRect.top = space;

        // Add top margin only for the first item to avoid double space between items
        /*if (parent.getChildLayoutPosition(view) == 3 || parent.getChildLayoutPosition(view) == 6
                || parent.getChildLayoutPosition(view) == 9 ) {
            outRect.top = moreSpace;
        } else {
            //outRect.top = 0;
        }*/
    }
}