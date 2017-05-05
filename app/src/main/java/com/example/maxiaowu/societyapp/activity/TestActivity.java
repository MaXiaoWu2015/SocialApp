package com.example.maxiaowu.societyapp.activity;

import android.graphics.drawable.Animatable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.maxiaowu.societyapp.R;
import com.example.maxiaowu.societyapp.utils.ImageLoaderManager;
import com.example.maxiaowu.societyapp.widgets.photoDraweeView.PhotoDraweeView;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.imagepipeline.image.ImageInfo;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TestActivity extends AppCompatActivity {
    @BindView(R.id.pdv_test) PhotoDraweeView  pdv_test;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);
        ImageLoaderManager.loadImage("res:///" +R.drawable.viewpager_1,pdv_test,new BaseControllerListener<ImageInfo>(){
            @Override
            public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
                pdv_test.update(imageInfo.getWidth(),imageInfo.getHeight());
            }
        },false);
    }
}
