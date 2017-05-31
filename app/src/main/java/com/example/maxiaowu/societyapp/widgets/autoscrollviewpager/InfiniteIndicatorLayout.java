package com.example.maxiaowu.societyapp.widgets.autoscrollviewpager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.example.maxiaowu.societyapp.R;
import com.example.maxiaowu.societyapp.adapter.RecycleAdapter;
import com.example.maxiaowu.societyapp.widgets.autoscrollviewpager.indicator.CirclePageIndicator;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by matingting on 2017/5/31.
 */

public class InfiniteIndicatorLayout extends RelativeLayout implements
        ViewPager.OnPageChangeListener {
    private Context mContext;
    private @BindView(R.id.vp_img) ViewPager mViewPager;
    private @BindView(R.id.cpi_for_img) CirclePageIndicator mPageIndicators;

    private RecylablePagerAdapter mPagerAdapter;
    private int mImagesCount;
    public InfiniteIndicatorLayout(Context context) {
        super(context);
        this.init(context);
    }

    public InfiniteIndicatorLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init(context);
    }

    public InfiniteIndicatorLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.init(context);
    }
    private void init(Context context){
        mContext=context;
        inflate(mContext,R.layout.infinite_indicator_layout,this);
        ButterKnife.bind(this);

        mViewPager.addOnPageChangeListener(this);
        mPagerAdapter=new RecylablePagerAdapter(mImagesCount,mContext);
        mViewPager.setAdapter(mPagerAdapter);
        mPageIndicators.setViewPager(mViewPager);

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mPageIndicators.setCurSelectedPosition(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public void setImagesCount(int imagesCount) {
        mImagesCount = imagesCount;
    }

    class RecylablePagerAdapter extends RecycleAdapter{
        private int mCount;
        private Context mContext;

        public RecylablePagerAdapter(int count, Context context) {
            mCount = count;
            mContext = context;
        }

    }
}
