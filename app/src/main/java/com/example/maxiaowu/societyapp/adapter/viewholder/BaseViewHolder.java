package com.example.maxiaowu.societyapp.adapter.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by maxiaowu on 17/3/19.
 */

public abstract class BaseViewHolder<K> extends RecyclerView.ViewHolder{
    public BaseViewHolder(View itemView) {
        super(itemView);
    }

    public abstract void setData2View(K item, Context mContext);

}
