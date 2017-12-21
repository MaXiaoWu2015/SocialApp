package com.example.maxiaowu.societyapp.adapter;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aqy.matingting.basiclibrary.utils.CollectionUtils;
import com.example.maxiaowu.societyapp.R;
import com.example.maxiaowu.societyapp.entity.RecommendSongListEntity;

import java.util.ArrayList;

/**
 * Created by matingting on 2017/12/13.
 */

public class RecommendSongListAdapter extends RecommendBaseAdapter<RecommendSongListEntity,RecommendSongListAdapter.RecommendSongListHolder> {


    public RecommendSongListAdapter(Context mContext,ArrayList<RecommendSongListEntity> list) {
        super(mContext,list);
    }

    @Override
    protected RecommendSongListHolder onCreateCustomViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(getResLayoutId(viewType),parent,false);
        RecommendSongListHolder holder = new RecommendSongListHolder(view);
        return holder;
    }

    @Override
    public void setData2View(RecommendSongListEntity item, RecommendSongListHolder holder) {
        holder.sd_recommend_list_single_item_poster.setImageURI(item.getPic());
        holder.tv_recommend_list_single_item_count.setText(item.getListenum());
        holder.tv_recommend_list_single_item_desc.setText(item.getTag());
    }


    //RecommendBaseItemHolder如果是有泛型的内部类,那么这样引用RecommendBaseItemHolder
    // 会出现‘type parameter given on a raw type’错误,但是如果不是内部类的化就没问题,可能跟泛型擦除有关
    class RecommendSongListHolder extends RecommendBaseAdapter.RecommendBaseItemHolder{
        public RecommendSongListHolder(View itemView) {
            super(itemView);
            tv_recommend_list_single_item_count.setVisibility(View.VISIBLE);
        }

    }
}
