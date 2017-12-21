package com.example.maxiaowu.societyapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aqy.matingting.basiclibrary.base.BaseViewHolder;
import com.example.maxiaowu.societyapp.entity.RecommendRadioEntity;

import java.util.List;

/**
 * Created by matingting on 2017/12/19.
 */

public class RecommendRadioAdapter extends RecommendBaseAdapter<RecommendRadioEntity,RecommendRadioAdapter.RecommendRadioHolder> {


    public RecommendRadioAdapter(Context mContext, List data) {
        super(mContext, data);
    }

    @Override
    public void setData2View(RecommendRadioEntity item, RecommendRadioHolder holder) {
        holder.sd_recommend_list_single_item_poster .setImageURI(item.getPic());
        holder.tv_recommend_list_single_item_desc.setText(item.getDesc());
        holder.tv_recommend_list_single_item_category.setText(item.getType());
    }

    @Override
    protected RecommendRadioHolder onCreateCustomViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(getResLayoutId(viewType),parent,false);

        return new RecommendRadioHolder(view);
    }

    class RecommendRadioHolder extends RecommendBaseAdapter.RecommendBaseItemHolder{

        public RecommendRadioHolder(View itemView) {
            super(itemView);
        }
    }
}
