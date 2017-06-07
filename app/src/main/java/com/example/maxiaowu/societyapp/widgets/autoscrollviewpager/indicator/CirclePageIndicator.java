package com.example.maxiaowu.societyapp.widgets.autoscrollviewpager.indicator;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

import com.example.maxiaowu.societyapp.R;

/**
 * Created by matingting on 2017/5/31.
 */

public class CirclePageIndicator extends View implements PageIndicator{
    //Constant Value
    private static final int HORIZONTAL=0;
    private static final int VERTICAL=1;

    //Default Values
    private static final float DEFAULT_CIRCLE_RADIUS=2.0f;
    private static final float DEFAULT_CIRCLE_MARGIN=10;
    private static final int DEFAULT_CIRCLE_COLOR=R.color.notification_bak;
    private static final int DEFAULT_CIRCLE_SELECTED_COLOR=R.color.themeColor;
    private static final int DEFAULT_CIRCLE_ORIENTATION=0;



    private Context mContext;
    private int mCount;
    private float mCircleRadius;
    private float mCircleMargin;
    private int   mCircleColor;
    private int   mCircleSelectedColor;
    private int   mOrientation;
    private Paint mPaintStroke;
    private Paint mPaintFilll;
    private Paint mPaintSelectedFill;
    private int   mCurPosition;

    private ViewPager mViewPager;
    public CirclePageIndicator(Context context) {
        super(context);
        this.init(context,null);
    }

    public CirclePageIndicator(Context context,
            @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.init(context,attrs);
    }

    public CirclePageIndicator(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.init(context,attrs);
    }

    private void init(Context context,AttributeSet attrs){
        this.mContext=context;
        Resources resources=context.getResources();

        TypedArray typedArray=context.obtainStyledAttributes(attrs, R.styleable.CirclePageIndicator);

        mCircleRadius=typedArray.getDimension(R.styleable.CirclePageIndicator_circle_radius,DEFAULT_CIRCLE_RADIUS);
        mCircleMargin=typedArray.getDimension(R.styleable.CirclePageIndicator_circle_margin,DEFAULT_CIRCLE_MARGIN);
        mCircleColor=typedArray.getColor(R.styleable.CirclePageIndicator_circle_color,resources.getColor(DEFAULT_CIRCLE_COLOR));
        mCircleSelectedColor=typedArray.getColor(R.styleable.CirclePageIndicator_circle_selected_color,resources.getColor(DEFAULT_CIRCLE_SELECTED_COLOR));
        mOrientation=typedArray.getInt(R.styleable.CirclePageIndicator_orientation,DEFAULT_CIRCLE_ORIENTATION);


        mPaintStroke =new Paint();
        mPaintStroke.setAntiAlias(true);
        mPaintStroke.setDither(false);
        mPaintStroke.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaintStroke.setStrokeWidth(2.0f);
        mPaintStroke.setColor(resources.getColor(DEFAULT_CIRCLE_COLOR));

//        mPaintFilll=new Paint();
//        mPaintFilll.setAntiAlias(true);
//        mPaintFilll.setDither(false);
//        mPaintFilll.setColor(resources.getColor(DEFAULT_CIRCLE_COLOR));

       mPaintSelectedFill=new Paint();
       mPaintSelectedFill.setAntiAlias(true);
       mPaintSelectedFill.setDither(false);
       mPaintSelectedFill.setStyle(Paint.Style.FILL);
       mPaintSelectedFill.setColor(resources.getColor(DEFAULT_CIRCLE_SELECTED_COLOR));
    }

    public int getCount() {
        if (mViewPager==null){
            return 0;
        }
        if (mViewPager.getAdapter()==null){
            return 0;
        }
        mCount = mViewPager.getAdapter().getCount();
        return mCount;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float cX,cSelectedX;
        float cY,cSelectedY;
        int width,height;
        int paddingleft,paddingRight;
        int paddingBottom,paddingTop;
        mCount=getCount();

        if (mOrientation==HORIZONTAL){
            width=getWidth();
            paddingleft=getPaddingLeft();
            paddingRight=getPaddingRight();
        }else if (mOrientation==VERTICAL){
            height=getHeight();
            paddingTop=getPaddingTop();
            paddingBottom=getPaddingBottom();
        }


        for (int i=0;i<mCount;i++){
           if (mOrientation==HORIZONTAL){
               cX=mCircleRadius + i * (2 * mCircleRadius + mCircleMargin);
               cY=mCircleRadius;
           }else {
               cX=mCircleRadius;
               cY=mCircleRadius+i*(2*mCircleRadius+mCircleMargin);
           }
        canvas.drawCircle(cX,cY,mCircleRadius, mPaintStroke);
        if (mOrientation==HORIZONTAL){
            cSelectedX=mCircleRadius + mCurPosition * (2 * mCircleRadius + mCircleMargin);
            cSelectedY=mCircleRadius;
        }else{
           cSelectedY=mCircleRadius+i*(mCurPosition*mCircleRadius+mCircleMargin);
           cSelectedX=mCircleRadius;
        }
        canvas.drawCircle(cSelectedX,cSelectedY,mCircleRadius,mPaintSelectedFill);
        }
    }

    @Override
    public void setCurSelectedPosition(int position) {
        mCurPosition = position;
        invalidate();
    }

    @Override
    public void notifyDataChanged() {
        invalidate();
    }

    public void setViewPager(ViewPager viewPager) {
        mViewPager = viewPager;
        invalidate();
    }
}
