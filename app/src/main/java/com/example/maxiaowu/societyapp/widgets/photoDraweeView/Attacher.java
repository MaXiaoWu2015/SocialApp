package com.example.maxiaowu.societyapp.widgets.photoDraweeView;

import android.animation.TimeInterpolator;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.MotionEventCompat;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewParent;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.view.DraweeView;

import java.lang.ref.WeakReference;

/**
 * Created by matingting on 2017/5/5.
 */

class Attacher implements View.OnTouchListener{
        private float mMinScale = 1.0f;
        private float mMidScale = 1.75f;
        private float mMaxScale = 3.0f;
        private long  mZoomDuration=200L;

    private GestureDetectorCompat mGestureDetector;//GestureDetectorCompat可以适配到API4
    private ScaleGestureDetector mScaleGestureDetector;
    private Matrix mMatrix=new Matrix();
    private WeakReference<DraweeView<GenericDraweeHierarchy>> mDraweeView;
    private int mImageHeight;
    private int mImageWidth;
    private RectF mRectF=new RectF();
    private float[] mMatrixValues=new float[9];
    private TimeInterpolator mZoomInterpolator=new AccelerateDecelerateInterpolator();
    private ScaleDragDetectorListener mScaleDragDetectorListener;

    public Attacher(DraweeView<GenericDraweeHierarchy> draweeView) {
        mDraweeView=new WeakReference<DraweeView<GenericDraweeHierarchy>>(draweeView);
        mGestureDetector=new GestureDetectorCompat(draweeView.getContext(),new GestureDetector.SimpleOnGestureListener(){

        });
        mGestureDetector.setOnDoubleTapListener(new DefaultDoubleTapListener(this));
        draweeView.setOnTouchListener(this);
        mScaleGestureDetector =new ScaleGestureDetector(draweeView.getContext(),mScaleDragDetectorListener=new ScaleDragDetectorListener(this));

    }

    public float getScale() {
        return (float) Math.sqrt(Math.pow(getMatrixValue(mMatrix,Matrix.MSCALE_X),2)+
        Math.pow(getMatrixValue(mMatrix,Matrix.MSKEW_Y),2));
    }
    public float getMatrixValue(Matrix matrix,int whichValue){
        matrix.getValues(mMatrixValues);
        return mMatrixValues[whichValue];
    }
    public void setScale(float scale, float x, float y, boolean isAnimated) {
        DraweeView<GenericDraweeHierarchy> draweeView=getDraweeView();
        if (draweeView==null){
            return;
        }
        if (isAnimated){
            draweeView.post(new AnimatedZoomRunnable(getScale(),scale,x,y));
        }else{
            mMatrix.setScale(scale,scale,x,y);
            checkMatrixAndInvalidate();
        }
    }

    private void checkMatrixAndInvalidate() {
        DraweeView<GenericDraweeHierarchy> draweeview=getDraweeView();
        if (draweeview==null){
            return;
        }
        if (checkMatrixBounds()){
            draweeview.invalidate();
        }
    }
//移动时进行边界判断:当图片大于屏幕时,移动图片,图片的边界至少等于屏幕边界
    private boolean checkMatrixBounds() {
        RectF rect=getDisplayRect(getDrawMatrix());
        if (rect==null){
            return false;
        }
        float width=rect.width();
        float height=rect.height();
        float viewHeight=getViewHeight();
        float deltaX=0.0f;
        float deltaY=0.0f;
        if (height<=viewHeight){//如果宽高小于屏幕,则让图片居中
            deltaY=viewHeight/2-(height/2+rect.top);
        }else if (rect.top>0){
            deltaY=-rect.top;
        }else if (rect.bottom<viewHeight){
            deltaY=viewHeight-rect.bottom;
        }
        float viewWidth=getViewWidth();
        if (width<=viewWidth){
            deltaX=viewWidth/2-(width/2+rect.left);
        }else if (rect.left>0){
            deltaX=-rect.left;
        }else if (rect.right<viewWidth){
            deltaX=viewWidth-rect.right;
        }
        mMatrix.postTranslate(deltaX,deltaY);//先有Matrix的变换,后有图像的变换
        return true;
    }

    private float getViewWidth() {
        DraweeView<GenericDraweeHierarchy> draweeView=getDraweeView();
        if (draweeView!=null){
            return draweeView.getWidth()-draweeView.getPaddingLeft()-draweeView.getPaddingRight();
        }
        return 0;
    }

    private float getViewHeight() {
        DraweeView<GenericDraweeHierarchy> draweeView=getDraweeView();
        if (draweeView==null){
            return 0;
        }
        return draweeView.getHeight()-draweeView.getPaddingTop()-draweeView.getPaddingBottom();
    }

