package com.example.maxiaowu.societyapp.widgets.photoDraweeView;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;

/**
 * Created by matingting on 2017/5/9.
 */

public class ScaleDragDetector implements ScaleGestureDetector.OnScaleGestureListener {
    private static final String TAG = "ScaleDragDetectorListen";
    private static final int INVALID_POINTER_ID=-1;
    private final float mTouchSlop;
    private final int mMinimumVelocity;
    private Attacher mAttacher;
    private VelocityTracker mVelocityTracker;
    private int mActivePointerIndex;
    private float mLastTouchX;
    private float mLastTouchY;
    private boolean mIsDragging;//当手指ACTION_UP时,判断图片需要根据惯性移动
    private int mActivePointerId;
    private ScaleGestureDetector mScaleGestureDetector;

    public ScaleDragDetector(
            Context context,Attacher attacher) {
        mAttacher = attacher;
        mScaleGestureDetector=new ScaleGestureDetector(context,this);
        ViewConfiguration configuration=new ViewConfiguration();
        mMinimumVelocity = configuration.getScaledMinimumFlingVelocity();
        mTouchSlop = configuration.getScaledTouchSlop();
    }

    @Override
    public boolean onScale(ScaleGestureDetector detector){
//        Log.d("detector.getScaleFactor():",""+detector.getScaleFactor());
        Log.d(TAG,"onScale:"+detector.getScaleFactor());
        if (Float.isNaN(detector.getScaleFactor()) || Float.isInfinite(detector.getScaleFactor())){
            return false;
        }
            mAttacher.onScale(detector.getScaleFactor(),detector.getFocusX(),detector.getFocusY());

        return true;//之前返回值一直为false时,导致缩小图片时可以一直缩小到invisible
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
    public void onTouchEvent(MotionEvent event){
        mScaleGestureDetector.onTouchEvent(event);
        onTouchDragEvent(event);
    }
    private void onTouchActivePointer(MotionEvent event){
        int action=MotionEventCompat.getActionMasked(event);
        switch (action){
            case MotionEvent.ACTION_DOWN:
                mActivePointerId=event.getPointerId(0);
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                mActivePointerId=INVALID_POINTER_ID;
                break;
            case MotionEvent.ACTION_POINTER_UP:
                final int pointerIndex=MotionEventCompat.getActionIndex(event);
                final int poniterId=MotionEventCompat.getPointerId(event,pointerIndex);
                if (poniterId==mActivePointerId){
                    final int newPointerIndex=pointerIndex==0?1:0;
                    mActivePointerId=MotionEventCompat.getPointerId(event,newPointerIndex);
                    mLastTouchX=event.getX(newPointerIndex);
                    mLastTouchY=event.getY(newPointerIndex);
                }
                break;
        }
        mActivePointerIndex=event.findPointerIndex(mActivePointerId==INVALID_POINTER_ID?0:mActivePointerId);
    }

    private void onTouchDragEvent(MotionEvent event){
        onTouchActivePointer(event);
        int action= MotionEventCompat.getActionMasked(event);
        switch (action){
            case MotionEvent.ACTION_DOWN:
//                mVelocityTracker=VelocityTracker.obtain();
//                if (null!=mVelocityTracker){
//                    mVelocityTracker.addMovement(event);
//                }
                mLastTouchX=getActiveX(event);
                mLastTouchY=getActiveY(event);
                mIsDragging=false;
                break;
            case MotionEvent.ACTION_MOVE:
                float x=getActiveX(event);
                float y=getActiveY(event);
                float detalX=x-mLastTouchX;
                float detalY=y-mLastTouchY;
                if (!mIsDragging){
                    mIsDragging=Math.sqrt(Math.pow(detalX,2)+Math.pow(detalY,2))>mTouchSlop;
                }
                if (mIsDragging){
                    mAttacher.onDrag(detalX,detalY);
                    mLastTouchY=y;
                    mLastTouchX=x;
                }
//                if (null!=mVelocityTracker){
//                    mVelocityTracker.addMovement(event);
//                }
                break;
            case MotionEvent.ACTION_CANCEL:
//                if (null!=mVelocityTracker){
//                    mVelocityTracker.recycle();
//                    mVelocityTracker=null;
//                }
                break;
            case MotionEvent.ACTION_UP:
                if (mIsDragging){
                    //使用GetureDetector检测Fling手势
//                    if (null!=mVelocityTracker){
//                        mVelocityTracker.addMovement(event);
//                        mVelocityTracker.computeCurrentVelocity(1000);
//
//                        final float vX=mVelocityTracker.getXVelocity();
//                        final float vY=mVelocityTracker.getYVelocity();
//                        if (Math.max(Math.abs(vX),Math.abs(vY))>=mMinimumVelocity){
//                            mAttacher.onFling(getActiveX(event),getActiveY(event),vX,vY);
//                        }
//                    }
                }
//                if (null!=mVelocityTracker){
//                    mVelocityTracker.recycle();
//                    mVelocityTracker=null;
//                }
                break;
        }
    }

    private float getActiveY(MotionEvent event) {
        float y;
        try {
            y=event.getY(mActivePointerIndex);
        }catch (Exception e){
            y=event.getY();
        }
        return y;
    }

    public float getActiveX(MotionEvent event){
        float x;
        try {
            x=event.getX(mActivePointerIndex);
        }catch (Exception e){
            x=event.getX();
        }
        return x;
    }

    public boolean isScaling(){
        return mScaleGestureDetector.isInProgress();
    }
}
