package com.example.maxiaowu.societyapp.transition_demo;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.maxiaowu.societyapp.R;

import java.util.List;

/**
 * Created by matingting on 2018/1/25.
 */

public class TransitionAdapter extends RecyclerView.Adapter<TransitionAdapter.MyViewHolder> {

    private Context mContext;

    private List<TransitionActivity.Sample> samples;

    public TransitionAdapter(Context mContext, List<TransitionActivity.Sample> samples) {
        this.mContext = mContext;
        this.samples = samples;
    }

    @Override
    public TransitionAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        MyViewHolder viewHolder = new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.transition_item_activity,viewGroup,false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TransitionAdapter.MyViewHolder viewHolder, int i) {
        viewHolder.mText.setText(samples.get(i).name);
        viewHolder.mText.setTextColor(samples.get(i).color);
        //给ImageView的src设置<shape> drawable文件,imageView.getDrawable返回的类型是GradientDrawable
        //动态设置GradientDrawable,如果之后又View使用相同的drawable文件,drawable的颜色为你设置的最后一个颜色,可能原因: 因为是同一个drawable文件
        // ,并且对应的drawable对象已经存在内存中,可以直接copy的,而不是重新加载文件生成对象
        //TODO:看ImageView源码  分析src和background的异同
        ((GradientDrawable)viewHolder.mBall.getBackground()).setColor(samples.get(i).color);

    }



    @Override
    public int getItemCount() {
        return samples.size();
    }



     class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView mText;

        public ImageView mBall;

        public MyViewHolder(View itemView) {
            super(itemView);
            mText = itemView.findViewById(R.id.sample_item);
            mBall = itemView.findViewById(R.id.sample_image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (getAdapterPosition() < 0)return;
                    switch (getAdapterPosition()){
                        case 0:
                            transitionToActivity(FirstTransitionActivity.class,getAdapterPosition());
                            break;
                        case 1:
                            shareTransitionToActivity(SecondShareElementActivity.class,new Pair<View, String>(mBall,"blueBall"));
                            break;
                        case 2:
                            transitionToActivity(ViewAnimationActivity.class,getAdapterPosition());
                            break;
                        case 3:
                            shareTransitionToActivity(RevealActivity.class,new Pair<View, String>(mBall,"yellowBall"));
                            break;
                    }
                }
            });
        }

         @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
         private void shareTransitionToActivity(Class activityClass, Pair<View,String> pairs) {

             ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity)mContext,pairs);
             mContext.startActivity(new Intent(mContext,activityClass),options.toBundle());


         }

         @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
         private void  transitionToActivity(Class firstTransitionActivityClass, int adapterPosition) {
            //TODO:01/26
             final Pair<View,String>[] pairs = TransitionHelper.createTransitionParticipants((Activity)mContext,true);
             ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) mContext,pairs);
             mContext.startActivity(new Intent(mContext,firstTransitionActivityClass),options.toBundle());//通过这种方式启动Activity转场动画才有效果

         }
     }
}
