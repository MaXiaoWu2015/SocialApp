package com.example.maxiaowu.societyapp.widgets.photoDraweeView;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by matingting on 2017/5/5.
 */

public class PhotoDraweeView extends SimpleDraweeView {
    private Attacher mAttacher;
    public PhotoDraweeView(Context context,
            GenericDraweeHierarchy hierarchy) {
        super(context, hierarchy);
        init();
    }

    public PhotoDraweeView(Context context) {
        super(context);
        init();
    }

    public PhotoDraweeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PhotoDraweeView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public PhotoDraweeView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public void init(){
        mAttacher=new Attacher(this);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mAttacher!=null){
            canvas.concat(mAttacher.getDrawMatrix());
        }
        super.onDraw(canvas);
    }

    public void update(int width, int height) {
        if (mAttacher!=null){
            mAttacher.update(width,height);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        mAttacher.onDetachFromWindow();
        super.onDetachedFromWindow();
    }
}
