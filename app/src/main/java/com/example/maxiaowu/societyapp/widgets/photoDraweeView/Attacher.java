package com.example.maxiaowu.societyapp.widgets.photoDraweeView;

import android.graphics.Matrix;
import android.graphics.RectF;
import android.support.v4.view.GestureDetectorCompat;
import android.view.MotionEvent;
import android.view.View;

import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.view.DraweeView;

import java.lang.ref.WeakReference;

/**
 * Created by matingting on 2017/5/5.
 */

class Attacher implements View.OnTouchListener{
    private GestureDetectorCompat mGestureDetector;//GestureDetectorCompat可以适配到API4
    private Matrix mMatrix=new Matrix();
    private WeakReference<DraweeView<GenericDraweeHierarchy>> mDraweeView;
    private int mImageHeight;
    private int mImageWidth;
    private RectF mRectF;

    public Attacher(DraweeView<GenericDraweeHierarchy> draweeView) {
        mDraweeView=new WeakReference<DraweeView<GenericDraweeHierarchy>>(draweeView);
        mGestureDetector=new GestureDetectorCompat(draweeView.getContext(),new GestureListener(this));
        draweeView.setOnTouchListener(this);

    }

    public float getScale() {
        return 0;
    }

    public void setScale(float scale, float x, float y, boolean isAnimated) {
        DraweeView<GenericDraweeHierarchy> draweeView=getDraweeView();
        if (draweeView==null){
            return;
        }
        if (isAnimated){

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
        return false;
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
    }
    public DraweeView<GenericDraweeHierarchy> getDraweeView() {
        return mDraweeView.get();
    }

    public Matrix getDrawMatrix() {
        return mMatrix;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        //TODO:调用手势监听的onTouchEvent
        return false;
    }
}
