package com.example.maxiaowu.societyapp.widgets.autoscrollviewpager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.maxiaowu.societyapp.R;
import com.example.maxiaowu.societyapp.adapter.RecyclingPagerAdapter;
import com.example.maxiaowu.societyapp.utils.ImageLoaderManager;
import com.example.maxiaowu.societyapp.widgets.autoscrollviewpager.indicator.CirclePageIndicator;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by matingting on 2017/5/31.
 * TODO:给ViewPager加上页面滑动动画 Page Transformer
 */

public class InfiniteIndicatorLayout extends RelativeLayout implements
        ViewPager.OnPageChangeListener {
    private Context mContext;
    public  @BindView(R.id.vp_img) ViewPager mViewPager;
    public  @BindView(R.id.cpi_for_img) CirclePageIndicator mPageIndicators;
    private ArrayList<String> mImageUrls;
    private RecyclablePagerAdapter mPagerAdapter;
    private int mImagesCount=4;
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
        initData();
        mViewPager.addOnPageChangeListener(this);
        mPagerAdapter=new RecyclablePagerAdapter(mImagesCount,mContext);
        mPagerAdapter.setImageUrls(mImageUrls);
        mViewPager.setAdapter(mPagerAdapter);
        mPageIndicators.setViewPager(mViewPager);
    }

    public void initData(){
//        mImageUrls=new ArrayList<>(Arrays.asList("http://business.cdn.qianqian.com/qianqian/pic/bos_client_14962159099c2ea0bd507d060f6fdf388edf4d2e99.jpg"
//                ,"http://business.cdn.qianqian.com/qianqian/pic/bos_client_1495789484dc0b75f81f249fa3479032f2eb662dc4.jpg"
//                ,"http://business.cdn.qianqian.com/qianqian/pic/bos_client_14957057549566557e02bd868968cc35e7f94083b1.jpg"
//                ,"http://business.cdn.qianqian.com/qianqian/pic/bos_client_149587343811606d084cf4dbd7ceec85ebb0b69aa1.jpg"));
        mImageUrls=new ArrayList<>(Arrays.asList("res://"+mContext.getPackageName() +"/" + R.drawable.first
                ,"res://"+mContext.getPackageName() +"/" + R.drawable.first1
                ,"res://"+mContext.getPackageName() +"/" + R.drawable.first2
                ,"res://"+mContext.getPackageName() +"/" + R.drawable.first3));
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

    class RecyclablePagerAdapter extends RecyclingPagerAdapter {
        public final static int TAG_KEY_URL=0;
        private int mCount;
        private Context mContext;
        private ArrayList<String> mImageUrls;


        public RecyclablePagerAdapter(int count, Context context) {
            mCount = count;
            mContext = context;
            mImageUrls=new ArrayList<>();
        }

        public void setImageUrls(ArrayList<String> imageUrls) {
            mImageUrls = imageUrls;
        }

        @Override
        public View getView(ViewGroup container, View convertView, int position) {
            ViewHolder viewHolder=null;
            if (convertView==null){
                convertView=LayoutInflater.from(mContext).inflate(R.layout.infinite_indicator_layout_item,container,false);
                viewHolder=new ViewHolder(convertView);
                convertView.setTag(viewHolder);
                viewHolder.mDraweeView.setTag(mImageUrls.get(position));
            }
            viewHolder= (ViewHolder) convertView.getTag();
            String urlTag= (String) viewHolder.mDraweeView.getTag(TAG_KEY_URL);
            String url=mImageUrls.get(position);
            if (!url.equals(urlTag)){
                ImageLoaderManager.loadImage(url,viewHolder.mDraweeView,false);
            }
            return convertView;
        }
         class ViewHolder{
             SimpleDraweeView mDraweeView;
             public ViewHolder(View itemView) {
                 mDraweeView = (SimpleDraweeView) itemView.findViewById(R.id.dv_img);
             }
         }
    }
}
