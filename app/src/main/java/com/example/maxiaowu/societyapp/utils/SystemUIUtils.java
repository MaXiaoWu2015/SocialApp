package com.example.maxiaowu.societyapp.utils;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewCompat;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.example.maxiaowu.societyapp.R;

import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
import static android.view.View.SYSTEM_UI_FLAG_VISIBLE;

/**
 * Created by maxiaowu on 2018/1/9.
 *
 * SystemWindow--系统窗口:指的就是屏幕上statusbar、navigationbar等系统空间占据的部分
 *
 *
 */

public class SystemUIUtils {
    private static final String TAG_FAKE_STATUS_BAR_VIEW = "tag_fake_status_bar_view";

    /**
     * 状态栏透明
     * @param isNeedShadow 是否需要阴影
     * */
    public static void makeStatusBarTransparent(Activity activity,boolean isNeedShadow){
        if (activity == null || activity.getWindow() == null) return;

        Window window = activity.getWindow();
        ViewGroup contentView =window.findViewById(Window.ID_ANDROID_CONTENT);
        View childView = contentView.getChildAt(0);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){

            if (isNeedShadow){
                window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.getDecorView().setSystemUiVisibility(SYSTEM_UI_FLAG_VISIBLE);
            }else {
                window.getDecorView().setSystemUiVisibility(SYSTEM_UI_FLAG_LAYOUT_STABLE | SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
                setStatusBarColor(activity, Color.TRANSPARENT);
            }
            if (childView != null){//为了防止之前设置了fitSystemWindow属性为true
                ViewCompat.setFitsSystemWindows(childView,false);
                ViewCompat.requestApplyInsets(childView);
            }
        }else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            if (childView != null){
                ViewCompat.setFitsSystemWindows(childView,false);
            }

        }
    }



    //1.改变状态栏的颜色

    public static void changeStatusBarColor(Activity activity,int statusColor){

        if (activity == null || activity.getWindow() == null)return;

        Window window = activity.getWindow();

        ViewGroup contentView =window.findViewById(Window.ID_ANDROID_CONTENT);
        View childView = contentView.getChildAt(0);

        //5.0以上, 直接改变状态栏的颜色
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){

            setStatusBarColor(activity,statusColor);

            //3.设置状态栏是可见的----应该可去掉,因为默认是可见的
//            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
            //让view不根据系统窗口(状态栏、导航栏)调整布局
            if (childView != null){
                ViewCompat.setFitsSystemWindows(childView,false);
               ViewCompat.requestApplyInsets(childView);
            }


        }else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            //4.4~5.0  将状态栏设置成透明的,然后add一个高度与状态栏相同背景色为statuscolor的View

            //1.设置透明flag
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            //2.创建假状态栏

            int statusHeight = getStatusBatHeight(activity);
            removeFakeStatusBarIfExists(activity);
            addFakeStatusBarView(activity,statusColor,statusHeight);
            addMarginTopToContentChild(childView,statusHeight);


            //如果设置了ActionBar
            int action_bar_Id = activity.getResources().getIdentifier("action_bar","id",activity.getPackageName());

            View view = activity.findViewById(action_bar_Id);


            if (view != null){
                TypedValue typedValue = new TypedValue();
                if (activity.getTheme().resolveAttribute(R.attr.actionBarSize,typedValue,true)){
                    int actionBarHeight = TypedValue.complexToDimensionPixelOffset(typedValue.data,activity.getResources().getDisplayMetrics());
                    setContentToPadding(contentView,actionBarHeight);
                }

            }

            //让view不根据系统窗口(状态栏、导航栏)调整布局
            if (childView != null){
                ViewCompat.setFitsSystemWindows(childView,false);
//            ViewCompat.requestApplyInsets(childView);
            }

        }

    }

    /**
     * 5.0以上设置状态栏颜色
     *
     * */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private static void setStatusBarColor(Activity activity, int statusColor){
        Window window = activity.getWindow();
        //1.清空透明flag
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        //2.设置背景是可绘制的
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        window.setStatusBarColor(statusColor);
    }


    private static void setContentToPadding(View contentView, int actionBarHeight) {
        contentView.setPadding(0,actionBarHeight,0,0);
    }

    private static void removeFakeStatusBarIfExists(Activity activity) {

        ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();

        View fakeView = decorView.findViewWithTag(TAG_FAKE_STATUS_BAR_VIEW);

        if ( fakeView != null){
            decorView.removeView(fakeView);
        }


    }

    private static void addMarginTopToContentChild(View childView, int statusHeight) {
        if (childView ==null) return;
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) childView.getLayoutParams();
        params.topMargin += statusHeight;
        childView.setLayoutParams(params);
    }

    private static void addFakeStatusBarView(Activity activity, int statusColor, int statusHeight) {
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
        ,statusHeight);
        params.gravity = Gravity.TOP;
        View fakeStatusView = new View(activity);
        fakeStatusView.setLayoutParams(params);
        fakeStatusView.setBackgroundColor(statusColor);
        fakeStatusView.setTag(TAG_FAKE_STATUS_BAR_VIEW);
        ((ViewGroup)activity.getWindow().getDecorView()).addView(fakeStatusView);
    }

    private static int getStatusBatHeight(Activity activity) {
        int result = 0;

        int resourceId = activity.getResources().getIdentifier("status_bar_height"
                ,"dimen","android");

        if (resourceId > 0){
            result = activity.getResources().getDimensionPixelOffset(resourceId);
        }

        return result;
    }

}
