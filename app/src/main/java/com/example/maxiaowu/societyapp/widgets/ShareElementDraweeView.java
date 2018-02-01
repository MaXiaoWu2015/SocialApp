package com.example.maxiaowu.societyapp.widgets;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by matingting on 2018/1/22.
 */

public class ShareElementDraweeView extends SimpleDraweeView {
    public ShareElementDraweeView(Context context, GenericDraweeHierarchy hierarchy) {
        super(context, hierarchy);
    }

    public ShareElementDraweeView(Context context) {
        super(context);
    }

    public ShareElementDraweeView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ShareElementDraweeView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public ShareElementDraweeView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        Drawable drawable = getTopLevelDrawable();
        if (drawable != null ){
            drawable.setBounds(getPaddingLeft(), getPaddingTop(), w - getPaddingLeft() - getPaddingRight(), h - getPaddingTop() - getPaddingBottom());
        }


    }
}
