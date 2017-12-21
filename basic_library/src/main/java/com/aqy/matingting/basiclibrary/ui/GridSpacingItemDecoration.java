package com.aqy.matingting.basiclibrary.ui;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by matingting on 2017/12/21.
 */

public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

    private int spanCount;

    private int spacing;

    private boolean includeLeftEdge;

    private boolean includeRightEdge;

    public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeLeftEdge, boolean includeRightEdge) {
        this.spanCount = spanCount;
        this.spacing = spacing;
        this.includeLeftEdge = includeLeftEdge;
        this.includeRightEdge = includeRightEdge;
    }


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        outRect.right = spacing;
        outRect.top = spacing;

        int column = parent.getChildAdapterPosition(view)%spanCount;


        if (includeLeftEdge && column == 0){
            outRect.left = spacing;
        }

        if (!includeRightEdge && column == spanCount-1){
            outRect.right = 0;
        }

        if (parent.getChildAdapterPosition(view) < spanCount){
            outRect.top =0;
        }


    }
}
