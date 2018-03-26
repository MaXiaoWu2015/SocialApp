package com.example.maxiaowu.societyapp.widgets.ptr;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by matingting on 2018/3/26.
 *
 * 控件状态指示器
 *
 */

public class PtrIndicator {

 //状态信息
    /**
     * 初始状态
     */
    public static final  int  PTR_STATUS_INIT = 0;
    /**
     * 准备状态
     */
    public static final  int     PTR_STATUS_PREPARE = 1;
    /**
     * 刷新中
     */
    public static final  int     PTR_STATUS_REFRESHING = 2;
    /**
     * 加载中
     */
    public static final  int     PTR_STATUS_LOADING = 3;
    /**
     * 完成刷新
     */
    public static final  int     PTR_STATUS_COMPLETE = 4;


    @IntDef ({PTR_STATUS_INIT,PTR_STATUS_PREPARE,PTR_STATUS_REFRESHING,PTR_STATUS_LOADING,PTR_STATUS_COMPLETE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface PtrStatus {

    }



    protected  final static  int START_POS = 0;

    /**
     * 起始位置
     * */
    public static final int POS_START = 0;

    /**
     * 下拉刷新最大高度值
     * */

    public float mMaxPUllOffset = 100.0f;

    /**
     * 当前Y轴偏移量
     * */

    public int mCurrentPosY = 0;

    /**
     * mLastPosY
     * */

    public int mLastPosY = 0;

    /**
     *
     * 当前状态
     * */

    public int status = PTR_STATUS_INIT;


    /**
     * 是否刚刚离开初始位置
     * */
    public boolean justLeftStartPosition(){
        return mLastPosY == POS_START && leftStartPosition();
    }


    /***
     *是否在当前初始位置
     */

    public boolean leftStartPosition() {
        return mCurrentPosY != POS_START;
    }


}
