package com.example.maxiaowu.societyapp.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.example.maxiaowu.societyapp.R;

/**
 * Created by matingting on 2017/12/26.
 */

public class LabelFlowLayout extends ViewGroup {

    private int label_maxLines =0;

    private float label_line_space = 0.0f;

    private float label_space = 0.0f;
    private int mWidth;
    private int mHeight;

    public LabelFlowLayout(Context context) {
        this(context,null);

    }

    public LabelFlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs,-1);
    }

    public LabelFlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs,R.styleable.LabelFlowLayout);

        label_maxLines = typedArray.getInt(R.styleable.LabelFlowLayout_label_max_lines,3);

        label_line_space = typedArray.getDimension(R.styleable.LabelFlowLayout_label_line_space,0.0f);

        label_space = typedArray.getDimension(R.styleable.LabelFlowLayout_label_space,0.0f);

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        measureChildren(widthMeasureSpec, heightMeasureSpec);

        int width = mWidth = MeasureSpec.getSize(widthMeasureSpec);

        int  height = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int lineCount = 0;

        int childLeft = getPaddingLeft();

        int childCount = getChildCount();

        mHeight = 0;

            for (int i = 0;i<childCount;i++){
                View childView = getChildAt(i);

                if (childView.getVisibility() == View.GONE){
                    continue;
                }


                int childWidth = childView.getMeasuredWidth();
                int childHeight = childView.getMeasuredHeight();

                mHeight = Math.max(childHeight,mHeight);

                if (childLeft+childWidth+getPaddingRight() > mWidth){
                    childLeft = getPaddingLeft();
                    mHeight +=label_line_space+childHeight;

                    lineCount++;
                    if (lineCount >= label_maxLines){
                        break;
                    }

                }else {
                    childLeft += childWidth+label_space;
                }
            }

            mHeight = Math.min(getPaddingTop()+mHeight+getPaddingBottom(),height);

        setMeasuredDimension(mWidth
                ,MeasureSpec.makeMeasureSpec(mHeight,heightMode));
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        int childCount = getChildCount();

        int childLeft = getPaddingLeft();

        int childTop = getPaddingTop();

        int lineCount = 0;

        for (int i=0;i<childCount;i++){
            View childView = getChildAt(i);

            if (childView.getVisibility() == View.GONE)continue;


            int childWidth = childView.getMeasuredWidth();
            int childHeight = childView.getMeasuredHeight();

            if (childLeft+childWidth+getPaddingRight() > mWidth){
                lineCount++;

                if (lineCount>label_maxLines)break;

                childLeft = getPaddingLeft();
                childTop += childHeight+label_line_space;
            }

            childView.layout(childLeft,childTop,childLeft+childWidth,childTop+childHeight);
            childLeft += childWidth+label_space;

        }

    }
}
