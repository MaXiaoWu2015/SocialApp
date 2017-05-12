package com.example.maxiaowu.societyapp.widgets.photoDraweeView;

import android.view.GestureDetector;
import android.view.MotionEvent;

/**
 * Created by matingting on 2017/5/5.
 */

public class DefaultDoubleTapListener implements GestureDetector.OnDoubleTapListener {
    private Attacher mAttacher;
    public DefaultDoubleTapListener(Attacher attacher) {
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

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        return false;
    }
}
