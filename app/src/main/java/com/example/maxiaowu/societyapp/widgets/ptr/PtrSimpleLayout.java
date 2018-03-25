package com.example.maxiaowu.societyapp.widgets.ptr;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by matingting on 2018/3/22.
 */

public abstract class PtrSimpleLayout<V extends View> extends PtrAbstractLayout<V> {
    public PtrSimpleLayout(@NonNull Context context) {
        this(context,null);
    }

    public PtrSimpleLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,-1);
    }

    public PtrSimpleLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setRefreshView(generateTextView());
        setLoadMoreView(generateTextView());
        setContentView(initContentView());
    }

    protected abstract V initContentView();


    private void setContentView(V contentView){
        if (mContentView != null && contentView != null && mContentView != contentView){
            removeView(mContentView);
        }

        mContentView = contentView;

        addView(contentView);
    }


    private void setLoadMoreView(View loadMoreView) {

        if (mLoadMoreView != null && loadMoreView != null && mRefreshView != loadMoreView){
            removeView(mLoadMoreView);
        }

        mLoadMoreView = loadMoreView;
        addView(mLoadMoreView);
        mLoadMoreView.setVisibility(INVISIBLE);
        if (mContentView != null){
            mContentView.bringToFront();
        }


    }

    private void setRefreshView(View header) {

        if (mRefreshView != null && header != null && mRefreshView != header){
            removeView(mRefreshView);
        }

        mRefreshView = header;

        addView(mRefreshView);

        if (mContentView != null){
            mContentView.bringToFront();
        }

    }



    public TextView generateTextView(){
        TextView textView = new TextView(getContext());

        textView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        textView.setPadding(0,20,0,20);
        textView.setGravity(Gravity.CENTER);
        textView.setText("正在加载");
        textView.setTextSize(28);
        textView.setTextColor(Color.BLACK);
        textView.setBackgroundColor(Color.BLUE);
        return textView;
    }
}
