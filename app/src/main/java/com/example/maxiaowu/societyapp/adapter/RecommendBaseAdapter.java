package com.example.maxiaowu.societyapp.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.aqy.matingting.basiclibrary.base.BaseAdapter;
import com.aqy.matingting.basiclibrary.base.BaseViewHolder;
import com.example.maxiaowu.societyapp.R;
import com.example.maxiaowu.societyapp.entity.RecommendBaseEntity;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import butterknife.BindView;

public abstract class RecommendBaseAdapter<K extends RecommendBaseEntity,T extends RecommendBaseAdapter.RecommendBaseItemHolder> extends BaseAdapter<K,T> {
    

    public RecommendBaseAdapter(Context mContext, List<K> data) {
        super(mContext,data);
    }

    @Override
    public int getResLayoutId(int type) {
        return R.layout.recommend_list_single_item;
    }

    public abstract  class RecommendBaseItemHolder<T> extends BaseViewHolder<T>{

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
//              ButterKnife.bind(itemView);

              sd_recommend_list_single_item_poster = (SimpleDraweeView) itemView.findViewById(R.id.sd_recommend_list_single_item_poster);
              tv_recommend_list_single_item_desc = (TextView) itemView.findViewById(R.id.tv_recommend_list_single_item_desc);
              tv_recommend_list_single_item_category = (TextView) itemView.findViewById(R.id.tv_recommend_list_single_item_category);
              tv_recommend_list_single_item_count = (TextView) itemView.findViewById(R.id.tv_recommend_list_single_item_count);


              sd_recommend_list_single_item_poster.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                      onPosterClick((SimpleDraweeView) v);
                  }
              });

          }

          public abstract void onPosterClick(SimpleDraweeView draweeView);



    }


    }