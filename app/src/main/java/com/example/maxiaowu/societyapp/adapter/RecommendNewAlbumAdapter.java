package com.example.maxiaowu.societyapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.maxiaowu.societyapp.R;
import com.example.maxiaowu.societyapp.entity.RecommendNewAlbumEntity;

import java.util.List;

/**
 * Created by matingting on 2017/12/19.
 */

public class RecommendNewAlbumAdapter extends RecommendBaseAdapter<RecommendNewAlbumEntity,RecommendNewAlbumAdapter.RecommendNewAlbumHolder> {


    public RecommendNewAlbumAdapter(Context mContext, List<RecommendNewAlbumEntity> data) {
        super(mContext, data);
    }

    @Override
    public void setData2View(RecommendNewAlbumEntity item, RecommendNewAlbumHolder holder) {
        holder.sd_recommend_list_single_item_poster.setImageURI(item.getPic());
        holder.tv_recommend_list_single_item_desc.setText(item.getTitle());
        holder.tv_recommend_list_single_item_category.setText(item.getAuthor());
    }

    @Override
    protected RecommendNewAlbumHolder onCreateCustomViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(mContext).inflate(getResLayoutId(viewType),parent,false);
        RecommendNewAlbumHolder holder = new RecommendNewAlbumHolder(rootView);
        return holder;
    }

    class RecommendNewAlbumHolder extends RecommendBaseAdapter.RecommendBaseItemHolder{

        public RecommendNewAlbumHolder(View itemView) {
            super(itemView);
        }
    }
}
