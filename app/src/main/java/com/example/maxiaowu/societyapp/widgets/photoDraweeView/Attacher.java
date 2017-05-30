package com.example.maxiaowu.societyapp.widgets.photoDraweeView;

import android.animation.TimeInterpolator;
import android.content.Context;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.os.Build;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.widget.ScrollerCompat;
import android.util.Log;
import android.view.MotionEvent;
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
    private static final String TAG = "Attacher";
        private float mMinScale = 1.0f;
        private float mMidScale = 1.75f;
        private float mMaxScale = 3.0f;
        private long  mZoomDuration=200L;

    //可能横向和纵向的父控件都可以滑动
    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;

    private static final int EDGE_NONE = -1;
    private static final int EDGE_LEFT = 0, EDGE_TOP = 0;
    private static final int EDGE_RIGHT = 1, EDGE_BOTTOM = 1;
    private static final int EDGE_BOTH = 2;

    //这两个变量是用来记录图片区域水平/垂直方向是否达到了View边缘
    private int mScrollEdageY=EDGE_BOTH;
    private int mScrollEdageX=EDGE_BOTH;


    private GestureDetectorCompat mGestureDetector;//GestureDetectorCompat可以适配到API4
    private ScaleDragDetector mScaleGestureDetector;
    private Matrix mMatrix=new Matrix();
    private WeakReference<DraweeView<GenericDraweeHierarchy>> mDraweeView;
    private int mImageHeight;
    private int mImageWidth;
    private RectF mRectF=new RectF();
    private float[] mMatrixValues=new float[9];
    private TimeInterpolator mZoomInterpolator=new AccelerateDecelerateInterpolator();
    private int mOrientation=HORIZONTAL;
    private FlingRunnable mCurrentFlingRunnable;

    public Attacher(DraweeView<GenericDraweeHierarchy> draweeView) {
        mDraweeView=new WeakReference<DraweeView<GenericDraweeHierarchy>>(draweeView);
        mGestureDetector=new GestureDetectorCompat(draweeView.getContext(),new DefaultGestureDetectorListener(this));
        draweeView.setOnTouchListener(this);
        mScaleGestureDetector =new ScaleDragDetector(draweeView.getContext(),this);

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
            mScrollEdageY=EDGE_BOTH;
        }else if (rect.top>0){
            deltaY=-rect.top;
            mScrollEdageY=EDGE_TOP;
        }else if (rect.bottom<viewHeight){
            deltaY=viewHeight-rect.bottom;
            mScrollEdageY=EDGE_BOTTOM;
        }else{
            mScrollEdageY=EDGE_NONE;
        }
        float viewWidth=getViewWidth();
        if (width<=viewWidth){
            deltaX=viewWidth/2-(width/2+rect.left);
            mScrollEdageX=EDGE_BOTH;
        }else if (rect.left>0){
            deltaX=-rect.left;
            mScrollEdageX=EDGE_LEFT;
        }else if (rect.right<viewWidth){
            deltaX=viewWidth-rect.right;
            mScrollEdageX=EDGE_RIGHT;
        }else{
            mScrollEdageX=EDGE_NONE;
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
                Log.d(TAG, "onTouch: ACTION_DOWN");
                ViewParent parent=v.getParent();
                if (parent!=null) {
                    parent.requestDisallowInterceptTouchEvent(true);
                }
            }break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:{
                Log.d(TAG, "onTouch: "+event.getAction());
                ViewParent parent=v.getParent();
                if (parent!=null){
                    parent.requestDisallowInterceptTouchEvent(false);
                }
            }
            break;
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

    public void onDrag(float detalX, float detalY) {
        DraweeView<GenericDraweeHierarchy> draweeView=getDraweeView();
        if (draweeView!=null && !mScaleGestureDetector.isScaling()) {
            mMatrix.postTranslate(detalX, detalY);
            checkMatrixAndInvalidate();
            ViewParent parent = draweeView.getParent();
            if (parent==null){
                return;
            }
            if (!mScaleGestureDetector.isScaling()) {
                //判断水平(左、右)/垂直(上、下)方向上往哪边滑动可以交给父控件处理了
                if (mOrientation == HORIZONTAL && (mScrollEdageX == EDGE_BOTH || (mScrollEdageX == EDGE_LEFT
                && detalX > 1f || (mScrollEdageX == EDGE_RIGHT && detalX<-1f)))){
                    parent.requestDisallowInterceptTouchEvent(false);
                }
                if (mOrientation == VERTICAL && (mScrollEdageY == EDGE_BOTH || (mScrollEdageY == EDGE_TOP && detalY > 1f)
                    || (mScrollEdageY==EDGE_BOTTOM && detalY<-1f))){
                    parent.requestDisallowInterceptTouchEvent(false);
                }
            } else {
                parent.requestDisallowInterceptTouchEvent(true);
            }
        }
    }

    public void onFling(int vX, int vY) {
        DraweeView<GenericDraweeHierarchy> draweeView=getDraweeView();
        if (draweeView==null) {
            return;
        }
         mCurrentFlingRunnable=new FlingRunnable(draweeView.getContext());
        mCurrentFlingRunnable.fling(getViewWidth(),getViewHeight(),-vX,-vY);//TODO:图片滑动方向与手指fling方向相反:因为速度是相反的
        draweeView.post(mCurrentFlingRunnable);
    }
    private class FlingRunnable implements Runnable{
        private final ScrollerCompat mScroller;
        private int  mCurrentX,mCurrentY;
        public FlingRunnable(Context context) {
            mScroller = ScrollerCompat.create(context);
        }

        public void cancelFling(){ mScroller.abortAnimation();}

        public void fling(float viewWidth, float viewHeight, int vX, int vY){
            final RectF rectF=getDisplayRect();
            if (null==rectF){
                return;
            }
   //*******
            //平移--所有点移动的位置就都是相同的,所以哪个点都可以
            final int startX=Math.round(-rectF.left);
            final int minX,maxX,minY,maxY;
            if (rectF.width()<=viewWidth){
                minX=maxX=startX;
            }else{
                minX=0;
                maxX=Math.round(rectF.width()-viewWidth);
            }
            final int startY=Math.round(-rectF.top);
            if (rectF.height()<=viewHeight){
                minY=maxY=startY;
            }else{
                minY=0;
                maxY=Math.round(rectF.height()-viewHeight);

            }
            mCurrentX=startX;
            mCurrentY=startY;
            if (startX!=maxX ||startY!=maxY){
                mScroller.fling(startX,startY,vX,vY,minX,maxX,minY,maxY,0,0);
            }
    //******
          //  mScroller.fling(startX,startY,vX,vY,0,maxX,0,maxY,0,0);
        }

        @Override
        public void run() {
            if (mScroller.isFinished()){
                return;
            }
            DraweeView<GenericDraweeHierarchy> draweeView=getDraweeView();
            if (draweeView==null){
                return;
            }
            if (mScroller.computeScrollOffset()){//计算当前的偏移量
                final int newX=mScroller.getCurrX();
                final int newY=mScroller.getCurrY();
                mMatrix.postTranslate(mCurrentX-newX,mCurrentY-newY);
                draweeView.invalidate();
                mCurrentX=newX;
                mCurrentY=newY;
                postOnAnimation(draweeView,this);
            }
        }
        public void postOnAnimation(DraweeView draweeView,Runnable runnable){
            if (Build.VERSION.SDK_INT>=16){
                draweeView.postOnAnimation(runnable);
            }else{
                draweeView.postDelayed(runnable,16L);
            }
        }
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
            onScale(detalScale,focalX,focalY);
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

    private void cancelFling(){
        if (mCurrentFlingRunnable!=null){
            mCurrentFlingRunnable.cancelFling();
            mCurrentFlingRunnable=null;
        }
    }


    public void onScale(float detalScale, float focalX, float focalY) {
        if (getScale()<getmMaxScale() || detalScale<1.0f ){//当前缩放比例小于最大值,或者deltalScale<1(即图片缩小)时,可以进行发图片缩放
            //onScaleChaneListener监听
            mMatrix.postScale(detalScale,detalScale,focalX,focalY);
            checkMatrixAndInvalidate();
        }
    }
    public void onDetachFromWindow(){
        cancelFling();
    }
}
