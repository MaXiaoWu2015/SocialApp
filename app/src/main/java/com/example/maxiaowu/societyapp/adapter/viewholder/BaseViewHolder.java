package com.example.maxiaowu.societyapp.adapter.viewholder;

import android.content.Context;
import android.view.View;

/**
 * Created by maxiaowu on 17/3/19.
 */

public abstract class BaseViewHolder<K>{
    public BaseViewHolder(View itemView) {
    }

    abstract void setData2View(K item, Context mContext);

}
