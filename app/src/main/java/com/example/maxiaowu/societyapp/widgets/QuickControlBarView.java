package com.example.maxiaowu.societyapp.widgets;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.bilibili.magicasakura.widgets.TintImageView;
import com.bilibili.magicasakura.widgets.TintProgressBar;
import com.example.maxiaowu.societyapp.R;
import com.example.maxiaowu.societyapp.utils.ImageLoaderManager;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.common.base.Preconditions;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by matingting on 2017/4/27.
 * 底部控制栏
 */

public class QuickControlBarView extends FrameLayout {
    private Context mContext;
    public @BindView(R.id.tiv_play_list) TintImageView mTivPlayList;
    public @BindView(R.id.tiv_play_btn) TintImageView mTivPlayBtn;
    public @BindView(R.id.tiv_play_next) TintImageView mTivPlayNextBtn;
    public @BindView(R.id.tpb_play_progress) TintProgressBar mTpbPlayProgress;
    public @BindView(R.id.tv_song_name) TextView      mTvSongName;
    public @BindView(R.id.tv_singer_name) TextView      mTvSinger;
    public @BindView(R.id.sdv_music_icon) SimpleDraweeView mSdvSongIcon;

    public QuickControlBarView(@NonNull Context context) {
        this(context,null);
    }

    public QuickControlBarView(@NonNull Context context,
            @Nullable AttributeSet attrs) {
        this(context, attrs,-1);
    }

    public QuickControlBarView(@NonNull Context context,
            @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext=context;
        inflate(mContext, R.layout.widget_quick_control_bar_layout,this);
        ButterKnife.bind(this);
        initView();
    }

    public void initView(){

    }

    public void setSongIcon(String url){
        if (!TextUtils.isEmpty(url)){
            ImageLoaderManager.loadImage(url,mSdvSongIcon,false);
        }
    }

    public void setSongName(String name){
        if (!TextUtils.isEmpty(name)){
            mTvSongName.setText(name);
        }else{
            mTvSongName.setText(R.string.unknown_music);
        }
    }
    public void setSingerName(String name){
        if (!TextUtils.isEmpty(name)){
            mTvSinger.setText(name);
        }else{
            mTvSinger.setText(R.string.unknown_singer);
        }
    }

    public void setPlayControlListener(
           final OnClickPlayControlListener playControlListener) {
        Preconditions.checkNotNull(playControlListener);
        mTivPlayList.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                playControlListener.onClickPlayList();
            }
        });
        mTivPlayBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                playControlListener.onClickPlayMusic();
            }
        });
        mTivPlayNextBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                playControlListener.onClickPlayNext();
            }
        });
    }
    public void setPlayProgress(int progress){
        mTpbPlayProgress.setProgress(progress);
    }
    public interface OnClickPlayControlListener{
       void onClickPlayList();
       void onClickPlayMusic();
       void onClickPlayNext();
    }
}
