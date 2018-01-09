package com.example.maxiaowu.societyapp.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ImageSpan;
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

    private SpannableString spanString;

    public RecommendSongListAdapter(Context mContext,ArrayList<RecommendSongListEntity> list) {
        super(mContext,list);
        Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(),R.mipmap.index_icn_earphone);
        ImageSpan imageSpan = new ImageSpan(mContext,bitmap,ImageSpan.ALIGN_BASELINE);//TODO:SpannableString 了解
        spanString = new SpannableString("icon ");
        spanString.setSpan(imageSpan,0,4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);



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
        holder.tv_recommend_list_single_item_count.setText(spanString);
        holder.tv_recommend_list_single_item_count.append(dataFormat(item.getListenum()));//TODO:数字格式相关工具类
        holder.tv_recommend_list_single_item_desc.setText(item.getTag());
    }

    private String dataFormat(String listNum){
        int count = Integer.valueOf(listNum);

        if (count>10000){
            count = count/10000;

            return count+"万";
        }

        return listNum;

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
