package com.example.maxiaowu.societyapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.maxiaowu.societyapp.R;
import com.example.maxiaowu.societyapp.adapter.model.DrawerLayoutItem;
import com.example.maxiaowu.societyapp.adapter.viewholder.SlideMenuItemViewHolder;

import java.util.ArrayList;

/**
 * Created by maxiaowu on 17/3/14.
 */

public class DrawerLayoutAdpater extends BaseAdapter {
    private Context mContext;
    private ArrayList<DrawerLayoutItem> mItems;

    public DrawerLayoutAdpater(Context mContext, ArrayList<DrawerLayoutItem> items) {
        this.mContext = mContext;
        this.mItems = items;
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public Object getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SlideMenuItemViewHolder holder;
        if(convertView==null){
            convertView= LayoutInflater.from(mContext).inflate(R.layout.slide_menu_listview_item,parent,false);
            holder=new SlideMenuItemViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder= (SlideMenuItemViewHolder) convertView.getTag();
        }
        holder.setData2View(mItems.get(position),mContext);
        return convertView;
    }


}
