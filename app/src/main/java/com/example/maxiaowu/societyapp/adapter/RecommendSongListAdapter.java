package com.example.maxiaowu.societyapp.adapter;

import android.content.Context;
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

public class RecommendSongListAdapter extends RecommendBaseAdapter<RecommendSongListAdapter.RecommendSongListHolder> {

    private ArrayList<RecommendSongListEntity> songListEntities;

    public RecommendSongListAdapter(Context mContext,ArrayList<RecommendSongListEntity> list) {
        super(mContext);
        this.songListEntities = list;
    }

    @Override
    public RecommendSongListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recommend_list_single_item,parent);
        RecommendSongListHolder holder = new RecommendSongListHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecommendSongListHolder holder, int position) {

    }




    @Override
    public int getItemCount() {
        return CollectionUtils.size(songListEntities);
    }

    //FIXME:继承有泛型的基类？？？
    class RecommendSongListHolder extends RecommendBaseAdapter.RecommendBaseItemHolder<RecommendSongListEntity> {
        public RecommendSongListHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void setData2View(Object item, Context mContext) {

        }


        @Override
       public void setData2View(RecommendSongListEntity item, Context mContext) {

       }
    }
}
