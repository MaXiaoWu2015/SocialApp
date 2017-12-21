package com.aqy.matingting.basiclibrary.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by maxiaowu on 17/3/19.
 */

public abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder{
    public BaseViewHolder(View itemView) {
        super(itemView);
    }

}
