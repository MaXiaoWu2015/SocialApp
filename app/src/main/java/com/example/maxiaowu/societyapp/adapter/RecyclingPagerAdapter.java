package com.example.maxiaowu.societyapp.adapter;

import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.util.SparseArray;
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

    public RecyclingPagerAdapter() {
        recylebin=new Recylebin();
        recylebin.setViewTypeCount(getViewTypeCount());
    }

    @Override
    public void notifyDataSetChanged() {
        recylebin.scrapActiveViews();
        super.notifyDataSetChanged();
    }

    public int getViewTypeCount(){
        return 1;
    }
    @Override
    public int getCount() {
        return 4;
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
        container.addView(view);
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
        private View[] mActiveViews=new View[0];

        private int[]  mActiveViewTypes=new int[0];

        private SparseArray<View>[] mScrapViews;

        private SparseArray<View> mCurrentScrapViews;

        private int mViewTypeCount;

        public View getScrapView(int position, int viewType) {
            if (mViewTypeCount==1){
                return retrieveFromScrap(mCurrentScrapViews,position);
            }else if (viewType>=0 && viewType<=mScrapViews.length){
                return retrieveFromScrap(mScrapViews[viewType],position);
            }
            return null;
        }

        @Nullable
        private View retrieveFromScrap(SparseArray<View> scrapViews, int position) {
            int size=scrapViews.size();
            if (size>0){
                for(int i=0;i<size;i++){
                    int fromPosition=scrapViews.keyAt(i);
                    if (fromPosition==position){
                        View view=scrapViews.get(fromPosition);
                        scrapViews.remove(fromPosition);
                        return view;
                    }
                }
                int index=size-1;
                View view=scrapViews.valueAt(index);
                scrapViews.remove(scrapViews.keyAt(index));
                return view;
            }else{
                return null;
            }
        }

        public void addScrapView(View view, int position, int viewType) {
            if (mViewTypeCount==1){
                mCurrentScrapViews.put(position,view);
            }else{
                mScrapViews[viewType].put(position,view);
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                view.setAccessibilityDelegate(null);
            }
        }
        public void setViewTypeCount(int viewTypeCount) {
            if (viewTypeCount<1){
                throw new IllegalArgumentException("Can't have viewTypeCount <1");
            }
            //noinspection unchecked
            SparseArray<View>[] scrapViews=new SparseArray[viewTypeCount];
            for (int i=0;i<viewTypeCount;i++){
                scrapViews[i]=new SparseArray<View>();
            }
            this.mViewTypeCount=viewTypeCount;
            this.mScrapViews=scrapViews;
            this.mCurrentScrapViews=scrapViews[0];//TODO:?????
        }
        /** Move all views remaining in activeViews to scrapViews. */
        public void scrapActiveViews() {
            final int count=mActiveViews.length;
            SparseArray<View> scrapViews=mCurrentScrapViews;
            for (int i=count-1;i>=0;i--){
                View view=mActiveViews[i];
                if (view!=null){
                    int scrapType=mActiveViewTypes[i];
                    mActiveViews[i]=null;
                    mActiveViewTypes[i]=-1;
                    if (shouldRecycleViewType(scrapType)){
                        continue;
                    }
                    if (mViewTypeCount>1){
                        scrapViews=this.mScrapViews[scrapType];
                    }
                    scrapViews.put(i,view);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                        view.setAccessibilityDelegate(null);
                    }
                }
            }
            pruneScrapViews();
        }
        /**
         * Makes sure that the size of scrapViews does not exceed the size of activeViews.
         * (This can happen if an adapter does not recycle its views).
         */
        private void pruneScrapViews() {
            final int maxViewCount=mActiveViews.length;
            for (int i=0;i<mViewTypeCount;i++){
                final SparseArray<View> scrapPile=mScrapViews[i];
                int size=scrapPile.size();
                final int extra=size-maxViewCount;
                size--;
                for (int j=0;j<extra;j++){
                    scrapPile.remove(scrapPile.keyAt(size--));
                }
            }
        }

        private boolean shouldRecycleViewType(int scrapType) {
            return scrapType==IGNORE_ITEM_VIEW_TYPE;
        }
    }
}
