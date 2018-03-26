package com.example.maxiaowu.societyapp.widgets.ptr;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.Scroller;

/**
 * Created by matingting on 2018/3/22.
 */

public abstract class PtrAbstractLayout<V extends View> extends FrameLayout {

    private static final String TAG = "PtrAbstractLayout";



    //下拉刷新最大高度
    protected  float mMaxPullOffset = 100.0f;

    protected  float mScale = mMaxPullOffset/5;//如果后来不给mScale赋值,mScale就是固定值20

    protected V mContentView;

    protected View mRefreshView;

    protected View mLoadMoreView;

    protected int mScrollPointerId;

    protected boolean  isBeginDragged = false;

    protected  float mLastMotionY;

    private int mTouchSlop = 0;

    protected int mCurPosY;

    public ValueAnimator animator ;

    private ValueAnimator animator1;

    protected  PtrIndicator mPtrIndicator;

    protected boolean enableRefresh = true;//是否开启下拉刷新

    protected boolean enableLoadMore = false;




    public PtrAbstractLayout(@NonNull Context context) {
        this(context,null);
    }

    public PtrAbstractLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,-1);
    }

    public PtrAbstractLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        mPtrIndicator = new PtrIndicator();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        requestLayout();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if (mRefreshView != null){
            measureChildWithMargins(mRefreshView,widthMeasureSpec,0,heightMeasureSpec,0);
        }

        if (mContentView != null){
//            measureChildWithMargins(mContentView,widthMeasureSpec,0,heightMeasureSpec,0);
            measureContentView(mContentView,widthMeasureSpec,heightMeasureSpec);
        }

        if (mLoadMoreView != null){
            measureChild(mLoadMoreView,widthMeasureSpec,heightMeasureSpec);

        }

    }

    private void measureContentView(View contentView, int widthMeasureSpec, int heightMeasureSpec) {
        final MarginLayoutParams lp = (MarginLayoutParams) contentView.getLayoutParams();

        final int childWidthMeasureSpec = getChildMeasureSpec(widthMeasureSpec
        ,getPaddingLeft()+getPaddingRight()+lp.leftMargin+lp.rightMargin,lp.width);


        final int childHeightMeasureSpec = getChildMeasureSpec(heightMeasureSpec
        ,getPaddingTop()+getPaddingBottom()+lp.topMargin+lp.bottomMargin,lp.height);


        contentView.measure(childWidthMeasureSpec,childHeightMeasureSpec);


    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        layoutChildren();


        mMaxPullOffset = getMaxPullOffset();
        mScale = mMaxPullOffset/5;
    }

    //getMeasureXXX在onMeasure之后就可以获取     getXXX在layout之后才能获取
    private void layoutChildren() {

        int paddingLeft = getPaddingLeft();

        int paddingTop = getPaddingTop();

        if (mRefreshView != null){

            MarginLayoutParams params = (MarginLayoutParams) mRefreshView.getLayoutParams();

            int top = paddingTop + params.topMargin ;

            int bottom = top + mRefreshView.getMeasuredHeight();

            int left = paddingLeft + params.leftMargin;

            int right = left + mRefreshView.getMeasuredWidth();

            mRefreshView.layout(left,top,right,bottom);
        }

        if (mContentView != null){
            MarginLayoutParams params = (MarginLayoutParams) mContentView.getLayoutParams();

            int top = paddingTop + params.topMargin;

            int bottom = top + mContentView.getMeasuredHeight();

            int left = paddingLeft + params.leftMargin;

            int right = left + mContentView.getMeasuredWidth();

            mContentView.layout(left,top,right,bottom);

        }


        if (mLoadMoreView != null){
            MarginLayoutParams params = (MarginLayoutParams) mLoadMoreView.getLayoutParams();

            int top = mContentView.getBottom() + params.topMargin;

            int bottom = top + mLoadMoreView.getMeasuredHeight();

            int left = paddingLeft + params.leftMargin;

            int right = left + mLoadMoreView.getMeasuredWidth();

            mLoadMoreView.layout(left,top,right,bottom);

        }

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {


        int action = MotionEventCompat.getActionMasked(ev);

        Log.d(TAG, "dispatchTouchEvent: " +action);

        int actionIndex = MotionEventCompat.getActionIndex(ev);

        switch (action){
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                mScrollPointerId = ev.getPointerId(actionIndex);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                isBeginDragged = false;
                if (mPtrIndicator.leftStartPosition()){
                    onRelease();
                }

                break;
        }

        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int action = MotionEventCompat.getActionMasked(ev);

        int eventIndex = MotionEventCompat.getActionIndex(ev);

        isBeginDragged = false;

        switch (action){
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:

                mScrollPointerId = MotionEventCompat.getPointerId(ev,eventIndex);

                if (canPullUp() || canPullDown()){
                    mLastMotionY = ev.getY(eventIndex);
                    isBeginDragged = false;//在DOWN的时候不要拦截事件,只在MOVE时根据判断的条件决定是否拦截
                }
                break;

            case MotionEvent.ACTION_MOVE:
                int index = ev.findPointerIndex(mScrollPointerId);

                if (index > ev.getPointerCount() || index < 0){//产生Down事件的手指离开了
                    break;
                }

                float y = ev.getY(index);

                float detalY = y - mLastMotionY;

                boolean canDragDown = (detalY > mTouchSlop) && canPullDown();

                boolean canDragUp = (-detalY > mTouchSlop) && canPullUp();

                boolean canDrag =  (canDragDown || canDragUp);

                if (canDrag){
                    isBeginDragged = true;
                    mLastMotionY = y;
                }

                ViewParent parent = getParent();

                if (parent != null){
                    parent.requestDisallowInterceptTouchEvent(true);
                }

                break;
        }



        return isBeginDragged;
    }


    /**
     * dispatchTouchEvent 每次一定会调用
     *
     * 调用onTouchEvent不一定调用onInterceptTouchEvent
     *
     * 调用onInterceptTouchEvent 也不一定会调用onTouchEvent
     *
     * */
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (mContentView == null) return false;

        int action = MotionEventCompat.getActionMasked(event);

        //该Index只在Down和Up是有用,在move时是无效的,在多点触控时要想追踪事件流,要根据PointerId作为标准
        //通过ActionIndex获取PointerId,在通过PointerId获取PointerIndex
        //PointerId 和 PointerIndex 的区别:PointerId 在手指按下时产生,在手指抬起时回收,期间一直不变;
        //                                PointerIndex可能会因为其他手指的抬起和落下改变
        int eventIndex = MotionEventCompat.getActionIndex(event);

        switch (action){
            case MotionEvent.ACTION_DOWN://第一个手指初次接触到屏幕时,触发
                return  true;
            case MotionEvent.ACTION_POINTER_DOWN://之后的手指接触到屏幕时,触发

                mScrollPointerId = event.getPointerId(eventIndex);

                int downIndex = event.findPointerIndex(mScrollPointerId);

                if (downIndex > event.getPointerCount() || downIndex <0){
                    break;
                }

                mLastMotionY = event.getY(downIndex);

               return true;
            case MotionEvent.ACTION_MOVE:

                int moveIndex = event.findPointerIndex(mScrollPointerId);

                float offset =  (event.getY(moveIndex)-mLastMotionY);
                if (Math.abs(offset) > mTouchSlop){
                    doMovePos(offset);
                    mLastMotionY = event.getY(moveIndex);
                }

                return true;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (event.getPointerCount() == 1){
                    mLastMotionY = 0 ;
                }

//                final int curTop = mContentView.getTop();
//
//                animator = ValueAnimator.ofInt(curTop,0).setDuration(500);
//
//                  animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                    @Override
//                    public void onAnimationUpdate(ValueAnimator animation) {
//                        mContentView.setTop((int) animation.getAnimatedValue());
//                        Log.d(TAG, "onAnimationUpdate: "+animation.getAnimatedValue()+":"+mContentView.getTop());
//                    }
//                });
//
//                final int refreshTop = mRefreshView.getTop();
//
//                animator1 =ValueAnimator.ofInt(refreshTop,0).setDuration(300);
//                 animator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                    @Override
//                    public void onAnimationUpdate(ValueAnimator animation) {
//                        mRefreshView.setTop((int) animation.getAnimatedValue());
//
//                    }
//                });
//
//                AnimatorSet set = new AnimatorSet();
//                set.playTogether(animator,animator1);
//                set.start();

                mCurPosY = 0;
                return true;

        }

        return false;
    }


    private void doMovePos(float offset) {
        if (offset == 0) return;

        float to = mCurPosY + offset;

        if (offset > 0 && to < 0 || offset < 0 && to > 0){
            to = 0f;
        }


        if (Math.abs(to) >= getMaxPullOffset() ){
            if (to > 0 ){
                to = getMaxPullOffset();
            }else {
                to = -getMaxPullOffset();
            }
        }

        offset = to - mCurPosY;

        mCurPosY = (int) to;

        updatePos(offset);




        Log.d(TAG, "doMovePos: "+"mCurPosY--"+mCurPosY+"   offset:"+(int) (offset / getResistance( offset > 0))
        +"mLoadMore:"+this.getBottom()+":"+mContentView.getBottom());

    }

    private void updatePos(float offset) {

        if (offset == 0){
            return;
        }

        switch (mPtrIndicator.status){
            case PtrIndicator.PTR_STATUS_INIT:
                if (mPtrIndicator.justLeftStartPosition()){
                    mPtrIndicator.status = PtrIndicator.PTR_STATUS_PREPARE;
                }
                break;
            case PtrIndicator.PTR_STATUS_PREPARE:

                break;
        }





        mContentView.offsetTopAndBottom((int) (offset / getResistance( offset > 0)));
        if (offset > 0){
            mRefreshView.offsetTopAndBottom((int) (offset/(2*getResistance(offset>0))));
        }else{
            mLoadMoreView.setVisibility(VISIBLE);//必须要mLoadMoreView先设置为Invisible,然后再设置成visible,否则第一次不显示
            mLoadMoreView.offsetTopAndBottom((int) (offset/ (getResistance(offset>0))));
        }


    }
    private void onRelease() {
        switch (mPtrIndicator.status){
            case PtrIndicator.PTR_STATUS_PREPARE:
                break;
            case PtrIndicator.PTR_STATUS_REFRESHING:
            case PtrIndicator.PTR_STATUS_LOADING:
                break;
            case PtrIndicator.PTR_STATUS_COMPLETE:
            default:

                break;
        }
    }


    private boolean tryScrollBackToOffset(){

    }


    /**
     * 获取阻力系数
     * */
    public final float getResistance(boolean isDown){
        if (isDown){

            if (mCurPosY < mScale){
                return 2.0f;
            }else if (mCurPosY < mScale * 2){
                return 4.0f;
            }else if (mCurPosY <mScale * 3){
                return 8.0f;
            }else if (mCurPosY < mScale * 4){
                return 16.0f;
            }else{
                return 32.0f;
            }

        }else{

            return 2.0f;
        }
    }


    private  float getMaxPullOffset(){

        float maxOffset = getTop() + this.getHeight();

        return maxOffset;


    }

    protected abstract boolean canPullDown();

    protected abstract boolean canPullUp();

    public static class  LayoutParams extends FrameLayout.LayoutParams{

        public LayoutParams(@NonNull Context c, @Nullable AttributeSet attrs) {
            super(c, attrs);
        }

        public LayoutParams(int width, int height) {
            super(width, height);
        }

        public LayoutParams(int width, int height, int gravity) {
            super(width, height, gravity);
        }

        public LayoutParams(@NonNull ViewGroup.LayoutParams source) {
            super(source);
        }

        public LayoutParams(@NonNull MarginLayoutParams source) {
            super(source);
        }

    }

    public class ScrollChecker implements Runnable{

        private Scroller mScroller;
        private int mLastFlingY;//上次滚动距离

        @Override
        public void run() {

            boolean finish = !mScroller.computeScrollOffset();






        }


        public void tryToScrollTo(int to,int duration){
            if (mPtrIndicator.mCurrentPosY == to){
                return;
            }

            int distance = to - mPtrIndicator.mCurrentPosY;
            removeCallbacks(this);
            mLastFlingY = 0;
            if (!mScroller.isFinished()){
                mScroller.abortAnimation();
            }

            mScroller.startScroll(0,0,0,distance,duration);
            post(this);

        }

    }


}
