package com.example.maxiaowu.societyapp.widgets.photoDraweeView;

import android.view.GestureDetector;
import android.view.MotionEvent;

/**
 * Created by matingting on 2017/5/5.
 */

public class GestureListener extends GestureDetector.SimpleOnGestureListener {
    private Attacher mAttacher;
    public GestureListener(Attacher attacher) {
        mAttacher=attacher;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        super.onLongPress(e);
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        if (mAttacher==null){
            return false;
        }
        mAttacher.setScale(3,e.getX(),e.getY(),false);
        return super.onDoubleTap(e);
    }
}
