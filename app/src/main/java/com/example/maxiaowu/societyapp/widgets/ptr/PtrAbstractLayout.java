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
import android.view.ViewParent;
import android.widget.FrameLayout;

/**
 * Created by matingting on 2018/3/22.
 */

public abstract class PtrAbstractLayout<V extends View> extends FrameLayout {

    private static final String TAG = "PtrAbstractLayout";

    protected  final static  int START_POS = 0;

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


    public PtrAbstractLayout(@NonNull Context context) {
        this(context,null);
    }

    public PtrAbstractLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,-1);
    }

    public PtrAbstractLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();

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
                    isBeginDragged = true;
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

                boolean canDrag = isStartPosition() && (canDragDown || canDragUp);

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

                doMovePos(offset);

                mLastMotionY = event.getY(moveIndex);
                return true;

            case MotionEvent.ACTION_UP:

                final int curTop = mContentView.getTop();

                animator = ValueAnimator.ofInt(curTop,0).setDuration(200);

                  animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        mContentView.offsetTopAndBottom( (int)animation.getAnimatedValue());
                    }
                });

                final int refreshTop = mRefreshView.getTop();

                animator1 =ValueAnimator.ofInt(refreshTop,0).setDuration(200);
                 animator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        mRefreshView.offsetTopAndBottom( (int)animation.getAnimatedValue());

                    }
                });

                AnimatorSet set = new AnimatorSet();
                set.playTogether(animator,animator1);
                set.start();

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

        if (offset > 0){
            mRefreshView.offsetTopAndBottom((int) (offset/2));
        }else{
            mLoadMoreView.offsetTopAndBottom((int) offset);
        }


        mContentView.offsetTopAndBottom((int) (offset / getResistance( offset > 0)));

        Log.d(TAG, "doMovePos: "+"mCurPosY--"+mCurPosY+"   offset:"+(int) (offset / getResistance( offset > 0)));

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



    private boolean isStartPosition() {
        return mCurPosY == START_POS;
    }

    protected abstract boolean canPullDown();

    protected abstract boolean canPullUp();


}
