package com.example.maxiaowu.societyapp.widgets.photoDraweeView;

import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

/**
 * Created by matingting on 2017/5/5.
 */

public class DefaultGestureDetectorListener extends GestureDetector.SimpleOnGestureListener {
    private static final String TAG = "DefaultGestureDetectorL";
    private Attacher mAttacher;
    public DefaultGestureDetectorListener(Attacher attacher) {
        mAttacher=attacher;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        if (mAttacher==null){
            return false;
        }
        float scale=mAttacher.getScale();
        if (scale<mAttacher.getmMidScale()){
            mAttacher.setScale(mAttacher.getmMidScale(),e.getX(),e.getY(),true);
        }else if (scale>=mAttacher.getmMidScale() && scale<mAttacher.getmMaxScale()){
            mAttacher.setScale(mAttacher.getmMaxScale(),e.getX(),e.getY(),true);
        }else if (scale>=mAttacher.getmMaxScale()){
            mAttacher.setScale(mAttacher.getmMinScale(),e.getX(),e.getY(),true);
        }
        return true;
    }

    //TODO:手指Fling后图片移动
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        mAttacher.onFling(((int)velocityX),((int)velocityY));//减速运动
        Log.d(TAG, "onFling: "+e1.getX()+"--"+e1.getY()+":"+e2.getX()+"---"+e2.getY());
        return true;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        return false;
    }
}
