package com.example.maxiaowu.societyapp.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;

/**
 * Created by matingting on 2017/5/31.
 * Thanks to https://github.com/JakeWharton/salvage
 */

public abstract class RecyclingPagerAdapter extends PagerAdapter {
    /**
     * The item view type returned by {@link Adapter#getItemViewType(int)} when
     * the adapter does not want the item's view recycled.
     */
    public int IGNORE_ITEM_VIEW_TYPE= AdapterView.ITEM_VIEW_TYPE_IGNORE;
    private Recylebin recylebin;
    @Override
    public int getCount() {
        return 0;
    }

    @SuppressWarnings("UnusedParameters")
    public int getItemViewType(int position){
        return 0;
    }
    /**
     * 参数object是instantiateItem()的返回值
     * */
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        int viewType=getItemViewType(position);
        View view=null;
        if (viewType!=IGNORE_ITEM_VIEW_TYPE){
            view=recylebin.getScrapView(position,viewType);
        }
        view=getView(container,view,position);
        container.addView(view);//?
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view= (View) object;
        int viewType=getItemViewType(position);
        container.removeView(view);
        if (viewType!=IGNORE_ITEM_VIEW_TYPE){
            recylebin.addScrapView(view, position, viewType);
        }
    }

    public abstract View getView(ViewGroup container, View convertView, int position);

    class Recylebin{

        public View getScrapView(int position, int viewType) {
            return null;
        }

        public void addScrapView(View view, int position, int viewType) {
        }
    }
}
