package com.example.maxiaowu.societyapp.widgets.photoDraweeView;

import android.util.Log;
import android.view.ScaleGestureDetector;

/**
 * Created by matingting on 2017/5/9.
 */

public class ScaleDragDetectorListener implements ScaleGestureDetector.OnScaleGestureListener {
    private static final String TAG = "ScaleDragDetectorListen";
    private Attacher mAttacher;

    public ScaleDragDetectorListener(
            Attacher attacher) {
        mAttacher = attacher;
    }

    @Override
    public boolean onScale(ScaleGestureDetector detector){
//        Log.d("detector.getScaleFactor():",""+detector.getScaleFactor());
        Log.d(TAG,"onScale:"+detector.getScaleFactor());
        if (Float.isNaN(detector.getScaleFactor()) || Float.isInfinite(detector.getScaleFactor())){
            return false;
        }
            mAttacher.onScale(detector.getScaleFactor(),detector.getFocusX(),detector.getFocusY());

        return false;
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector)
    {
        Log.d(TAG,"onScaleBegin");
        return true;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {
        mAttacher.checkMinScale();
        Log.d(TAG,"onScaleEnd");
    }
}
