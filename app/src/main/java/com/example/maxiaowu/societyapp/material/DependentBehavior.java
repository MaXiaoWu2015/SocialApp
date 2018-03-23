package com.example.maxiaowu.societyapp.material;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

/**
 * Created by matingting on 2018/3/7.
 */

/**
 * 某个View监听另一个View的状态变化,例如大小、位置、显示状态等
 *
 * */
public class DependentBehavior extends CoordinatorLayout.Behavior<View> {

    //这个带参数的构造方法一定要重载,因为CoordinatorLayout利用反射拿到的就是这个构造
    public DependentBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return dependency instanceof TextView;
    }


    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        int offset = dependency.getTop() - child.getTop();
        ViewCompat.offsetTopAndBottom(child,offset);
        return true;
    }
}
