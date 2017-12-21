package com.aqy.matingting.basiclibrary.base;

import android.content.Context;
import android.support.annotation.IntRange;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by matingting on 2017/12/18.
 */

public abstract class BaseAdapter<K,T extends BaseViewHolder> extends RecyclerView.Adapter<T> {

    protected Context mContext;

    protected List<K> mDatas;

    public BaseAdapter(Context mContext,@Nullable List<K> data) {
        this.mContext = mContext;
        this.mDatas = data == null?new ArrayList<K>():data;
    }


    public abstract int getResLayoutId(int type);


    @Override
    public T onCreateViewHolder(ViewGroup parent, int viewType) {

        T baseViewHolder = null;
        switch (viewType){
            default:
                baseViewHolder = onCreateCustomViewHolder(parent,viewType);
        }


        return baseViewHolder;
    }

    protected T onCreateCustomViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(T holder, int position) {
        int viewType = getItemViewType(position);
        switch (viewType){
            default:
                setData2View(getItem(position-getHeaderLayoutCount()),holder);
        }
    }

    @Nullable
    private K getItem(@IntRange(from = 0) int position) {
        if (position < mDatas.size()){
            return mDatas.get(position);
        }
        return null;
    }


    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    private int getHeaderLayoutCount() {
        return 0;
    }

    public abstract void setData2View(K item,T holder);
}