    private RectF getDisplayRect(Matrix drawMatrix) {
        DraweeView<GenericDraweeHierarchy> draweeView=getDraweeView();
        mRectF.set(0,0,mImageWidth,mImageHeight);
        draweeView.getHierarchy().getActualImageBounds(mRectF);
        drawMatrix.mapRect(mRectF);
        return mRectF;
    }
    public void update(int imageWidth,int imageHeight){
        mImageWidth=imageWidth;
        mImageHeight=imageHeight;
        updateBaseMatrix();
    }

    private void updateBaseMatrix() {
        if (mImageWidth ==-1 &&mImageHeight == -1){
            return;
        }
        resetMatrx();

    }

    private void resetMatrx() {
        mMatrix.reset();
        checkMatrixBounds();
        DraweeView<GenericDraweeHierarchy> draweeView=getDraweeView();
        if (draweeView!=null){
            draweeView.invalidate();
        }
    }

    public DraweeView<GenericDraweeHierarchy> getDraweeView() {
        return mDraweeView.get();
    }

    public Matrix getDrawMatrix() {
        return mMatrix;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int action= MotionEventCompat.getActionMasked(event);
        switch (action){
            case MotionEvent.ACTION_DOWN:{
                ViewParent parent=v.getParent();
                if (parent!=null) {
                    parent.requestDisallowInterceptTouchEvent(true);
                }
            }break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:{
                ViewParent parent=v.getParent();
                if (parent!=null){
                    parent.requestDisallowInterceptTouchEvent(false);
                }
            }

        }

        mScaleGestureDetector.onTouchEvent(event);

        if (mGestureDetector.onTouchEvent(event)){
            return true;
        }

        return true;//必须要返回true,因为要拦截点击事件
    }

    public float getmMinScale() {
        return mMinScale;
    }

    public void setmMinScale(float mMinScale) {
        this.mMinScale = mMinScale;
    }

    public float getmMidScale() {
        return mMidScale;
    }

    public void setmMidScale(float mMidScale) {
        this.mMidScale = mMidScale;
    }

    public float getmMaxScale() {
        return mMaxScale;
    }

    public void setmMaxScale(float mMaxScale) {
        this.mMaxScale = mMaxScale;
    }

    public void checkMinScale() {
        DraweeView<GenericDraweeHierarchy> draweeView=getDraweeView();
        if (draweeView==null){
            return;
        }
        if (getScale()<mMinScale){
            RectF rect=getDisplayRect();
            if (rect!=null){
                draweeView.post(new AnimatedZoomRunnable(getScale(),mMinScale,rect.centerX(),rect.centerY()));
            }
        }
    }

    private RectF getDisplayRect() {
        checkMatrixAndInvalidate();
        return getDisplayRect(mMatrix);


    }

    private class AnimatedZoomRunnable implements Runnable {
        //1.插补器 TimeInterpolatpr  控制变换速率
        //
        private float mZoomStart;
        private float mZoomEnd;
        private float focalX;
        private float focalY;
        private long mStartTime;
        public AnimatedZoomRunnable(float currentZoom, float targetZoom, float focalx, float focaly) {
            mZoomStart=currentZoom;
            mZoomEnd=targetZoom;
            this.focalX=focalx;
            this.focalY=focaly;
            mStartTime=System.currentTimeMillis();
        }

        @Override
        public void run() {
            DraweeView<GenericDraweeHierarchy> draweeView=getDraweeView();
            if (draweeView==null){
                return;
            }

            float t=interpolat();
            float scale=mZoomStart+t*(mZoomEnd-mZoomStart);
            float detalScale=scale/getScale();//scale是按比例缩放的
            mScaleDragDetectorListener.onScale(mScaleGestureDetector);
            if (t<1f){
                draweeView.postDelayed(this,16L);
            }

        }

        private float interpolat() {
            float t = 1f * (System.currentTimeMillis() - mStartTime) / mZoomDuration;//mStarTime单位写成float了,导致t一直为0
            t=Math.min(1f,t);
            t=mZoomInterpolator.getInterpolation(t);
            return t;
        }
    }

    public void onScale(float detalScale, float focalX, float focalY) {
        if (getScale()<getmMaxScale() || detalScale<1.0f ){//当前缩放比例小于最大值,或者deltalScale<1(即图片缩小)时,可以进行发图片缩放
            //onScaleChaneListener监听
            mMatrix.postScale(detalScale,detalScale,focalX,focalY);
            checkMatrixAndInvalidate();
        }
    }
}
