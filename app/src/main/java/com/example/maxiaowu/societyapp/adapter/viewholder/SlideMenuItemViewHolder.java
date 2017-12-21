package com.example.maxiaowu.societyapp.adapter.viewholder;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.widget.TextViewCompat;
import android.view.View;
import android.widget.TextView;

import com.aqy.matingting.basiclibrary.base.BaseViewHolder;
import com.example.maxiaowu.societyapp.R;
import com.example.maxiaowu.societyapp.adapter.model.DrawerLayoutItem;
import com.example.maxiaowu.societyapp.utils.ViewUtils;

public class SlideMenuItemViewHolder  {
        public TextView  mItemNameTv;

    public SlideMenuItemViewHolder(View itemView) {
        mItemNameTv= (TextView) itemView.findViewById(R.id.tv_item_name);
    }

    public void setData2View(@NonNull DrawerLayoutItem item, Context context) {
        mItemNameTv.setText(item.mItemName);
        Drawable icon=context.getResources().getDrawable(item.mIconId);
        if (icon!=null){
            icon.setBounds(0,0, ViewUtils.getDimension(context,R.dimen.drawer_icon_size),ViewUtils.getDimension(context,R.dimen.drawer_icon_size));
            TextViewCompat.setCompoundDrawablesRelative(mItemNameTv,icon,null,null,null);
        }
    }
}