package com.example.maxiaowu.societyapp.widgets.ptr;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Created by matingting on 2018/3/22.
 */

public class CommonRecyclerView extends PtrSimpleLayout<RecyclerView> {
    public CommonRecyclerView(@NonNull Context context) {
        this(context,null);
    }

    public CommonRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,-1);
    }

    public CommonRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected RecyclerView initContentView() {

        RecyclerView recyclerView = new RecyclerView(getContext());

        recyclerView.setOverScrollMode(OVER_SCROLL_NEVER);
        recyclerView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        Drawable background = getBackground();
        if (background != null){
            recyclerView.setBackgroundDrawable(background);
        }
        return recyclerView;
    }


    public void setAdapter(RecyclerView.Adapter adapter){
        if (adapter != null){
            mContentView.setAdapter(adapter);
        }
    }

    public void setLayoutManager(RecyclerView.LayoutManager layoutManager){
        if (layoutManager != null){
            mContentView.setLayoutManager(layoutManager);
        }
    }


    @Override
    protected boolean canPullDown() {
        return mContentView != null && mContentView.getTop() >= 0 && ((LinearLayoutManager)mContentView.getLayoutManager()).findFirstCompletelyVisibleItemPosition() == 0 ;
    }

    @Override
    protected boolean canPullUp() {
        return mContentView != null && mContentView.getTop() <= 0 && ((LinearLayoutManager)mContentView.getLayoutManager()).findLastCompletelyVisibleItemPosition() == mContentView.getAdapter().getItemCount()-1;
    }
}
