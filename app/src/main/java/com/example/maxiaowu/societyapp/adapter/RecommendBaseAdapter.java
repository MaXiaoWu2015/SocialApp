package com.example.maxiaowu.societyapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.maxiaowu.societyapp.R;
import com.example.maxiaowu.societyapp.adapter.viewholder.BaseViewHolder;
import com.example.maxiaowu.societyapp.entity.RecommendBaseEntity;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.ButterKnife;

public abstract class RecommendBaseAdapter<T extends RecommendBaseAdapter.RecommendBaseItemHolder> extends RecyclerView.Adapter<T>{
    
    protected Context mContext;
    
    public RecommendBaseAdapter(Context mContext) {
        this.mContext = mContext;
    }


    @Override
    public T onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(T holder, int position) {

    }

    public abstract   class RecommendBaseItemHolder<K extends RecommendBaseEntity> extends BaseViewHolder<K>{

          @BindView(R.id.sd_recommend_list_single_item_poster)
          public SimpleDraweeView sd_recommend_list_single_item_poster;

          @BindView(R.id.tv_recommend_list_single_item_desc)
          public TextView tv_recommend_list_single_item_desc;

          @BindView(R.id.tv_recommend_list_single_item_category)
          public TextView tv_recommend_list_single_item_category;

          @BindView(R.id.tv_recommend_list_single_item_count)
          public TextView tv_recommend_list_single_item_count;

          public RecommendBaseItemHolder(View itemView) {
              super(itemView);
              ButterKnife.bind(itemView);
          }

    }


    }